package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import edu.yu.cs.com1320.project.stage5.impl.DocumentStoreImpl;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class StageFourDocumentStoreTest {

    @Test
    public void undo()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
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
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3) == null;
    }

    @Test
    public void makingLimitZeroBeforeAddingDocs()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        store.setMaxDocumentCount(0);
        store.setMaxDocumentBytes(9);
        byte[] array1 = {0, 1, 1};
        InputStream stream1 = new ByteArrayInputStream(array1);
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.BINARY);
        assert store.getDocument(uri1) == null;
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3) == null;
        assert store.getDocument(uri4) == null;
        byte[] array2 = {0, 1, 1};
        InputStream stream2 = new ByteArrayInputStream(array1);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.BINARY);
        byte[] array3 = {0, 1, 1};
        InputStream stream3 = new ByteArrayInputStream(array1);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.BINARY);
        byte[] array4 = {0, 1, 1};
        InputStream stream4 = new ByteArrayInputStream(array1);
        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.BINARY);


        assert store.getDocument(uri1) == null;
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3) == null;
        assert store.getDocument(uri4) == null;
    }

    @Test
    public void makingLimitZeroAfterAddingDocs()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
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
        store.setMaxDocumentCount(5);
        store.setMaxDocumentBytes(0);

        assert store.getDocument(uri1) == null;
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3) == null;
        assert store.getDocument(uri4) == null;
    }

    @Test
    public void setLimitAfterBeingOver()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
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
        assert store.getDocument(uri1) == null;
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3) == null;
        assert store.getDocument(uri4) != null;



    }

    @Test
    public void checkStack()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
        String string1 = "first";
        String string2 = "first second";
        String string3 = "third";
        String string4 = "fourth";
        InputStream stream1 = new ByteArrayInputStream((string1.getBytes()));
        InputStream stream2 = new ByteArrayInputStream((string2.getBytes()));
        InputStream stream3 = new ByteArrayInputStream((string3.getBytes()));
        InputStream stream4 = new ByteArrayInputStream((string4.getBytes()));
        InputStream stream7 = new ByteArrayInputStream((string2.getBytes()));
        byte[] array1 = {0, 1, 1};
        InputStream stream5 = new ByteArrayInputStream(array1);
        InputStream stream9 = new ByteArrayInputStream((string1.getBytes()));
        InputStream stream10 = new ByteArrayInputStream((string2.getBytes()));


        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);



        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
        store.deleteDocument(uri2);
        store.putDocument(stream7, uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        store.deleteAll("first");
        store.putDocument(stream9, uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream10, uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.TXT);
        store.getDocument(uri1);
        store.getDocument(uri3);
        store.getDocument(uri4);
        store.setMaxDocumentCount(3);

        assert store.getDocument(uri2) == null;
        store.undo();  //got rid of put on 4
        store.undo(); //got rid of put on 1
        assert store.getDocument(uri1) == null;
        store.undo();
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) == null;
        store.undo();
        store.undo();
        assert store.getDocument(uri2) == null;
        //store.undo();



        //store.undo(uri2); //this should throw exception. good


    }
    @Test
    public void updateTimeWithUndoOnCommandSet()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
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
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri2) == null;

    }

    @Test
    public void updateTimeWithUndoOnGenericCommand()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
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

        assert store.getDocument(uri3).getDocumentTxt().equals(string3);
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri1).getDocumentTxt().equals(string1);

        //OK I DID A LOT OF CHANGES TO DOCUMENT STORE BECAUSE OF THE TEST SO DEFINITELY GO THROUGH EVERYTHING. WHEN I DO THE UNDO
        //IN THIS TEST WHEN I TRY TO REMOVE FROM HEAP WHAT WAS PREVIOUSLY AT URI1 IN REALITY WHAT I DO IS REMOVE THE
        //URI2 FROM THE HEAP. WHAT THIS DOES IS MAKE IT SO I WILL NEVER COMPLETELYREMOVE FROM ABSOLUTELY EVERYTHING URI2
        //BECAUSE IT'S NO LONGER IN THE HEAP FOR ME TO GRAB IT. THIS IS WHAT I HAVE TO FIGURE OUT.
        //WHY IS IT THAT WHEN I REMOVE FROM HEAP IN THAT UNDO IT'S SETTING THE TIME OF ONE DOCUMENT TO ZERO
        //BUT THEN DELETING ANOTHER DOCUMENT I GUESS IT'S BECAUSE THAT FIRST DOCUMENT WAS NEVER IN THE HEAP



    }

    @Test
    public void notInCommandSetUndoAfterDeletingForMemory() throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        URI uri4 = new URI("D");
        URI uri5 = new URI("E");
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
        assert store.getDocument(uri1) == null;
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri2) != null;

        //assert store.deleteAll("first").size() == 1; this was good before check back later to make sure still good
        store.undo();
        assert store.getDocument(uri3) == null;
        store.undo();
        assert store.getDocument(uri2) == null;
        store.undo();//this should undo just uri2 from delete all no
        assert store.getDocument(uri2) != null;
        assert store.getDocument(uri1) == null;
        store.undo();
        assert store.getDocument(uri2) == null;
        //store.undo();


    }

    @Test
    public void noGenericUndosAfterDeletingForMemory() throws IOException, URISyntaxException {//if a document is normally deleted it doesn't countn towards space right
        DocumentStoreImpl store = new DocumentStoreImpl();
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
    public void tooMuchBinaryDataTest() throws URISyntaxException, IOException {//haven't tested binary data of string
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
        byte[] array1 = {0, 1, 1};
        byte[] array2 = {0, 1, 0};

        InputStream stream = new ByteArrayInputStream(array1);
        InputStream stream2 = new ByteArrayInputStream(array2);
        store.putDocument(stream, uri1, DocumentStore.DocumentFormat.BINARY);
        assert store.getDocument(uri1) != null;
        store.setMaxDocumentBytes(5);
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

        store.setMaxDocumentBytes(1);
        assert store.getDocument(uri1) == null;
        assert store.getDocument(uri2) == null;
        assert store.getDocument(uri3) != null;










    }
    @Test
    public void oneExtraDocument() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("A");
        URI uri2 = new URI("B");
        URI uri3 = new URI("C");
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
        assert store.getDocument(uri3) != null;
        assert store.getDocument(uri1) == null;
        assert store.getDocument(uri2) != null;
        assert store.search("first").size() == 0;
        assert store.search("second").size() == 1;
        assert store.search("third").size() == 1;
        URI uri4 = new URI("D");//btw what would happen if I use the same URI as another one
        String string4 = "fourth";
        InputStream stream4 = new ByteArrayInputStream((string4.getBytes()));
        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.TXT);
        assert store.getDocument(uri4) != null;
        assert store.search("fourth").size() == 1;
        assert store.getDocument(uri2) == null;
        assert store.search("second").size() == 0;
        assert store.search("third").size() == 1; //now four should be least recently used
        String fourth = "fourth";
        InputStream stream5 = new ByteArrayInputStream((fourth.getBytes()));

        store.putDocument(stream5, uri1, DocumentStore.DocumentFormat.TXT);
        assert store.getDocument(uri1) != null;
        assert store.getDocument(uri4) == null;
        assert store.getDocument(uri3) != null;
        //deal with making sure the counting in hashtable is all right with all posibilities






    }
}

