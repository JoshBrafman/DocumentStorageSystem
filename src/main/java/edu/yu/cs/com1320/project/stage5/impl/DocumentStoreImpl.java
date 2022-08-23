package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.CommandSet;
import edu.yu.cs.com1320.project.GenericCommand;
import edu.yu.cs.com1320.project.Undoable;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
import edu.yu.cs.com1320.project.impl.MinHeapImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.function.Function;

public class DocumentStoreImpl implements DocumentStore {
    class URIObjectComparer implements java.util.Comparator<URIObject> {
        String word;
        Boolean isPrefix;
        BTreeImpl<URI, Document> bTree;

        public URIObjectComparer(String word, Boolean isPrefix) {
            this.word = word;
            this.isPrefix = isPrefix;
        }

        public int compare(URIObject first, URIObject second) {
            int number1 = 0;
            int number2 = 0;
            if (!isPrefix) {
                return (bTree.get(first.getKey()).wordCount(word) - bTree.get(second.getKey()).wordCount(word)) * (-1);
            } else {
                for (String string : bTree.get(first.getKey()).getWords()) {
                    if (string.startsWith(this.word)) {
                        number1 += bTree.get(first.getKey()).wordCount(string);
                    }
                }
                for (String string : bTree.get(second.getKey()).getWords()) {
                    if (string.startsWith(this.word)) {
                        number2 += bTree.get(second.getKey()).wordCount(string);
                    }
                }
                return number2 - number1;
            }
        }
    }

    class URIObject implements Comparable<URIObject> {
        BTreeImpl<URI, Document> bTree;
        URI uri;

        public URIObject(BTreeImpl bTree, URI uri) {
            this.bTree = bTree;
            this.uri = uri;
        }

        protected URI getKey() {
            return this.uri;
        }

        @Override
        public int compareTo(URIObject other) {
            return bTree.get(this.uri).compareTo(bTree.get(other.uri));
        }
    }

    private final BTreeImpl<URI, Document> bTree;
    private final StackImpl<Undoable> stack;
    private final TrieImpl<URIObject> trie;
    private final MinHeapImpl<URIObject> heap;
    private int maxDocumentCount;
    private int maxDocumentBytes;
    private int documentCount;
    private int byteCount;
    private boolean maxDocumentCountWasSet;
    private boolean maxDocumentBytesWasSet;
    private final HashMap<URI, URIObject> uriToUriObject;
    private final HashSet<URI> docsKickedOutOfMemory;

    public DocumentStoreImpl() {
        this.bTree = new BTreeImpl<>();
        this.stack = new StackImpl<>();
        this.trie = new TrieImpl<>();
        this.heap = new MinHeapImpl<>();
        this.uriToUriObject = new HashMap<>();
        this.docsKickedOutOfMemory = new HashSet<>();
        DocumentPersistenceManager pm = new DocumentPersistenceManager(null);
        bTree.setPersistenceManager(pm);
    }

    public DocumentStoreImpl(File baseDir) {
        this.bTree = new BTreeImpl<>();
        this.stack = new StackImpl<>();
        this.trie = new TrieImpl<>();
        this.heap = new MinHeapImpl<>();
        this.uriToUriObject = new HashMap<>();
        DocumentPersistenceManager pm = new DocumentPersistenceManager(baseDir);
        bTree.setPersistenceManager(pm);
        this.docsKickedOutOfMemory = new HashSet<>();
    }

    @Override
    public int putDocument(InputStream input, URI uri, DocumentFormat format) throws IOException {

        if (input == null && uri != null) {
            Document previous = this.bTree.get(uri);
            this.deleteDocument(uri);
            return zeroOrHashcode(previous);
        }

        if (uri == null || format == null) {
            throw new IllegalArgumentException();
        }

        byte[] bytes = input.readAllBytes();
        if (format == DocumentFormat.TXT) {
            String str = new String(bytes);
            DocumentImpl doc = new DocumentImpl(uri, str, null);
            Document previous = this.bTree.get(uri);
            if (!this.docsKickedOutOfMemory.contains(uri)) {
                this.byteCount -= this.getByteOfDocument(previous);
                this.subtractOrDont(previous);
                if (previous != null) {
                    this.removeFromHeap(this.uriToUriObject.get(uri));
                }
            }
            this.byteCount += this.getByteOfDocument(doc);
            this.documentCount += 1;

            if (previous != null && previous.getDocumentTxt() != null) {
                this.deleteFromTrie(this.uriToUriObject.get(uri));
            }
            this.bTree.put(uri, doc);
            doc.setLastUseTime(java.lang.System.nanoTime());
            URIObject uriObject = new URIObject(this.bTree, uri);
            this.uriToUriObject.put(uri, uriObject);
            this.heap.insert(uriObject);
            this.uploadToTrie(uriObject);
            boolean goToDisk = this.docsKickedOutOfMemory.remove(uri);
            this.getBackUnderLimit();
            this.addCommand(previous, uri, goToDisk);
            return zeroOrHashcode(previous);
        }

        if (format == DocumentFormat.BINARY) {
            DocumentImpl doc = new DocumentImpl(uri, bytes);
            Document previous = this.bTree.get(uri);
            if (!this.docsKickedOutOfMemory.contains(uri)) {
                this.byteCount -= this.getByteOfDocument(previous);
                this.subtractOrDont(previous);
                if (previous != null) {
                    this.removeFromHeap(this.uriToUriObject.get(uri));
                }
            }

            if (previous != null && previous.getDocumentTxt() != null) {
                this.deleteFromTrie(this.uriToUriObject.get(uri));
            }
            this.byteCount += this.getByteOfDocument(doc);
            this.documentCount += 1;
            this.bTree.put(uri, doc);
            doc.setLastUseTime(java.lang.System.nanoTime());
            URIObject uriObject = new URIObject(this.bTree, uri);
            this.uriToUriObject.put(uri, uriObject);
            this.heap.insert(uriObject);
            boolean goToDisk = this.docsKickedOutOfMemory.remove(uri);
            this.getBackUnderLimit();
            this.addCommand(previous, uri, goToDisk);
            return zeroOrHashcode(previous);
        }
        return -1;
    }

    public Document getDocument(URI uri) {
        Document doc = this.bTree.get(uri);
        if (doc == null) {
            return null;
        }
        if (this.docsKickedOutOfMemory.contains(uri)) {
            this.setTimeAndInsertToHeap(this.uriToUriObject.get(uri));
            this.byteCount += this.getByteOfDocument(doc);
            this.documentCount += 1;
        } else {
            this.setTimeAndReheapify(this.uriToUriObject.get(uri));
        }
        if (this.docsKickedOutOfMemory.contains(uri)) {
            this.docsKickedOutOfMemory.remove(uri);
            this.getBackUnderLimit();
        }
        return doc;
    }

    public boolean deleteDocument(URI uri) {
        Document previous = this.bTree.get(uri);
        boolean goToDisk = false;
        if (!this.docsKickedOutOfMemory.contains(uri)) {
            this.byteCount -= this.getByteOfDocument(previous);
            this.subtractOrDont(previous);
            this.removeFromHeap(this.uriToUriObject.get(uri));
        } else {
            goToDisk = true;
        }
        this.docsKickedOutOfMemory.remove(uri);


        boolean finalGoToDisk = goToDisk;
        Function<URI, Boolean> function = (thing) -> {
            this.deleteFromTrie(this.uriToUriObject.get(uri));
            if (!this.docsKickedOutOfMemory.contains(uri)) {
                this.removeFromHeap(this.uriToUriObject.get(uri));
                this.subtractOrDont(this.getDocument(thing));
                this.byteCount -= this.getByteOfDocument(this.getDocument(thing));
            }
            this.bTree.put(thing, previous);
            URIObject uriObject = new URIObject(this.bTree, uri);
            this.uriToUriObject.put(uri, uriObject);
            this.docsKickedOutOfMemory.remove(uri);
            this.uploadToTrie(this.uriToUriObject.get(uri));
            this.byteCount += this.getByteOfDocument(previous);
            this.addOrDont(previous);
            this.setTimeAndInsertToHeap(this.uriToUriObject.get(uri));
            if (finalGoToDisk) {
                this.undoToDisk(previous, uri);
            }
            this.getBackUnderLimit();
            return true;
        };
        GenericCommand<URI> command = new GenericCommand(uri, function);
        this.stack.push(command);
        if (previous != null && previous.getDocumentTxt() != null) {
            this.deleteFromTrie(this.uriToUriObject.get(uri));
        }
        this.bTree.put(uri, null);
        return previous != null;
    }

    @Override
    public void undo() throws IllegalStateException {
        if (this.stack.size() == 0) {
            throw new IllegalStateException();
        }
        Undoable command = this.stack.pop();
        command.undo();
    }

    @Override
    public void undo(URI uri) throws IllegalStateException {
        if (this.stack.size() == 0) {
            throw new IllegalStateException();
        }
        StackImpl<Undoable> temp = new StackImpl<>();
        while ((this.stack.peek() instanceof GenericCommand && !((GenericCommand) this.stack.peek()).getTarget().equals(uri)) || (this.stack.peek() instanceof CommandSet && !((CommandSet) this.stack.peek()).containsTarget(uri))) {
            Undoable command = stack.pop();
            temp.push(command);
            if (stack.size() == 0) {
                while (!(temp.size() == 0)) {
                    Undoable c = temp.pop();
                    this.stack.push(c);
                }
                throw new IllegalStateException();
            }
        }
        if (this.stack.peek() instanceof GenericCommand) {
            Undoable command = this.stack.pop();
            command.undo();

            while (temp.size() != 0) {
                Undoable thing = temp.pop();
                this.stack.push(thing);
            }
        }

        if (this.stack.peek() instanceof CommandSet) {
            CommandSet<URI> commandSet = (CommandSet<URI>) this.stack.peek();
            commandSet.undo(uri);
            if (commandSet.size() == 0) {
                this.stack.pop();
            }
            while (temp.size() != 0) {
                Undoable thing = temp.pop();
                this.stack.push(thing);
            }
        }
    }

    private int zeroOrHashcode(Document document) {
        if (document == null) {
            return 0;
        }
        return document.hashCode();
    }

    private void addCommand(Document document, URI uri, boolean goToDisk) {
        Function<URI, Boolean> function
                = (thing) -> {
            this.deleteFromTrie(this.uriToUriObject.get(uri));
            if (!this.docsKickedOutOfMemory.contains(uri)) {
                this.removeFromHeap(this.uriToUriObject.get(uri));
                this.subtractOrDont(this.getDocument(thing));
                this.byteCount -= this.getByteOfDocument(this.getDocument(thing));
            }
            this.bTree.put(thing, document);
            URIObject uriObject = new URIObject(this.bTree, uri);
            this.uriToUriObject.put(uri, uriObject);
            this.docsKickedOutOfMemory.remove(uri);
            this.uploadToTrie(this.uriToUriObject.get(uri));
            this.byteCount += this.getByteOfDocument(document);
            this.addOrDont(document);
            this.setTimeAndInsertToHeap(this.uriToUriObject.get(uri));
            if (goToDisk) {
               this.undoToDisk(document, uri);
            }
            this.getBackUnderLimit();
            return true;
        };
        GenericCommand<URI> command = new GenericCommand(uri, function);
        this.stack.push(command);
    }

    /**
     * Retrieve all documents whose text contains the given keyword.
     * Documents are returned in sorted, descending order, sorted by the number of times the keyword appears in the document.
     * Search is CASE INSENSITIVE.
     *
     * @param keyword
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    @Override
    public List<Document> search(String keyword) {
        URIObjectComparer documentComparer = new URIObjectComparer(keyword.toLowerCase(), false);
        List<URIObject> list = this.trie.getAllSorted(keyword.toLowerCase(), documentComparer);
        List<Document> documents = new ArrayList<>();
        for (URIObject uriObject : list) {
            documents.add(this.getDocument(uriObject.getKey()));
        }
        return documents;
    }

    /**
     * Retrieve all documents whose text starts with the given prefix
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE INSENSITIVE.
     *
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    @Override
    public List<Document> searchByPrefix(String keywordPrefix) {
        URIObjectComparer documentComparer = new URIObjectComparer(keywordPrefix.toLowerCase(), true);
        List<URIObject> list = this.trie.getAllWithPrefixSorted(keywordPrefix.toLowerCase(), documentComparer);
        List<Document> documents = new ArrayList<>();
        for (URIObject uriObject : list) {
            documents.add(this.getDocument(uriObject.getKey()));
        }
        return documents;
    }

    /**
     * Completely remove any trace of any document which contains the given keyword
     *
     * @param keyword
     * @return a Set of URIs of the documents that were deleted.
     */
    @Override
    public Set<URI> deleteAll(String keyword) {
        Set<URIObject> uriObjects = this.trie.deleteAll(keyword.toLowerCase());
        Set<URI> setOfUris = new HashSet<>();
        Set<Document> documents = new HashSet<>();
        for (URIObject uriObject : uriObjects) {
            documents.add(this.bTree.get(uriObject.getKey()));
            if (!this.docsKickedOutOfMemory.contains(uriObject.getKey())) {
                this.byteCount -= this.getByteOfDocument(this.bTree.get(uriObject.getKey()));
                this.subtractOrDont(this.bTree.get(uriObject.getKey()));
                if (this.bTree.get(uriObject.getKey()) != null) {
                    this.removeFromHeap(uriObject);
                }
            }
            this.deleteFromTrie(uriObject);
        }
        for (Document document : documents) {
            setOfUris.add(document.getKey());
        }
        this.deleteDocumentsAndMakeCommandSet(setOfUris);
        return setOfUris;
    }

    /**
     * Completely remove any trace of any document which contains a word that has the given prefix
     * Search is CASE INSENSITIVE.
     *
     * @param keywordPrefix
     * @return a Set of URIs of the documents that were deleted.
     */
    @Override
    public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
        Set<URIObject> uriObjects = this.trie.deleteAllWithPrefix(keywordPrefix.toLowerCase());
        Set<URI> setOfUris = new HashSet<URI>();
        Set<Document> documents = new HashSet<Document>();
        for (URIObject uriObject : uriObjects) {
            documents.add(this.bTree.get(uriObject.getKey()));
            if (!this.docsKickedOutOfMemory.contains(uriObject.getKey())) {
                this.byteCount -= this.getByteOfDocument(this.bTree.get(uriObject.getKey()));
                this.subtractOrDont(this.bTree.get(uriObject.getKey()));
                if (this.bTree.get(uriObject.getKey()) != null) {
                    this.removeFromHeap(uriObject);
                }
            }
            this.deleteFromTrie(uriObject);
        }
        for (Document document : documents) {
            setOfUris.add(document.getKey());
        }
        this.deleteDocumentsAndMakeCommandSet(setOfUris);
        return setOfUris;
    }

    private void uploadToTrie(URIObject uriObject) {
        if (uriObject == null) {
            return;
        }
        if (this.bTree.get(uriObject.getKey()) == null || this.bTree.get(uriObject.getKey()).getDocumentTxt() == null) {
            return;
        }
        for (String string : this.bTree.get(uriObject.getKey()).getWords()) {
            this.trie.put(string.toLowerCase(), uriObject);
        }
    }


    private void deleteFromTrie(URIObject uriObject) {
        if (uriObject == null) {
            return;
        }
        if (this.bTree.get(uriObject.getKey()) == null || this.bTree.get(uriObject.getKey()).getDocumentTxt() == null) {
            return;
        }
        if (this.bTree.get(uriObject.getKey()).getWords().size() == 0) {
            return;
        }
        for (String string : this.bTree.get(uriObject.getKey()).getWords()) {
            this.trie.delete(string.toLowerCase(), uriObject);
        }
    }


    private void deleteDocumentsAndMakeCommandSet(Set<URI> setOfUris) {
        CommandSet<URI> commandSet = new CommandSet<URI>();
        for (URI uri : setOfUris) {
            Document previous = this.bTree.get(uri);
            boolean goToDisk = this.docsKickedOutOfMemory.remove(uri);
            Function<URI, Boolean> function = (thing) ->
            {
                this.deleteFromTrie(this.uriToUriObject.get(uri));
                if (!this.docsKickedOutOfMemory.contains(uri)) {
                    this.removeFromHeap(this.uriToUriObject.get(uri));
                    this.subtractOrDont(this.getDocument(thing));
                    this.byteCount -= this.getByteOfDocument(this.getDocument(thing));
                }
                this.bTree.put(thing, previous);
                URIObject uriObject = new URIObject(this.bTree, uri);
                this.uriToUriObject.put(uri, uriObject);
                this.docsKickedOutOfMemory.remove(uri);
                this.uploadToTrie(this.uriToUriObject.get(uri));
                this.byteCount += this.getByteOfDocument(previous);
                this.addOrDont(previous);
                this.setTimeAndInsertToHeap(this.uriToUriObject.get(uri));
                if (goToDisk) {
                    this.undoToDisk(previous,uri);
                }
                this.getBackUnderLimit();
                return true;
            };
            GenericCommand<URI> command = new GenericCommand(uri, function);
            commandSet.addCommand(command);
            this.bTree.put(uri, null);
        }
        this.stack.push(commandSet);
    }

    public void setMaxDocumentCount(int limit) {
        this.maxDocumentCountWasSet = true;
        this.maxDocumentCount = limit;
        this.getBackUnderLimit();
    }

    public void setMaxDocumentBytes(int limit) {
        this.maxDocumentBytesWasSet = true;
        this.maxDocumentBytes = limit;
        this.getBackUnderLimit();
    }

    private void setTimeAndReheapify(URIObject uriObject) {
        if (uriObject == null) {
            return;
        }
        if (this.bTree.get(uriObject.getKey()) == null) {
            return;
        }
        this.bTree.get(uriObject.getKey()).setLastUseTime(java.lang.System.nanoTime());
        this.heap.reHeapify(uriObject);
    }

    private void setTimeAndInsertToHeap(URIObject uriObject) {
        if (uriObject == null) {
            return;
        }
        if (this.bTree.get(uriObject.getKey()) == null) {
            return;
        }
        this.bTree.get(uriObject.getKey()).setLastUseTime(java.lang.System.nanoTime());
        this.heap.insert(uriObject);
    }

    private void removeFromHeap(URIObject uriObject) {

        if (uriObject == null) {
            return;
        }
        if (this.bTree.get(uriObject.getKey()) == null) {
            return;
        }
        this.bTree.get(uriObject.getKey()).setLastUseTime(0);
        this.heap.reHeapify(uriObject);
        this.heap.remove();
    }

    private void sendToDisk(URI uri) throws Exception {
        this.byteCount -= this.getByteOfDocument(this.getDocument(uri));
        this.subtractOrDont(this.getDocument(uri));
        this.docsKickedOutOfMemory.add(uri);
        this.bTree.moveToDisk(uri);
        StackImpl<Undoable> temp = new StackImpl<>();
    }

    private int getByteOfDocument(Document doc) {
        if (doc == null) {
            return 0;
        }
        if (doc.getDocumentTxt() != null) {
            return doc.getDocumentTxt().getBytes().length;
        } else {
            return doc.getDocumentBinaryData().length;
        }
    }

    private boolean tooManyDocuments() {
        return this.maxDocumentCountWasSet && this.documentCount > this.maxDocumentCount;
    }

    private boolean tooManyBytes() {
        return this.maxDocumentBytesWasSet && this.byteCount > this.maxDocumentBytes;
    }

    private void subtractOrDont(Document document){
        if (document != null){
            this.documentCount -=1;
        }
    }

    private void addOrDont(Document document) {
        if (document != null) {
            this.documentCount += 1;
        }
    }

    private void getBackUnderLimit() {
        while (this.tooManyBytes()) {
            try {
                this.sendToDisk(this.heap.remove().getKey());
            } catch (Exception ignored) {
            }
        }
        while (this.tooManyDocuments()) {
            try {
                this.sendToDisk(this.heap.remove().getKey());
            } catch (Exception ignored) {
            }
        }
    }

    private void undoToDisk(Document previous, URI uri){
        try {
            this.subtractOrDont(previous);
            this.byteCount -= this.getByteOfDocument(previous);
            this.docsKickedOutOfMemory.add(uri);
            this.removeFromHeap(uriToUriObject.get(uri));
            this.bTree.moveToDisk(uri);
        } catch (Exception ignored) {
        }
    }
}
