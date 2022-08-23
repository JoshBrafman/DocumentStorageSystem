package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.Utils;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JudahStageFourDocumentStoreImplTest {

    //variables to hold possible values for doc1
    private URI uri1;
    private String txt1;
    private Path path1;
    private File file1;

    //variables to hold possible values for doc2
    private URI uri2;
    private String txt2;
    private Path path2;
    private File file2;
    //variables to hold possible values for doc3
    private URI uri3;
    private String txt3;
    private Path path3;
    private File file3;

    //variables to hold possible values for doc4
    private URI uri4;
    private String txt4;
    private Path path4;
    private File file4;

    private int bytes1;
    private int bytes2;
    private int bytes3;
    private int bytes4;


    @BeforeEach
    public void init() throws Exception {
        //init possible values for doc1
        this.uri1 = new URI("http://edu.yu.cs/com1320/project/ayeye1");
        this.txt1 = "This doc1 plain text string Computer Headphones";
        String string2 = uri1.getRawSchemeSpecificPart() + ".json";
        string2 = string2.substring(1);
        this.path1 = Paths.get(System.getProperty("user.dir"),string2);
        System.out.println("HERE "+ this.path1);


        //init possible values for doc2
        this.uri2 = new URI("http://edu.yu.cs/com1320/project/ayeye2");
        this.txt2 = "Text doc2 plain String";
        String string3 = uri2.getRawSchemeSpecificPart() + ".json";
        this.path2 = Paths.get(System.getProperty("user.dir"),string3);

        //init possible values for doc3
        this.uri3 = new URI("http://edu.yu.cs/com1320/project/ayeye3");
        this.txt3 = "This is the text of doc3";
        String string4 = uri3.getRawSchemeSpecificPart() + ".json";
        this.path3 = Paths.get(System.getProperty("user.dir"),string4);

        //init possible values for doc4
        this.uri4 = new URI("http://edu.yu.cs/com1320/project/ayeye4");
        this.txt4 = "This is the text of doc4";
        String string7 = uri4.getRawSchemeSpecificPart() + ".json";
        this.path4 = Paths.get(System.getProperty("user.dir"),string7);

        this.bytes1 = this.txt1.getBytes().length;
        this.bytes2 = this.txt2.getBytes().length;
        this.bytes3 = this.txt3.getBytes().length;
        this.bytes4 = this.txt4.getBytes().length;

        this.file1 = new File(String.valueOf(path1));

        this.file2 = new File(String.valueOf(path2));

        this.file3= new File(String.valueOf(path3));

       this.file4 = new File(String.valueOf(path4));
        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();

    }
    private DocumentStore getStoreWithTextAdded() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
        return store;
    }

    private DocumentStore getStoreWithBinaryAdded() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.BINARY);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.BINARY);
        return store;
    }

    /*
Every time a document is used, its last use time should be updated to the relative JVM time, as measured in nanoseconds (see java.lang.System.nanoTime().)
A Document is considered to be "used" whenever it is accessed as a result of a call to any part of DocumentStore’s public API. In other words, if it is "put",
or returned in any form as the result of any "get" or "search" request, or an action on it is undone via any call to either of the DocumentStore.undo methods.
     */

//    @Test
//    public void checkStack()throws IOException, URISyntaxException{
//        DocumentStoreImpl store = new DocumentStoreImpl(null);
////        URI uri1 = new URI("A");
////        URI uri2 = new URI("B");
////        URI uri3 = new URI("C");
////        URI uri4 = new URI("D");
////        URI uri5 = new URI("E");
//        String string1 = "first";
//        String string2 = "first second";
//        String string3 = "third";
//        String string4 = "fourth";
//        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
//        InputStream stream2 = new ByteArrayInputStream((string2.getBytes()));
//        InputStream stream3 = new ByteArrayInputStream((string3.getBytes()));
//        InputStream stream4 = new ByteArrayInputStream((string4.getBytes()));
//        InputStream stream7 = new ByteArrayInputStream((string2.getBytes()));
//        byte[] array1 = {0, 1, 1};
//        InputStream stream5 = new ByteArrayInputStream(array1);
//        InputStream stream9 = new ByteArrayInputStream((string1.getBytes()));
//        InputStream stream10 = new ByteArrayInputStream((string2.getBytes()));
//
//
//        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);
//
//
//
//        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
//        store.deleteDocument(uri2);
//        store.putDocument(stream7, uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
//        store.deleteAll("first");
//        store.putDocument(stream9, uri2, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(stream10, uri1, DocumentStore.DocumentFormat.TXT);
//        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.TXT);
//        store.getDocument(uri1);
//        store.getDocument(uri3);
//        store.getDocument(uri4);
//        store.setMaxDocumentCount(3);
//        assert Files.exists(path2);
//        assert store.getDocument(uri2) != null;
//
//        store.undo();  //got rid of put on 4
//        store.undo(); //got rid of put on 1
//        //assert store.getDocument(uri1) == null;
//        assert Files.exists(path1);
//        store.undo();
//        assert store.getDocument(uri1) != null;
//        assert !Files.exists(path1);
//        assert Files.exists(path2);
//        assert store.getDocument(uri2) != null;
//        store.undo();
//        store.undo();
//        assert store.getDocument(uri2) == null;
//        //store.undo();
//
//
//
//        //store.undo(uri2); //this should throw exception. good
//
//
//    }

    @Test
    public void searchForDocOnDisk()throws IOException{
        DocumentStore store = new DocumentStoreImpl();
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream("one".getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("one two".getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("one two three".getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream("one two three four".getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert !Files.exists(path4);
        List<Document> list = store.searchByPrefix("o");
        for (Document doc : list){
            System.out.println(doc.getWordMap());
        }
        store.getDocument(uri1);
        assert Files.exists(path4);
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        store.searchByPrefix("f");
        assert !Files.exists(path4);
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);

    }

    @Test
    public void undoSomethingNotOnDisk()throws IOException{
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(4);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.undo();
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.deleteDocument(uri2);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        assert store.getDocument(uri2) == null;
        store.undo();
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        assert store.getDocument(uri2) != null;
        store.deleteAllWithPrefix("d");
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        assert store.getDocument(uri1) == null;
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3) == null;
        assert store.getDocument(uri4) == null;
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.undo();
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri4) == null;
    }
    @Test
    public void binaryArrays()throws IOException{
        DocumentStore store = new DocumentStoreImpl();
        byte[] bytes = {0, 1, 0};
        //URI uri = new URI("A");
        InputStream stream = new ByteArrayInputStream(bytes);
        store.setMaxDocumentCount(0);
        assert !Files.exists(path1);
        store.putDocument(stream, uri1, DocumentStore.DocumentFormat.BINARY);
        assert Files.exists(path1);
        Document doc = store.getDocument(uri1);
        System.out.println(Arrays.toString(doc.getDocumentBinaryData()));
    }


    @Test
    public void differentDirectory()throws IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(new File("baseDirectory"));
        //DocumentStore store = new DocumentStoreImpl(null);
        String string2 = "baseDirectory" + uri1.getRawSchemeSpecificPart() + ".json";
        Path path1 = Paths.get(System.getProperty("user.dir"),string2);
        String string3 = "baseDirectory" + uri2.getRawSchemeSpecificPart() + ".json";
        Path path2 = Paths.get(System.getProperty("user.dir"),string3);
        String string4 = "baseDirectory" + uri3.getRawSchemeSpecificPart() + ".json";
        Path path3 = Paths.get(System.getProperty("user.dir"),string4);
        this.file1 = new File(String.valueOf(path1));

        this.file2 = new File(String.valueOf(path2));

        this.file3= new File(String.valueOf(path3));
        file1.delete();
        file2.delete();
        file3.delete();
        store.setMaxDocumentCount(2);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        Set<URI> set = store.deleteAll("doc2");
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert set.size() == 1;
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert store.getDocument(uri1).getKey().equals(uri1);
        assert store.getDocument(uri1).getDocumentTxt().equals(txt1);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert Files.exists(path3);
        assert store.deleteAll("doc3").size() == 1;
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) == null;
        store.undo();
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert Files.exists(path3);//i just haven't fixed undo yet
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri1) != null;
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert !Files.exists(path3);
    }

    @Test
    public void tooMuchBinaryDataTest() throws URISyntaxException, IOException {//haven't tested binary data of string
        DocumentStoreImpl store = new DocumentStoreImpl();
//        URI uri1 = new URI("A");
//        URI uri2 = new URI("B");
//        URI uri3 = new URI("C");
        byte[] array1 = {0, 1, 1};
        byte[] array2 = {0, 1, 0};

        InputStream stream = new ByteArrayInputStream(array1);
        InputStream stream2 = new ByteArrayInputStream(array2);
        store.putDocument(stream, uri1, DocumentStore.DocumentFormat.BINARY);
        assert store.getDocument(uri1) != null;
        //store.setMaxDocumentBytes(5);
        store.putDocument(stream2, uri1, DocumentStore.DocumentFormat.BINARY);
        assert store.getDocument(uri1) != null;
        //ok maybe do a lot more testing cuz wow that was the same uri and i don't know what I'm doing
        byte[] array3 = {1};
        InputStream stream3 = new ByteArrayInputStream(array3);
        store.putDocument(stream3, uri2, DocumentStore.DocumentFormat.BINARY);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        String string1 = "f";
        InputStream stream4 = new ByteArrayInputStream((string1.getBytes()));
        store.putDocument(stream4, uri3, DocumentStore.DocumentFormat.TXT);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) != null;

        store.setMaxDocumentCount(1);
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert !Files.exists(path3);

       Document doc1 = store.getDocument(uri1);
        assert !Files.exists(path1);
        System.out.println(Arrays.toString(doc1.getDocumentBinaryData()) + " should be zero 1 1");
        assert store.getDocument(uri2) != null;
        Document doc2 = store.getDocument(uri2);
        assert !Files.exists(path2);
        System.out.println(Arrays.toString(doc2.getDocumentBinaryData()) + " should be zero 1 0");
        Document doc3 = store.getDocument(uri3);
        System.out.println(Arrays.toString(doc3.getDocumentBinaryData()) + " this should be null");



    }
    @Test
    public void oneExtraDocument() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
//        URI uri1 = new URI("A");
//        URI uri2 = new URI("B");
//        URI uri3 = new URI("C");
        String string1 = "first";
        String string2 = "second";
        String string3 = "third";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        InputStream stream2 = new ByteArrayInputStream((string2.getBytes()));
        InputStream stream3 = new ByteArrayInputStream((string3.getBytes()));
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);
        store.setMaxDocumentCount(2);
        assert store.getDocument(uri1) != null;
        assert store.search("first").size() == 1;
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
        assert store.search("second").size() == 1;
        assert store.getDocument(uri2) != null;
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path1);
        assert !Files.exists(path3);
        assert !Files.exists(path2);
        assert store.getDocument(uri3) != null;
       // assert store.getDocument(uri1) == null;
        assert store.getDocument(uri2) != null;
        //assert store.search("first").size() == 0;
        assert store.search("second").size() == 1;
        assert store.search("third").size() == 1;
        //URI uri4 = new URI("D");//btw what would happen if I use the same URI as another one
        String string4 = "fourth";
        InputStream stream4 = new ByteArrayInputStream((string4.getBytes()));
        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.TXT);
        assert store.getDocument(uri4) != null;
        assert store.search("fourth").size() == 1;
        assert Files.exists(path2);
        //assert store.getDocument(uri2) == null;
       // assert store.search("second").size() == 0;
        assert store.search("third").size() == 1; //now four should be least recently used
        String fourth = "fourth";
        InputStream stream5 = new ByteArrayInputStream((fourth.getBytes()));

        store.putDocument(stream5, uri1, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert !Files.exists(path3);
        assert Files.exists(path4);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri4) != null;
        assert store.getDocument(uri3) != null;
        //deal with making sure the counting in hashtable is all right with all posibilities






    }
@Test
public void noGenericUndosAfterDeletingForMemory() throws IOException, URISyntaxException {//if a document is normally deleted it doesn't countn towards space right
    DocumentStoreImpl store = new DocumentStoreImpl(null);
    URI uri1 = new URI("A");
    URI uri2 = new URI("B");
    URI uri3 = new URI("C");
    String string1 = "first";
    String string2 = "second";
    String string3 = "third";
    InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
    InputStream stream2 = new ByteArrayInputStream((string2.getBytes()));
    InputStream stream3 = new ByteArrayInputStream((string3.getBytes()));
    store.setMaxDocumentCount(2);
    store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);
    store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
    InputStream stream5 = new ByteArrayInputStream(("hi".getBytes()));
    assert store.getDocument(uri1) != null;
    store.deleteDocument(uri1);
    assert store.getDocument(uri1) == null;
    store.putDocument(stream5, uri1, DocumentStore.DocumentFormat.TXT);
    assert store.getDocument(uri1) != null;
    store.search("second");
    store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);//this should remove uri1
    store.undo(uri2);//look up what these things should do
    store.undo(uri3);

    //store.undo(uri1);//should throw exception


    //URI uri4 = new URI("D");//btw what would happen if I use the same URI as another one
    //String string4 = "fourth";
    //InputStream stream4 = new ByteArrayInputStream((string4.getBytes()));
    //store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.TXT);

}
@Test
public void notInCommandSetUndoAfterDeletingForMemory() throws IOException, URISyntaxException{
    DocumentStoreImpl store = new DocumentStoreImpl(null);
//    URI uri1 = new URI("A");
//    URI uri2 = new URI("B");
//    URI uri3 = new URI("C");
//    URI uri4 = new URI("D");
//    URI uri5 = new URI("E");
    String string1 = "first";
    String string2 = " first second";
    String string3 = "first";
    String string4 = "first second";
    InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
    InputStream stream2 = new ByteArrayInputStream((string2.getBytes()));
    InputStream stream3 = new ByteArrayInputStream((string3.getBytes()));
    InputStream stream4 = new ByteArrayInputStream((string4.getBytes()));
    store.setMaxDocumentCount(2);
    store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);
    store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
    assert store.deleteAll("first").size() == 2;
    store.putDocument(stream3, uri1, DocumentStore.DocumentFormat.TXT);
    store.putDocument(stream4, uri2, DocumentStore.DocumentFormat.TXT);
    byte[] array1 = {0, 1, 1};
    byte[] array2 = {0, 1, 0};

    InputStream stream5 = new ByteArrayInputStream(array1);
    InputStream stream6 = new ByteArrayInputStream(array2);
    //assert store.getDocument(uri1) != null;
    store.putDocument(stream5, uri3, DocumentStore.DocumentFormat.BINARY);
    assert Files.exists(path1);
    assert !Files.exists(path3);
    assert !Files.exists(path2);
    assert store.getDocument(uri3) != null;
    assert store.getDocument(uri2) != null;

    //assert store.deleteAll("first").size() == 1; this was good before check back later to make sure still good
    store.undo();
    assert store.getDocument(uri3) == null;
    store.undo();
    assert store.getDocument(uri2) == null;
    assert Files.exists(path1);
    assert !Files.exists(path3);
    assert !Files.exists(path2);
    store.undo();
    store.undo();//this should undo just uri2 from delete all no
    assert !Files.exists(path2);
    assert store.getDocument(uri2) != null;
    assert Files.exists(path1);
    //assert store.getDocument(uri1) == null;
    store.undo();
    assert store.getDocument(uri2) == null;
    //assert Files.exists(path2);
    //store.undo();


}

    @Test
    public void updateTimeWithUndoOnGenericCommand()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
//        URI uri1 = new URI("A");
//        URI uri2 = new URI("B");
//        URI uri3 = new URI("C");
//        URI uri4 = new URI("D");
//        URI uri5 = new URI("E");
        String string1 = "first";
        String string2 = "  second";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        InputStream stream2 = new ByteArrayInputStream((string2.getBytes()));
        byte[] array1 = {0, 1, 1};
        InputStream stream3 = new ByteArrayInputStream(array1);
        store.setMaxDocumentCount(2);
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);
        Document document1 = store.getDocument(uri1);
        store.putDocument(stream2, uri1, DocumentStore.DocumentFormat.TXT);
        Document documentThatWillDisappear = store.getDocument(uri1);
        store.putDocument(stream3, uri2, DocumentStore.DocumentFormat.BINARY);
        //System.out.println(documentThatWillDisappear.getLastUseTime());
        Document document2 = store.getDocument(uri2);
        assert document1.compareTo(document2) <0;
        assert store.getDocument(uri1).getDocumentTxt().equals(string2);
        assert store.getDocument(uri2).getDocumentTxt() == null;



        assert documentThatWillDisappear.compareTo(document2) <0;
        //System.out.println(documentThatWillDisappear.getLastUseTime());
        store.undo(uri1);//now uri2 is least recently used
        //System.out.println(documentThatWillDisappear.getLastUseTime());
        //assert store.getDocument(uri1).getDocumentTxt().equals(string1);
        //assert store.getDocument(uri2) != null; these are right just don't want to change times
        //assert documentThatWillDisappear.compareTo(document2) <0;
        assert document1.compareTo(document2) >0;

        //System.out.println(document1.getLastUseTime());
        //System.out.println(document2.getLastUseTime());
        //System.out.println(documentThatWillDisappear.getLastUseTime());

        //assert store.getDocument(uri1).getDocumentTxt().equals(string1); this would mess with timing

        String string3 = "third";
        InputStream stream4 = new ByteArrayInputStream((string3.getBytes()));
        store.putDocument(stream4, uri3, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path3);
        assert store.getDocument(uri3).getDocumentTxt().equals(string3);
        assert Files.exists(path2);
        assert !Files.exists(path1);
        assert store.getDocument(uri1).getDocumentTxt().equals(string1);
        System.out.println("hi");

        //OK I DID A LOT OF CHANGES TO DOCUMENT STORE BECAUSE OF THE TEST SO DEFINITELY GO THROUGH EVERYTHING. WHEN I DO THE UNDO
        //IN THIS TEST WHEN I TRY TO REMOVE FROM HEAP WHAT WAS PREVIOUSLY AT URI1 IN REALITY WHAT I DO IS REMOVE THE
        //URI2 FROM THE HEAP. WHAT THIS DOES IS MAKE IT SO I WILL NEVER COMPLETELYREMOVE FROM ABSOLUTELY EVERYTHING URI2
        //BECAUSE IT'S NO LONGER IN THE HEAP FOR ME TO GRAB IT. THIS IS WHAT I HAVE TO FIGURE OUT.
        //WHY IS IT THAT WHEN I REMOVE FROM HEAP IN THAT UNDO IT'S SETTING THE TIME OF ONE DOCUMENT TO ZERO
        //BUT THEN DELETING ANOTHER DOCUMENT I GUESS IT'S BECAUSE THAT FIRST DOCUMENT WAS NEVER IN THE HEAP



    }
    @Test
    public void updateTimeWithUndoOnCommandSet()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
//        URI uri1 = new URI("A");
//        URI uri2 = new URI("B");
//        URI uri3 = new URI("C");
//        URI uri4 = new URI("D");
//        URI uri5 = new URI("E");
        String string1 = "first";
        String string2 = "second";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        InputStream stream2 = new ByteArrayInputStream((string2.getBytes()));
        byte[] array1 = {0, 1, 1};
        InputStream stream3 = new ByteArrayInputStream(array1);
        store.setMaxDocumentCount(2);

        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);

        store.deleteAll("first");

        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);

        store.undo(uri1);

        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.BINARY);
//        assert store.getDocument(uri3) != null;
//        assert store.getDocument(uri1) != null;
//        assert store.getDocument(uri2) == null;
        assert Files.exists(path2);
        assert !Files.exists(path1);
        assert !Files.exists(path3);

    }
    @Test
    public void setLimitAfterBeingOver()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
//        URI uri1 = new URI("A");
//        URI uri2 = new URI("B");
//        URI uri3 = new URI("C");
//        URI uri4 = new URI("D");
//        URI uri5 = new URI("E");
        byte[] array1 = {0, 1, 1};
        InputStream stream1 = new ByteArrayInputStream(array1);
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.BINARY);
        byte[] array2 = {0, 1, 1};
        InputStream stream2 = new ByteArrayInputStream(array1);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.BINARY);
        byte[] array3 = {0, 1, 1};
        InputStream stream3 = new ByteArrayInputStream(array1);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.BINARY);
        byte[] array4 = {0, 1, 1};
        InputStream stream4 = new ByteArrayInputStream(array1);
        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.BINARY);
        store.setMaxDocumentBytes(6);
        store.setMaxDocumentCount(1);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri4) != null;
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert !Files.exists(path4);



    }
    @Test
    public void makingLimitZeroAfterAddingDocs()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
//        URI uri1 = new URI("A");
//        URI uri2 = new URI("B");
//        URI uri3 = new URI("C");
//        URI uri4 = new URI("D");
//        URI uri5 = new URI("E");
        byte[] array1 = {0, 1, 1};
        InputStream stream1 = new ByteArrayInputStream(array1);
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.BINARY);
        byte[] array2 = {0, 1, 1};
        InputStream stream2 = new ByteArrayInputStream(array1);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.BINARY);
        byte[] array3 = {0, 1, 1};
        InputStream stream3 = new ByteArrayInputStream(array1);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.BINARY);
        byte[] array4 = {0, 1, 1};
        InputStream stream4 = new ByteArrayInputStream(array1);
        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.BINARY);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.setMaxDocumentCount(5);
        store.setMaxDocumentBytes(0);
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert Files.exists(path4);

        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri4) != null;
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert Files.exists(path4);
    }
    @Test
    public void makingLimitZeroBeforeAddingDocs()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
//        URI uri1 = new URI("A");
//        URI uri2 = new URI("B");
//        URI uri3 = new URI("C");
//        URI uri4 = new URI("D");
//        URI uri5 = new URI("E");
        store.setMaxDocumentCount(0);
        store.setMaxDocumentBytes(9);
        byte[] array1 = {0, 1, 1};
        InputStream stream1 = new ByteArrayInputStream(array1);
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.BINARY);
        assert Files.exists(path1);

        assert store.getDocument(uri1) != null;
        assert Files.exists(path1);
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3) == null;
        assert store.getDocument(uri4) == null;
        assert Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        byte[] array2 = {0, 1, 1};
        InputStream stream2 = new ByteArrayInputStream(array1);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.BINARY);
        byte[] array3 = {0, 1, 1};
        InputStream stream3 = new ByteArrayInputStream(array1);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.BINARY);
        byte[] array4 = {0, 1, 1};
        InputStream stream4 = new ByteArrayInputStream(array1);
        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.BINARY);

        assert Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert Files.exists(path4);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri4) != null;
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert Files.exists(path4);
    }

    @Test
    public void undo()throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
//        URI uri1 = new URI("A");
//        URI uri2 = new URI("B");
//        URI uri3 = new URI("C");
//        URI uri4 = new URI("D");
//        URI uri5 = new URI("E");
        byte[] array1 = {0, 1, 1};
        InputStream stream1 = new ByteArrayInputStream(array1);
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.BINARY);
        byte[] array2 = {0, 1, 1};
        InputStream stream2 = new ByteArrayInputStream(array1);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.BINARY);
        byte[] array3 = {0, 1, 1};
        InputStream stream3 = new ByteArrayInputStream(array1);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.BINARY);
        byte[] array4 = {0, 1, 1};
        InputStream stream4 = new ByteArrayInputStream(array1);
        store.putDocument(stream4, uri1, DocumentStore.DocumentFormat.BINARY);
        store.undo();
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) != null;
        store.getDocument(uri1);
        store.setMaxDocumentCount(1);
        assert store.getDocument(uri1) != null;
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert !Files.exists(path4);
        //assert store.getDocument(uri1).getDocumentBinaryData().equals(stream1);
        System.out.println(store.getDocument(uri1).getDocumentBinaryData());
        System.out.println(stream1);

        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert !Files.exists(path4);
       store.getDocument(uri1);
        store.getDocument(uri1);
        store.getDocument(uri1);
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert Files.exists(path3);
        assert !Files.exists(path4);
        store.getDocument(uri2);
        assert !Files.exists(path2);
        store.getDocument(uri2);
        store.getDocument(uri2);
        store.getDocument(uri2);
        assert Files.exists(path1);
        assert !Files.exists(path2);
        assert Files.exists(path3);
        assert !Files.exists(path4);

       // assert store.getDocument(uri2) == null;
        //assert store.getDocument(uri3) == null;
    }
    @Test
    public void shouldNeverGoOver() throws IOException{
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(4);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()),this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()),this.uri4, DocumentStore.DocumentFormat.TXT);
        store.setMaxDocumentCount(4);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.getDocument(uri1);
        store.getDocument(uri1);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.getDocument(uri1);
        store.getDocument(uri2);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.getDocument(uri3);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.getDocument(uri3);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.deleteAllWithPrefix("d");
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.undo();
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.undo();
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);

    }


    @Test
    public void deleteAll()throws IOException{
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(2);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        Set<URI> set = store.deleteAll("doc2");
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert set.size() == 1;
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert store.getDocument(uri1).getKey().equals(uri1);
        assert store.getDocument(uri1).getDocumentTxt().equals(txt1);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert Files.exists(path3);
        assert store.deleteAll("doc3").size() == 1;
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) == null;
        store.undo();
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert Files.exists(path3);//i just haven't fixed undo yet
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri1) != null;
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert !Files.exists(path3);




    }


    @Test
    public void iDontEvenKnow()throws IOException{
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(2);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert store.deleteDocument(uri1);
        assert store.getDocument(uri1) == null;
        assert !store.deleteDocument(uri1);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        assert store.getDocument(uri4) != null;
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.undo(uri1);
        store.undo(uri1);
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert !Files.exists(path4);
        assert Files.exists(path3);//I ADDED GET BACK UNDER LIMIT TO UNDO SO IF I PUT SOMETHING IN IT WILL KICK ANOTHER OUT


    }

    @Test
    public void weirdUndo() throws IOException{
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path1);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert store.getDocument(uri1).getDocumentTxt().equals(txt3);
        assert store.search("doc3").size() == 1;
        assert store.search("doc1").size() == 0;
        assert store.getDocument(uri1).getDocumentTxt().equals(txt3);
        store.undo(uri1);
        assert store.search("doc3").size() == 0;
        assert store.search("doc1").size() == 1;
        assert store.getDocument(uri1).getDocumentTxt().equals(txt1);
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert store.search("doc2").size() == 1;
        assert Files.exists(path1);
        assert !Files.exists(path2);
        assert store.searchByPrefix("doc1").size() == 1;
        assert !Files.exists(path1);
        assert Files.exists(path2);
        assert store.search("doc2").size() == 1;
        assert Files.exists(path1);
        assert !Files.exists(path2);
        assert store.deleteDocument(uri1);
        assert !Files.exists(path1);
        assert store.getDocument(uri1) == null;
        assert store.search("doc1").size() == 0;
        assert store.deleteDocument(uri1) == false;
        assert !Files.exists(path2);
        store.undo(uri1);
        assert !Files.exists(path1);
        assert store.search("doc1").size() == 0;
        store.undo(uri1);
        assert Files.exists(path1);
        assert !Files.exists(path2);
        assert store.searchByPrefix("doc1").size() == 1;
        assert !Files.exists(path1);//not sure why - check what's up with btree maybe. also i didn't change the undo stuff in the other three Functions
        assert Files.exists(path2);



    }


    @Test
    public void sameURI() throws IOException{
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        long first = store.getDocument(uri1).getLastUseTime();

        assert !Files.exists(path1);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path1);
        assert !Files.exists(path2);
        Document doc = store.getDocument(uri1);
        assert !Files.exists(path1);
        assert Files.exists(path2);
        long second = doc.getLastUseTime();
        assert first < second;
        assert doc.getDocumentTxt().equals(txt2);
        assert store.search(txt1).size() == 0;
        //store.undo(uri1);//should this be throwing an exception?
        //assert store.search(txt1).size() == 1;
        //assert Files.exists(path2);
        //assert !Files.exists(path1);



    }

    @Test
    public void stage4TestSetDocLastUseTimeOnGet() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.BINARY);
        Document doc = store.getDocument(this.uri1);
        long first = doc.getLastUseTime();
        doc = store.getDocument(this.uri1);
        long second = doc.getLastUseTime();
        //was last use time updated on the put?
        assertTrue(first < second,"last use time should be changed when the DocumentStore.getDocument method is called");
    }

    @Test
    public void stage4TestSetDocLastUseTimeOnPut() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        long before = System.nanoTime();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        Document doc = store.getDocument(this.uri1);
        //was last use time set on the put?
        assertTrue(before < doc.getLastUseTime(),"last use time should be after the time at which the document was put");
    }
    @Test
    public void stage4TestUpdateDocLastUseTimeOnOverwrite() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        //was last use time updated on the put?
        long before = System.nanoTime();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.BINARY);
        Document doc = store.getDocument(this.uri1);
        assertTrue(before < doc.getLastUseTime(),"last use time should be after the time at which the document was put");
        before = System.nanoTime();
        //was last use time updated on overwrite?
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri1, DocumentStore.DocumentFormat.BINARY);
        Document doc2 = store.getDocument(this.uri1);
        assertTrue(before < doc2.getLastUseTime(),"last use time should be after the time at which the document was overwritten");
    }

    @Test
    public void stage4TestUpdateDocLastUseTimeOnSearch() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        long before = System.nanoTime();
        //this search should return the contents of the doc at uri1
        List<Document> results = store.search("Computer");
        Document doc = store.getDocument(this.uri1);
        //was last use time updated on the search?
        assertTrue(before < doc.getLastUseTime(),"last use time of search result doc should be after the time at which the document was put");
    }
    @Test
    public void stage4TestUpdateDocLastUseTimeOnSearchByPrefix() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        long before = System.nanoTime();
        //this search should return the contents of the doc at uri1
        List<Document> results = store.searchByPrefix("Comput");
        Document doc = store.getDocument(this.uri1);
        //was last use time updated on the searchByPrefix?
        assertTrue(before < doc.getLastUseTime(),"last use time of search result should be after the time at which the document was put");
    }

    /**
     * test max doc count via put
     */
    @Test
    public void stage4TestMaxDocCountViaPut() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(2);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.BINARY);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.BINARY);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.BINARY);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.BINARY);
        //uri1 and uri2 should both be gone, having been pushed out by 3 and 4
        //assertNull(store.getDocument(this.uri1),"uri1 should've been pushed out of memory when uri3 was inserted");
        //assertNull(store.getDocument(this.uri2),"uri2 should've been pushed out of memory when uri4 was inserted");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assertNotNull(store.getDocument(this.uri4),"uri4 should still be in memory");
        //MAKE SURE DOc1 and 2 are on disk
        //THEY ARENT, GOT RID OF ASSERT NULL SO IT WILL THROW EXCEPTION SO WILL FIGURE OUT WHY.
        //IT TRIED TO MAKE THE DOCS 1 AND 2 WHICH IS GOOD, IT JUST FAILED FOR SOME REASON
        //ok when i made it hello1and2 it worked. why didn't it work when it was DOc1? case doesn't matter?
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);

        assert file1.delete() == true;
        assert file2.delete() == true;
        assert file3.delete() == false;
        assert file4.delete() == false;
    }

    /**
     * test max doc count via search
     */
    @Test
    public void stage4TestMaxDocCountViaSearch() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(3);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        //all 3 should still be in memory
        //assertNotNull(store.getDocument(this.uri1),"uri1 should still be in memory");
        //assertNotNull(store.getDocument(this.uri2),"uri2 should still be in memory");
        //assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        //"touch" uri1 via a search
        store.search("doc1");
        //add doc4, doc2 should be pushed out, not doc1
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        //assertNotNull(store.getDocument(this.uri1),"uri1 should still be in memory");
        //assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        //assertNotNull(store.getDocument(this.uri4),"uri4 should still be in memory");
        assert !Files.exists(path1);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        //uri2 should've been pushed out of memory
        //assertNull(store.getDocument(this.uri2),"uri2 should not still be in memory");
        assert Files.exists(path2);
        List<Document> list = store.searchByPrefix("doc2");
        assert Files.exists(path3);
        assert !Files.exists(path2);
        assert list.get(0).getDocumentTxt().equals(txt2);
        assert store.search("doc2").get(0).getDocumentTxt().equals(txt2);
        assert store.getDocument(uri2).getDocumentTxt().equals(txt2);
        assert Files.exists(path3);
        assert !Files.exists(path2);
        assert !Files.exists(path1);
        assert !Files.exists(path4);
    }

    /**
     * test undo after going over max doc count
     */
    @Test
    public void stage4TestUndoAfterMaxDocCount() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(3);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.BINARY);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.BINARY);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.BINARY);
        //all 3 should still be in memory
        assertNotNull(store.getDocument(this.uri1),"uri1 should still be in memory");
        assertNotNull(store.getDocument(this.uri2),"uri2 should still be in memory");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        //add doc4, doc1 should be pushed out
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.BINARY);
        assertNotNull(store.getDocument(this.uri2),"uri2 should still be in memory");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assertNotNull(store.getDocument(this.uri4),"uri4 should still be in memory");
        assert !Files.exists(path4);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        //uri1 should've been pushed out of memory
        //assertNull(store.getDocument(this.uri1),"uri1 should've been pushed out of memory");
        assert Files.exists(path1);
        //undo the put - should eliminate doc4, and only uri2 and uri3 should be left
        store.undo();
        assertNull(store.getDocument(this.uri4),"uri4 should be gone due to the undo");
        //assertNull(store.getDocument(this.uri1),"uri1 should NOT have reappeared once booted out of memory");
        assert Files.exists(path1);
        assertNotNull(store.getDocument(this.uri2),"uri2 should still be in memory");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
    }


    /**
     * test max doc bytes via put
     */
    @Test
    public void stage4TestMaxDocBytesViaPut() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentBytes(this.bytes1 + this.bytes2);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        //uri1 and uri2 should both be gone, having been pushed out by 3 and 4
        //assertNull(store.getDocument(this.uri1),"uri1 should've been pushed out of memory when uri3 was inserted");
        //assertNull(store.getDocument(this.uri2),"uri2 should've been pushed out of memory when uri4 was inserted");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assertNotNull(store.getDocument(this.uri4),"uri4 should still be in memory");
        assert Files.exists(path1);
        assert Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
    }

    /**
     * test max doc bytes via search
     */
    @Test
    public void stage4TestMaxDocBytesViaSearch() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentBytes(this.bytes1 + this.bytes2 + this.bytes3 + 10);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        //all 3 should still be in memory
        assertNotNull(store.getDocument(this.uri1),"uri1 should still be in memory");
        assertNotNull(store.getDocument(this.uri2),"uri2 should still be in memory");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        //"touch" uri1 via a search
        store.search("doc1");
        //add doc4, doc2 should be pushed out, not doc1
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        assertNotNull(store.getDocument(this.uri1),"uri1 should still be in memory");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assertNotNull(store.getDocument(this.uri4),"uri4 should still be in memory");
        assert !Files.exists(path1);
        assert !Files.exists(path4);
        assert !Files.exists(path3);
        assert Files.exists(path2);



        //uri2 should've been pushed out of memory
        //assertNull(store.getDocument(this.uri2),"uri2 should've been pushed out memory");
        //make sure Doc2 is in a file and only Doc2
        //SUCCESS
    }

    /**
     * test undo after going over max bytes
     */
    @Test
    public void stage4TestUndoAfterMaxBytes() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentBytes(this.bytes1 + this.bytes2 + this.bytes3);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        //all 3 should still be in memory
        assertNotNull(store.getDocument(this.uri1),"uri1 should still be in memory");
        assertNotNull(store.getDocument(this.uri2),"uri2 should still be in memory");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assert !Files.exists(path1);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        //add doc4, doc1 should be pushed out
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        assertNotNull(store.getDocument(this.uri2),"uri2 should still be in memory");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assertNotNull(store.getDocument(this.uri4),"uri4 should still be in memory");
        assert !Files.exists(path4);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        //uri1 should've been pushed out of memory
        //assertNull(store.getDocument(this.uri1),"uri1 should've been pushed out of memory");
        assert Files.exists(path1);
        //undo the put - should eliminate doc4, and only uri2 and uri3 should be left
        store.undo();
        assertNull(store.getDocument(this.uri4),"uri4 should be gone due to the undo");
        assert !Files.exists(path4);
        assertNotNull(store.getDocument(this.uri2),"uri2 should still be in memory");
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        //assertNull(store.getDocument(this.uri1),"uri1 should NOT reappear after being pushed out of memory");
        assert Files.exists(path1);
    }

    /**
     * test going over max docs only when both max docs and max bytes are set
     */
    @Test
    public void stage4TestMaxDocsWhenDoubleMaxViaPut() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentBytes(this.bytes1*10);
        store.setMaxDocumentCount(2);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        //uri1 and uri2 should both be gone, having been pushed out by 3 and 4
        //assertNull(store.getDocument(this.uri1),"uri1 should've been pushed out of memory when uri3 was inserted");
        //assertNull(store.getDocument(this.uri2),"uri2 should've been pushed out of memory when uri4 was inserted");
        assert Files.exists(path1);
        assert Files.exists(path2);
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assertNotNull(store.getDocument(this.uri4),"uri4 should still be in memory");
        assert !Files.exists(path3);
        assert !Files.exists(path4);
    }

    /**
     * test going over max bytes only when both max docs and max bytes are set
     */
    @Test
    public void stage4TestMaxBytesWhenDoubleMaxViaPut() throws IOException {
        DocumentStore store = new DocumentStoreImpl(null);
        store.setMaxDocumentBytes(this.bytes1 + this.bytes2);
        store.setMaxDocumentCount(20);
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()), this.uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt3.getBytes()), this.uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(new ByteArrayInputStream(this.txt4.getBytes()), this.uri4, DocumentStore.DocumentFormat.TXT);
        //uri1 and uri2 should both be gone, having been pushed out by 3 and 4
        //assertNull(store.getDocument(this.uri1),"uri1 should've been pushed out of memory when uri3 was inserted");
        //assertNull(store.getDocument(this.uri2),"uri2 should've been pushed out of memory when uri4 was inserted");
        assert Files.exists(path1);
        assert Files.exists(path2);
        assertNotNull(store.getDocument(this.uri3),"uri3 should still be in memory");
        assertNotNull(store.getDocument(this.uri4),"uri4 should still be in memory");
        assert !Files.exists(path3);
        assert !Files.exists(path4);
    }


    //stage 3 tests

    @Test
    public void stage3Search() throws IOException {
        List<Document> results = this.search(this.getStoreWithTextAdded(),"plain",2);
        assertTrue(this.containsDocWithUri(results,this.uri1),"Result set should've included " + this.uri1);
        assertTrue(this.containsDocWithUri(results,this.uri2),"Result set should've included " + this.uri2);
        this.search(this.getStoreWithTextAdded(),"missing",0);
    }
    @Test
    public void stage3SearchBinary() throws IOException {
        List<Document> results = this.search(this.getStoreWithBinaryAdded(),"plain",2);
        assertTrue(this.containsDocWithUri(results,this.uri1),"Result set should've included " + this.uri1);
        assertTrue(this.containsDocWithUri(results,this.uri2),"Result set should've included " + this.uri2);
        this.search(this.getStoreWithBinaryAdded(),"missing",0);
    }

    @Test
    public void stage3DeleteAllTxt() throws IOException {
        DocumentStore store = this.getStoreWithTextAdded();
        String keyword = "plain";
        store.deleteAll(keyword);
        List<Document> results = store.search(keyword);
        URI[] absent = {this.uri1,this.uri2,this.uri3,this.uri4};
        URI[] present = new URI[0];
        this.checkContents(results,present,absent);
    }

    @Test
    public void stage3DeleteAllBinary() throws IOException {
        DocumentStore store = this.getStoreWithBinaryAdded();
        String keyword = "Headphones";
        store.deleteAll(keyword);
        List<Document> results = store.search(keyword);
        URI[] absent = {this.uri1,this.uri2,this.uri3,this.uri4};
        URI[] present = new URI[0];
        this.checkContents(results,present,absent);
    }

    @Test
    public void stage3SearchTxtByPrefix() throws IOException {
        this.stage3SearchByPrefix(this.getStoreWithTextAdded());
    }

    @Test
    public void stage3SearchBinaryByPrefix() throws IOException {
        this.stage3SearchByPrefix(this.getStoreWithBinaryAdded());
    }

    @Test
    public void stage3DeleteAllWithPrefix() throws IOException {
        DocumentStore store = this.getStoreWithTextAdded();
        //delete all starting with thi
        store.deleteAllWithPrefix("thi");
        List<Document> results = store.searchByPrefix("thi");
        assertEquals(0,results.size(),"search should've returned 0 results");
        URI[] present = new URI[0];
        URI[] absent = {this.uri1,this.uri2,this.uri3,this.uri4};
        this.checkContents(results,present,absent);

    }

    private List<Document> search(DocumentStore store, String keyword, int expectedMatches){
        List<Document> results = store.search(keyword);
        assertEquals(expectedMatches,results.size(),"expected " + expectedMatches + " matches, received " + results.size());
        return results;
    }

    private boolean containsDocWithUri(List<Document> docs, URI uri){
        for(Document doc : docs){
            if(doc.getKey().equals(uri)){
                return true;
            }
        }
        return false;
    }

    private void checkContents(List<Document> results, URI[] present, URI[] absent){
        for(URI uri : present){
            if(!this.containsDocWithUri(results, uri)){
                fail(uri + " should be in the result set, but is not");
            }
        }
        for(URI uri : absent){
            if(this.containsDocWithUri(results, uri)){
                fail(uri + " should NOT be in the result set, but is");
            }
        }
    }

    private void stage3SearchByPrefix(DocumentStore store){
        List<Document> results = store.searchByPrefix("str");
        assertEquals(2,results.size(),"expected 2 match, received " + results.size());
        URI[] present = {this.uri1,this.uri2};
        URI[] absent = {this.uri3,this.uri4};
        this.checkContents(results,present,absent);

        results = store.searchByPrefix("comp");
        assertEquals(1,results.size(),"expected 1 match, received " + results.size());
        URI[] present2 = {this.uri1};
        URI[] absent2 = {this.uri3,this.uri4,this.uri2};
        this.checkContents(results,present2,absent2);

        results = store.searchByPrefix("doc2");
        assertEquals(1,results.size(),"expected 1 match, received " + results.size());
        URI[] present3 = {this.uri2};
        URI[] absent3 = {this.uri3,this.uri4,this.uri1};
        this.checkContents(results,present3,absent3);

        results = store.searchByPrefix("blah");
        assertEquals(0,results.size(),"expected 0 match, received " + results.size());
    }

    //stage 1 tests
    @Test
    public void testPutBinaryDocumentNoPreviousDocAtURI() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.BINARY);
        assertTrue(returned == 0);
    }

    @Test
    public void testPutTxtDocumentNoPreviousDocAtURI() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        assertTrue(returned == 0);
    }

    @Test
    public void testPutDocumentWithNullArguments() throws IOException{
        DocumentStore store = new DocumentStoreImpl();
        try {
            store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), null, DocumentStore.DocumentFormat.TXT);
            fail("null URI should've thrown IllegalArgumentException");
        }catch(IllegalArgumentException e){}
        try {
            store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()), this.uri1, null);
            fail("null format should've thrown IllegalArgumentException");
        }catch(IllegalArgumentException e){}
    }

    @Test
    public void testPutNewVersionOfDocumentBinary() throws IOException {
        //put the first version
        DocumentStore store = new DocumentStoreImpl();
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.BINARY);
        assertTrue(returned == 0);
        Document doc1 = store.getDocument(this.uri1);
        assertArrayEquals(this.txt1.getBytes(),doc1.getDocumentBinaryData(),"failed to return correct binary text");

        //put the second version, testing both return value of put and see if it gets the correct text
        int expected = doc1.hashCode();
        returned = store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri1, DocumentStore.DocumentFormat.BINARY);

        assertEquals(expected, returned,"should return hashcode of the old document");
        assertArrayEquals(this.txt2.getBytes(),store.getDocument(this.uri1).getDocumentBinaryData(),"failed to return correct data");
    }

    @Test
    public void testPutNewVersionOfDocumentTxt() throws IOException {
        //put the first version
        DocumentStore store = new DocumentStoreImpl();
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        assertTrue(returned == 0);
        assertEquals(this.txt1,store.getDocument(this.uri1).getDocumentTxt(),"failed to return correct text");

        //put the second version, testing both return value of put and see if it gets the correct text
        returned = store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        assertTrue(Utils.calculateHashCode(this.uri1, this.txt1,null) == returned,"should return hashcode of old text");
        assertEquals(this.txt2,store.getDocument(this.uri1).getDocumentTxt(),"failed to return correct text");
    }

    @Test
    public void testGetTxtDoc() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        assertTrue(returned == 0);
        assertEquals(this.txt1,store.getDocument(this.uri1).getDocumentTxt(),"did not return a doc with the correct text");
    }

    @Test
    public void testGetTxtDocAsBinary() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        int returned = store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        assertTrue(returned == 0);
        assertNull(store.getDocument(this.uri1).getDocumentBinaryData(),"a text doc should return null for binary");
    }

    @Test
    public void testGetBinaryDocAsBinary() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        int returned = store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.BINARY);
        assertTrue(returned == 0);
        assertArrayEquals(this.txt2.getBytes(),store.getDocument(this.uri2).getDocumentBinaryData(),"failed to return correct binary array");
    }

    @Test
    public void testGetBinaryDocAsTxt() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        int returned = store.putDocument(new ByteArrayInputStream(this.txt2.getBytes()),this.uri2, DocumentStore.DocumentFormat.BINARY);
        assertTrue(returned == 0);
        assertNull(store.getDocument(this.uri2).getDocumentTxt(),"binary doc should return null for text");
    }

    @Test
    public void testDeleteDoc() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        store.deleteDocument(this.uri1);
        assertNull(store.getDocument(this.uri1),"calling get on URI from which doc was deleted should've returned null");
    }

    @Test
    public void testDeleteDocReturnValue() throws IOException {
        DocumentStore store = new DocumentStoreImpl();
        store.putDocument(new ByteArrayInputStream(this.txt1.getBytes()),this.uri1, DocumentStore.DocumentFormat.TXT);
        //should return true when deleting a document
        assertEquals(true,store.deleteDocument(this.uri1),"failed to return true when deleting a document");
        //should return false if I try to delete the same doc again
        assertEquals(false,store.deleteDocument(this.uri1),"failed to return false when trying to delete that which was already deleted");
        //should return false if I try to delete something that was never there to begin with
        assertEquals(false,store.deleteDocument(this.uri2),"failed to return false when trying to delete that which was never there to begin with");
    }
}