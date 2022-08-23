package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import edu.yu.cs.com1320.project.stage5.impl.DocumentStoreImpl;
//import org.testng.annotations.Test;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class StageThreeDocumentStoreImplTest {



    @Test
    public void afterDeletingEverything() throws IOException, URISyntaxException {
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String string1 = "z a";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        assert store.search("Z").size() != 0;
        String string2 = "hat ah ha is z two ay z";
        InputStream stream3 = new ByteArrayInputStream((string2.getBytes()));
        assert store.deleteDocument((uri)) == true;
        store.undo();
        assert store.deleteDocument((uri)) == true;
        assert store.deleteDocument((uri)) == false;
        assert store.search("Z").size() == 0;
        store.putDocument(stream3, uri2, DocumentStore.DocumentFormat.TXT);
        assert store.search("Z").size() == 1;



    }
    @Test
    public void orderOfPrefix() throws IOException, URISyntaxException {
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String string1 = "z a";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        String string2 = "hat ah ha is z two ay z";
        InputStream stream3 = new ByteArrayInputStream((string2.getBytes()));
        assert store.search("z").size() == 1;
        assert store.getDocument(uri).getDocumentTxt().equals(string1);
        store.putDocument(stream3, uri2, DocumentStore.DocumentFormat.TXT);
        List<Document> list = store.searchByPrefix("a");
        assert list.get(0).getDocumentTxt().equals(string2);
        assert list.get(1).getDocumentTxt().equals(string1);
        List<Document> list2 = store.search("z");
        assert list2.get(0).getDocumentTxt().equals(string2);
        assert list2.get(1).getDocumentTxt().equals(string1);

    }
    @Test
    public void deleteByOverride()throws URISyntaxException, IOException{
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String string1 = "z";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        String string2 = "hat ah ha is two";
        InputStream stream3 = new ByteArrayInputStream((string2.getBytes()));
        assert store.search("z").size() == 1;
        assert store.getDocument(uri).getDocumentTxt().equals(string1);
        store.putDocument(stream3, uri, DocumentStore.DocumentFormat.TXT);
        assert store.getDocument(uri).getDocumentTxt().equals(string2);
        assert store.search("z").size() == 0;
        assert store.searchByPrefix("ah").size() == 1;
        store.undo(uri);
        assert store.search("z").size() == 1;
        assert store.getDocument(uri).getDocumentTxt().equals(string1);
        assert store.searchByPrefix("ah").size() == 0;
    }
    @Test
    public void commandSetWithNothing()throws URISyntaxException, IOException{
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String string1 = "Hello-' world am I right";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        assert store.deleteAllWithPrefix("zz").size() == 0;
        store.undo();
        assert store.getDocument(uri) != null;
        store.undo();
        assert store.getDocument(uri) == null;
    }

    @Test
    public void deleteAllWhenItWillMakeACommandSetForOnlyOneThing() throws URISyntaxException, IOException {
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String string1 = "Hello-' world am I right";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        assert store.search("right").size() == 1;
        String one = "so this only has one";
        String three = "hat this has three ha";
        String two = "hat ah ha is two";
        InputStream stream2 = new ByteArrayInputStream((one.getBytes()));
        InputStream stream3 = new ByteArrayInputStream((two.getBytes()));
        InputStream stream4 = new ByteArrayInputStream((three.getBytes()));
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
        store.deleteAllWithPrefix("he");
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        store.deleteAll("is");
        //assert store.search("right").size() == 1;
        store.putDocument(stream4, uri5, DocumentStore.DocumentFormat.TXT);
        assert store.getDocument(uri5) != null;
        assert store.getDocument(uri3) == null;
        store.undo(uri3);
        assert store.getDocument(uri3) != null;
        store.undo();
        assert store.getDocument(uri5) == null;
        store.undo();
        assert store.getDocument(uri3) == null;
        assert store.search("right").size() == 0;
        store.undo();
        assert store.search("right").size() == 1;

    }

    @Test
    public void returnValueOfMethods() throws URISyntaxException, IOException{
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String helloWorld = "Hello-' world";
        InputStream stream1 = new ByteArrayInputStream((helloWorld.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        System.out.println(store.getDocument(uri).getWords());
        System.out.println(store.getDocument(uri).getDocumentTxt());
        Set<URI> nothing = store.deleteAllWithPrefix("ll");
        Set<URI> oneThing = store.deleteAllWithPrefix("wo");
        Set<URI> empty = store.deleteAllWithPrefix("wo");
        Set<URI> empty2 = store.deleteAllWithPrefix("h");
        assert nothing.size() == 0;
        assert oneThing.size() == 1;
        assert empty.size() == 0;
        assert empty2.size() == 0;
        String one = "so this only has one";
        String three = "hat this has three ha";
        String two = "hat ah ha is two";
        InputStream stream2 = new ByteArrayInputStream((one.getBytes()));
        InputStream stream3 = new ByteArrayInputStream((two.getBytes()));
        InputStream stream4 = new ByteArrayInputStream((three.getBytes()));
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream4, uri5, DocumentStore.DocumentFormat.TXT);

        List<Document> list = store.searchByPrefix("ha");
        for (Document doc : list){
            System.out.println(doc.getWords());

        }
        assert list.get(0).getDocumentTxt().equals(three);
        assert list.get(1).getDocumentTxt().equals(two);
        assert list.get(2).getDocumentTxt().equals(one);



    }

    @Test
    public void deleteAllWithPrefix() throws URISyntaxException, IOException{
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String threeShes = "She she she sells sea shells by the sea shore";
        InputStream stream1 = new ByteArrayInputStream((threeShes.getBytes()));
        String threeSells = "She sells SeLls sea shells by the sELLS sea shore";
        InputStream stream2 = new ByteArrayInputStream((threeSells.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);

        String zebra = "zebra ywo";
        InputStream stream3 = new ByteArrayInputStream((zebra.getBytes()));
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        List<Document> sheSearch = store.search("she");
        assert sheSearch.size() == 2;
        List<Document> sSearch = store.searchByPrefix("s");
        //System.out.println(sSearch);
        assert sSearch.size() == 2;
        List<Document> zebraSearch = store.search("zebra");
        assert store.getDocument(uri) != null;
        Set<URI> uris1And2 = store.deleteAllWithPrefix("s");
        assert uris1And2.size() == 2;
        assert uris1And2.contains(uri);
        assert uris1And2.contains(uri2);
        assert store.getDocument(uri) == null;
        List<Document> sheSearch2 = store.search("she");
        assert sheSearch2.size() == 0;
        List<Document> sSearch2 = store.search("s");
        assert sSearch2.size() == 0;
        assert store.getDocument(uri3) != null;
        List<Document> zeb = store.searchByPrefix("zE");
        List<Document> ywo = store.searchByPrefix("y");
        assert zeb.size() == 1;
        assert ywo.size() == 1;
        assert zeb.get(0).getDocumentTxt().equals(zebra);
        Set<URI> uri3Set = store.deleteAllWithPrefix("zebra");
        assert uri3Set.size() == 1;
        assert uri3Set.contains(uri3);
        assert store.getDocument(uri3) == null;
        assert store.search("ywo").size() == 0;
        assert store.searchByPrefix("z").size() == 0;
        //do some undos now
        store.undo(uri3);
        assert store.getDocument(uri3) != null;
        assert store.searchByPrefix("z").size() == 1;
        //System.out.println(store.search("ywo"));
        store.undo(uri3);
        assert store.getDocument(uri3) == null;
        //System.out.println(store.search("zebra"));
        //System.out.println(store.search("ywo"));
        assert store.search("ywo").size() == 0;
        store.undo(uri);
        assert store.getDocument(uri) != null;
        assert store.getDocument(uri2) == null;
        assert store.search("she").size() == 1;
        store.undo(uri2);
        assert store.getDocument(uri2) != null;
        assert store.search("she").size() == 2;
        store.undo();
        assert store.getDocument(uri2) == null;
        assert store.search("she").size() == 1;
        store.undo(uri);
        assert store.searchByPrefix("s").size() == 0;
        assert store.deleteAllWithPrefix("").size() == 0;





    }

    @Test
    public void undoOneThingInCommandSet() throws URISyntaxException, IOException {
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String threeShes = "She she she sells sea shells by the sea shore";
        InputStream stream1 = new ByteArrayInputStream((threeShes.getBytes()));
        String threeSells = "She sells SeLls sea shells by the sELLS sea shore";
        InputStream stream2 = new ByteArrayInputStream((threeSells.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);

        String zebra = "zebra";
        InputStream stream3 = new ByteArrayInputStream((zebra.getBytes()));
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        //System.out.println(store.stack.size() + " this should be three");
        List<Document> sheSearch = store.search("she");
        assert sheSearch.size() == 2;
        assert sheSearch.get(0).getDocumentTxt().equals(threeShes);
        assert sheSearch.get(1).getDocumentTxt().equals(threeSells);
        assert store.getDocument(uri).getDocumentTxt().equals(threeShes);
        assert store.getDocument(uri2).getDocumentTxt().equals(threeSells);
        assert store.getDocument(uri3).getDocumentTxt().equals(zebra);
        // System.out.println(store.stack.size() + " this should be three");
        Set<URI> deletedUris = store.deleteAll("ShE");
        //System.out.println(store.stack.size() + " this should be four");
        assert deletedUris.size() == 2;
        assert deletedUris.contains(uri2);
        assert store.getDocument(uri) == null;
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3).getDocumentTxt().equals(zebra);
        List<Document> sheSearch2 = store.search("she");
        assert sheSearch2.size() == 0;
        store.undo(uri); //make only this one be undeleted
        List<Document> sheSearch3 = store.search("she");
        assert sheSearch3.size() == 1;
        assert sheSearch3.get(0).getDocumentTxt().equals(threeShes);
        assert store.getDocument(uri) != null;
        assert store.getDocument(uri).getDocumentTxt().equals(threeShes);
        assert store.getDocument(uri2) == null;

        store.undo();//delete the rest of this command
        List<Document> sheSearch4 = store.search("sHe");
        assert sheSearch4.size() == 2;
        assert store.getDocument(uri).getDocumentTxt().equals(threeShes);
        assert store.getDocument(uri2).getDocumentTxt().equals(threeSells);
        store.undo();
        //store.undo();whyyyyy

        assert store.getDocument(uri3) == null;
    }

    @Test
    public void undoCommandSet() throws URISyntaxException, IOException {
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String threeShes = "She she she sells sea shells by the sea shore";
        InputStream stream1 = new ByteArrayInputStream((threeShes.getBytes()));
        String threeSells = "She sells SeLls sea shells by the sELLS sea shore";
        InputStream stream2 = new ByteArrayInputStream((threeSells.getBytes()));
        store.putDocument(stream1, uri, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);

        String zebra = "zebra";
        InputStream stream3 = new ByteArrayInputStream((zebra.getBytes()));
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        List<Document> sheSearch = store.search("she");
        assert sheSearch.size() == 2;
        assert sheSearch.get(0).getDocumentTxt().equals(threeShes);
        assert sheSearch.get(1).getDocumentTxt().equals(threeSells);
        assert store.getDocument(uri).getDocumentTxt().equals(threeShes);
        assert store.getDocument(uri2).getDocumentTxt().equals(threeSells);
        assert store.getDocument(uri3).getDocumentTxt().equals(zebra);
        Set<URI> deletedUris = store.deleteAll("ShE");
        assert deletedUris.size() == 2;
        assert deletedUris.contains(uri2);
        assert store.getDocument(uri) == null;
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3).getDocumentTxt().equals(zebra);
        List<Document> sheSearch2 = store.search("she");
        assert sheSearch2.size() == 0;
        store.undo();
        assert store.getDocument(uri).getDocumentTxt().equals(threeShes);
        assert store.getDocument(uri2).getDocumentTxt().equals(threeSells);
        List<Document> sheSearch3 = store.search("she");
        assert sheSearch3.size() == 2;
        store.undo(uri2);
        assert store.getDocument(uri2) == null;


    }

    @Test
    public void firstStageThreeTest() throws URISyntaxException, IOException {
        URI uri = new URI("A");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        String string = "She sells sea shells by the sea shore";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT);
        URI uri2 = new URI("B");
        String string2 = "she she she this has three shes";
        InputStream stream2 = new ByteArrayInputStream((string2.getBytes()));
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
        List<Document> listWithShe = store.search("SHE");
        System.out.println(listWithShe);
        for (Document document : listWithShe){
            System.out.println("these have she " + document.getDocumentTxt());
        }
        List<Document> listWithThis = store.search("ThiS");
        for (Document document : listWithThis){
            System.out.println("these have this " + document.getDocumentTxt());
        }

        //store.deleteAll("this");
        //System.out.println("should be nore more thises after this");
        store.deleteAll("she");
        System.out.println("no more things please");
        List<Document> listWithShes = store.search("she");
        for (Document document : listWithShes){
            System.out.println("these have she " + document.getDocumentTxt());
        }

    }

    @Test
    public void simpleBinaryTest() throws IOException, URISyntaxException {

        byte[] bytes = {0, 1, 2};
        URI uri = new URI("A");
        InputStream stream = new ByteArrayInputStream(bytes);
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.BINARY);
        Document doc = store.getDocument(uri);
        byte[] data = doc.getDocumentBinaryData();
        assert Arrays.equals(data, bytes);
        System.out.println(Arrays.toString(doc.getDocumentBinaryData()));
        assert doc.getKey().equals(uri);
        assert doc.getDocumentTxt() == null;
    }

    @Test
    public void testStringDocument() throws URISyntaxException, IOException {
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("B");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT);
        Document doc = store.getDocument(uri);
        assert doc.getDocumentBinaryData() == null;
        assert doc.getDocumentTxt().equals(string);
    }

    @Test
    public void undoDelete() throws URISyntaxException, IOException {
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("B");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT);
        Document doc = store.getDocument(uri);
        assert doc.getDocumentBinaryData() == null;
        assert doc.getDocumentTxt().equals(string);
        assert store.deleteDocument(uri);
        assert store.getDocument(uri) == null;
        //assert !store.deleteDocument(uri); this messes it up bc another call
        store.undo();
        Document backFromDead = store.getDocument(uri);
        assert backFromDead.equals(doc);
    }


    @Test
    public void undoNothingStuff() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        URI uri = new URI("B");
        assert store.getDocument(uri) == null;
        store.putDocument(null, uri, null);
        assert store.getDocument(uri) == null;
        store.deleteDocument(uri);
        assert store.getDocument(uri) == null;
        store.undo();
        assert store.getDocument(uri) == null;
        store.undo(uri);
        assert store.getDocument(uri) == null;

    }
    @Test
    public void undoBrandNewDocument() throws URISyntaxException, IOException {
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("B");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT);
        Document doc = store.getDocument(uri);
        assert doc.getDocumentBinaryData() == null;
        assert doc.getDocumentTxt().equals(string);
        assert store.getDocument(uri) != null;
        store.undo();
        assert store.getDocument(uri) == null;
    }

    @Test
    public void extensiveUndos() throws URISyntaxException, IOException {
        byte[] bytes = {0, 1, 2};
        URI uri = new URI("A");
        InputStream stream = new ByteArrayInputStream(bytes);
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.BINARY);
        String string = "string";
        InputStream stream2 = new ByteArrayInputStream((string.getBytes()));
        URI uri2 = new URI("B");
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
        InputStream stream3 = new ByteArrayInputStream(("hi".getBytes()));
        store.putDocument(stream3, uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(null, uri2, null);

        assert store.getDocument(uri2) == null;
        store.undo();
        assert store.getDocument(uri2).getDocumentTxt().equals("hi");
        store.undo();
        assert store.getDocument(uri2).getDocumentTxt().equals("string");
        store.undo();
        assert store.getDocument(uri2) == null;


        assert store.getDocument(uri).getDocumentBinaryData() != null;
        store.undo();
        assert store.getDocument(uri) == null;



    }

    @Test
    public void undoSpecificURI() throws IOException, URISyntaxException {
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        URI uri6 = new URI("F");
        URI uri7 = new URI("G");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        assert store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT) == 0;
        byte[] bytes = {0, 1, 2};
        InputStream stream2 = new ByteArrayInputStream(bytes);
        InputStream stream3 = new ByteArrayInputStream(bytes);
        assert store.getDocument(uri).getDocumentTxt().equals("string");
        assert store.putDocument(stream2, uri, DocumentStore.DocumentFormat.BINARY) != 0;
        assert store.getDocument(uri).getKey().equals(uri);
        assert store.getDocument(uri).getDocumentTxt() == null;
        InputStream stream4 = new ByteArrayInputStream(("A".getBytes()));
        InputStream stream5 = new ByteArrayInputStream(("B".getBytes()));
        InputStream stream6 = new ByteArrayInputStream(("C".getBytes()));
        InputStream stream7 = new ByteArrayInputStream(("D".getBytes()));
        InputStream stream8 = new ByteArrayInputStream(("E".getBytes()));
        InputStream stream9 = new ByteArrayInputStream(("F".getBytes()));
        InputStream stream10 = new ByteArrayInputStream(("G".getBytes()));
        InputStream stream11 = new ByteArrayInputStream(("H".getBytes()));
        assert store.putDocument(stream4, uri2, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream5, uri3, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream6, uri4, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream7, uri5, DocumentStore.DocumentFormat.TXT) == 0;
        store.deleteDocument(uri2);
        assert store.putDocument(stream8, uri6, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream9, uri7, DocumentStore.DocumentFormat.TXT) == 0;
        //assert store.getDocument(uri2).getDocumentTxt().equals("A");
        assert store.getDocument(uri3).getDocumentTxt().equals("B");
        assert store.getDocument(uri4).getDocumentTxt().equals("C");
        assert store.getDocument(uri5).getDocumentTxt().equals("D");
        assert store.getDocument(uri6).getDocumentTxt().equals("E");
        assert store.getDocument(uri7).getDocumentTxt().equals("F");

        assert store.getDocument(uri2) == null;
        store.undo(uri2);
        assert store.getDocument(uri2) != null;
        store.undo();
        store.undo(uri2);
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri6) != null;
        store.undo();
        assert store.getDocument(uri6) == null;
    }

    @Test
    public void simpleDeleteThroughPut() throws IOException, URISyntaxException {

        byte[] bytes = {0, 1, 2};
        URI uri = new URI("A");
        InputStream stream = new ByteArrayInputStream(bytes);
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.BINARY);
        assert store.getDocument(uri) != null;
        store.putDocument(null, uri, DocumentStore.DocumentFormat.BINARY);
        assert store.getDocument(uri) == null;

    }

    @Test
    public void delete() throws IOException, URISyntaxException {
        byte[] bytes = {0, 1, 2};
        URI uri = new URI("B");
        InputStream stream = new ByteArrayInputStream(bytes);
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        assert store.getDocument(uri) == null;
        assert store.deleteDocument(uri) == false;
        store.putDocument(stream, uri, DocumentStore.DocumentFormat.BINARY);
        assert store.getDocument(uri) != null;
        assert store.deleteDocument(uri) == true;
        assert store.getDocument(uri) == null;

    }

    @Test
    public void extensiveUseOfSameKey() throws IOException, URISyntaxException {
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("B");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        assert store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT) == 0;
        byte[] bytes = {0, 1, 2};
        InputStream stream2 = new ByteArrayInputStream(bytes);
        InputStream stream3 = new ByteArrayInputStream(bytes);
        assert store.putDocument(stream2, uri, DocumentStore.DocumentFormat.BINARY) != 0;
        assert store.putDocument(null, uri, DocumentStore.DocumentFormat.BINARY) != 0;
        assert store.putDocument(null, uri, DocumentStore.DocumentFormat.BINARY) == 0;
        assert store.deleteDocument(uri) == false;
        assert store.putDocument(stream3, uri, DocumentStore.DocumentFormat.BINARY) == 0;
        assert store.getDocument(uri) != null;
        assert store.deleteDocument(uri) == true;
        assert store.getDocument(uri) == null;

    }

    @Test
    public void fillTable() throws URISyntaxException, IOException {//just put in a ton of stuff and test it all
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        URI uri6 = new URI("F");
        URI uri7 = new URI("G");
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.setMaxDocumentCount(1);
        assert store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT) == 0;
        byte[] bytes = {0, 1, 2};
        InputStream stream2 = new ByteArrayInputStream(bytes);
        InputStream stream3 = new ByteArrayInputStream(bytes);
        assert store.getDocument(uri).getDocumentTxt().equals("string");
        assert store.putDocument(stream2, uri, DocumentStore.DocumentFormat.BINARY) != 0;
        assert store.getDocument(uri).getKey().equals(uri);
        assert store.getDocument(uri).getDocumentTxt() == null;
        InputStream stream4 = new ByteArrayInputStream(("A".getBytes()));
        InputStream stream5 = new ByteArrayInputStream(("B".getBytes()));
        InputStream stream6 = new ByteArrayInputStream(("C".getBytes()));
        InputStream stream7 = new ByteArrayInputStream(("D".getBytes()));
        InputStream stream8 = new ByteArrayInputStream(("E".getBytes()));
        InputStream stream9 = new ByteArrayInputStream(("F".getBytes()));
        InputStream stream10 = new ByteArrayInputStream(("G".getBytes()));
        InputStream stream11 = new ByteArrayInputStream(("H".getBytes()));
        assert store.putDocument(stream4, uri2, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream5, uri3, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream6, uri4, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream7, uri5, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream8, uri6, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream9, uri7, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.getDocument(uri2).getDocumentTxt().equals("A");
        assert store.getDocument(uri3).getDocumentTxt().equals("B");
        assert store.getDocument(uri4).getDocumentTxt().equals("C");
        assert store.getDocument(uri5).getDocumentTxt().equals("D");
        assert store.getDocument(uri6).getDocumentTxt().equals("E");
        assert store.getDocument(uri7).getDocumentTxt().equals("F");
        assert !store.getDocument(uri2).equals(store.getDocument(uri3));
        assert store.deleteDocument(uri2) == true;
        assert store.getDocument(uri2) == null;
        //assert store.deleteDocument(null) == false;
        //assert store.getDocument(null) == null;


    }

    @Test
    public void putDeleteAndExceptions() throws URISyntaxException, IOException {
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("A");
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.setMaxDocumentCount(1);
        assert store.putDocument(null, uri, null) == 0;
        assert store.deleteDocument(uri) == false;
        assert store.putDocument(null, uri, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.putDocument(null, uri, null) != 0;
        assert store.deleteDocument(uri) == false;
        //store.putDocument(null, null, DocumentStore.DocumentFormat.TXT);


    }

    @Test
    public void getOnNonexistentURI() throws URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.setMaxDocumentCount(1);
        URI uri = new URI("A");
        assert store.getDocument(uri) == null;

    }

    @Test
    public void undoExceptions() throws URISyntaxException, IOException {
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("B");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        assert store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT) == 0;
        URI uri2 = new URI("A");
        //store.undo(uri2);
        store.undo(uri);
        //store.undo(uri);
        //store.undo();

    }

    @Test
    public void tryCatch() throws URISyntaxException, IOException {
        String string = "string";
        InputStream stream = new ByteArrayInputStream((string.getBytes()));
        URI uri = new URI("A");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        assert store.putDocument(stream, uri, DocumentStore.DocumentFormat.TXT) == 0;
        assert store.deleteDocument(uri) == true;
        assert store.deleteDocument(uri) == false;
        URI uri2 = new URI("B");
        assert store.getDocument(uri) == null;


        try{
            store.undo(uri2);

        } catch (IllegalStateException e){
            System.out.println("caught");
        }
        store.undo(uri);
        assert store.getDocument(uri) == null;
        store.undo(uri);
        assert store.getDocument(uri) != null;
        assert store.getDocument(uri2) == null;
        store.undo(uri);
        assert store.getDocument(uri) == null;
    }



}
