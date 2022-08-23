package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.DocumentStore;
import edu.yu.cs.com1320.project.stage5.impl.DocumentStoreImpl;
import org.junit.jupiter.api.Test;


import javax.print.Doc;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StageFiveDocumentStoreImplTest {


    @Test
    public void undoiguess() throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri = new URI("http://a12345678910");
        URI uri2 = new URI("http://a12345679111");
        String string2 = uri.getRawSchemeSpecificPart() + ".json";
        Path path = Paths.get(System.getProperty("user.dir"),string2);
        String string3 = uri2.getRawSchemeSpecificPart() + ".json";
        Path path2 = Paths.get(System.getProperty("user.dir"),string3);
        String string4 = uri2.getRawSchemeSpecificPart() + ".json";
//THIS IS BAD THE FILE WASN'T UNWRITTEN
        store.putDocument(new ByteArrayInputStream(("hello there".getBytes())), uri, DocumentStore.DocumentFormat.TXT);
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream(("hell".getBytes())), uri2, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path);
        store.putDocument(new ByteArrayInputStream(("new one".getBytes())), uri, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path);
        assert Files.exists(path2);
        assert store.getDocument(uri).getDocumentTxt().equals("new one");
        store.undo(uri);
        assert store.getDocument(uri).getDocumentTxt().equals("hello there");
        //not sure what docs should be on file now

    }
    @Test
    public void putReturningOldDoc() throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri = new URI("http://a123456");
        URI uri2 = new URI("http://a1234567");
        String string2 = uri.getRawSchemeSpecificPart() + ".json";
        Path path = Paths.get(System.getProperty("user.dir"),string2);
//THIS IS BAD THE FILE WASN'T UNWRITTEN
        store.putDocument(new ByteArrayInputStream(("hello there".getBytes())), uri, DocumentStore.DocumentFormat.TXT);
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream(("hell".getBytes())), uri2, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path);
        store.deleteDocument(uri);
        assert !Files.exists(path);
        assert store.getDocument(uri) == null;
        store.undo(uri);
        assert store.getDocument(uri2) != null;

    }
    @Test
    public void undoDocThatWasOnDisk() throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri = new URI("http://aaAa");
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream(("hello there".getBytes())), uri, DocumentStore.DocumentFormat.TXT);

        String string2 = uri.getRawSchemeSpecificPart() + ".json";
        Path path = Paths.get(System.getProperty("user.dir"),string2);

       // store.setMaxDocumentCount(0);
        URI uri2 = new URI("http://zaA");
        store.putDocument(new ByteArrayInputStream(("a".getBytes())), uri2, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path);
        store.putDocument(new ByteArrayInputStream(("hi".getBytes())), uri, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path);
        //assert store.getDocument(uri).getDocumentTxt().equals("hi");
        //assert store.search("hello").size() == 0;
        store.undo();//i don't even know what's supposed to happen here
        assert Files.exists(path);
        assert store.getDocument(uri).getDocumentTxt().equals("hello there");
        assert store.search("hello").size() == 1;

    }
    @Test
    public void bringOneDocumentBack() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri = new URI("http://a");
        store.setMaxDocumentCount(1);
        store.putDocument(new ByteArrayInputStream(("hello there".getBytes())), uri, DocumentStore.DocumentFormat.TXT);

        String string2 = uri.getRawSchemeSpecificPart() + ".json";
        Path path = Paths.get(System.getProperty("user.dir"),string2);

        store.setMaxDocumentCount(0);
        assert Files.exists(path);
        assert store.searchByPrefix("a").size() == 0;
        store.setMaxDocumentCount(1);
        List<Document> list = store.searchByPrefix("he");
        assert list.size() == 1;
        assert !Files.exists(path);
        assert list.get(0).getDocumentTxt().equals("hello there");
        assert list.get(0).getKey().equals(uri);
        System.out.println(list.get(0).getWordMap());
        assert list.get(0).equals(store.getDocument(uri));
        assert !Files.exists(path);
        store.setMaxDocumentCount(0);
        assert Files.exists(path);


    }
    @Test
    public void nonNullDir() throws IOException, URISyntaxException {
        DocumentStoreImpl store = new DocumentStoreImpl(new File("hi"));
       // DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri = new URI("http://afterHi2");
        store.setMaxDocumentCount(0);
        store.putDocument(new ByteArrayInputStream(("string".getBytes())), uri, DocumentStore.DocumentFormat.TXT);

    }
    @Test
    public void searchManyDocs()throws IOException, URISyntaxException {
        URI uri1 = new URI("http://a/wow/URI1A111");
        URI uri2 = new URI("http://a/alright/URI2A111");
        URI uri3 = new URI("http://a/then/URI3A111");
        URI uri4 = new URI("http://a/then/URI4A111");

        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(0);
        String string2 = uri1.getRawSchemeSpecificPart() + ".json";
        System.out.println("HERE" + string2);
        Path path = Paths.get(System.getProperty("user.dir"),string2);
        System.out.println(path + "HERE");
        String string3 = uri2.getRawSchemeSpecificPart() + ".json";
        Path path2 = Paths.get(System.getProperty("user.dir"),string3);
        String string4 = uri3.getRawSchemeSpecificPart() + ".json";
        Path path3 = Paths.get(System.getProperty("user.dir"),string4);
        String string5 = uri4.getRawSchemeSpecificPart() + ".json";
        Path path4 = Paths.get(System.getProperty("user.dir"),string5);
        String string11 = "hi there";
        String string21 = "hello there";
        String string31 = "what's up there";
        String string41 = "so there";
        InputStream stream1 = new ByteArrayInputStream((string11.getBytes()));
        InputStream stream2 = new ByteArrayInputStream((string21.getBytes()));
        InputStream stream3 = new ByteArrayInputStream((string31.getBytes()));
        InputStream stream4 = new ByteArrayInputStream((string41.getBytes()));
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream4,uri4, DocumentStore.DocumentFormat.TXT);
        store.setMaxDocumentCount(0);
        assert store.search("there").size() == 4;


    }

    @Test
    public void eh()throws IOException, URISyntaxException{
        URI uri1 = new URI("http://eh/wow/URI1A111");
        URI uri2 = new URI("http://eh/alright/URI2A111");
        URI uri3 = new URI("http://alright/then/URI3A111");
        URI uri4 = new URI("http://alright/then/URI4A111");
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        store.setMaxDocumentCount(2);
        String string2 = uri1.getRawSchemeSpecificPart() + ".json";
        Path path = Paths.get(System.getProperty("user.dir"),string2);
        String string3 = uri2.getRawSchemeSpecificPart() + ".json";
        Path path2 = Paths.get(System.getProperty("user.dir"),string3);
        String string4 = uri3.getRawSchemeSpecificPart() + ".json";
        Path path3 = Paths.get(System.getProperty("user.dir"),string4);
        String string5 = uri4.getRawSchemeSpecificPart() + ".json";
        Path path4 = Paths.get(System.getProperty("user.dir"),string5);
        String string11 = "first";
        String string21 = "second";
        String string31 = "third";
        String string41 = "fourth";
        InputStream stream1 = new ByteArrayInputStream((string11.getBytes()));
        InputStream stream2 = new ByteArrayInputStream((string21.getBytes()));
        InputStream stream3 = new ByteArrayInputStream((string31.getBytes()));
        InputStream stream4 = new ByteArrayInputStream((string41.getBytes()));
        store.putDocument(stream1, uri1, DocumentStore.DocumentFormat.TXT);
        store.putDocument(stream2, uri2, DocumentStore.DocumentFormat.TXT);
        assert !Files.exists(path);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.search("first");
        assert !Files.exists(path);
        assert !Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.putDocument(stream3, uri3, DocumentStore.DocumentFormat.TXT);
        assert Files.exists(path2);
        assert !Files.exists(path);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        List<Document> list = store.search("second");
        assert list.get(0).getDocumentTxt().equals("second");
        assert list.get(0).getKey().equals(uri2);
        assert list.size() == 1;
        assert !Files.exists(path2);
        assert Files.exists(path);
        store.putDocument(stream4, uri1, DocumentStore.DocumentFormat.TXT);
      assert !Files.exists(path);
        assert !Files.exists(path2);
      assert store.getDocument(uri1).getDocumentTxt().equals("fourth");

      assert Files.exists(path3);
      store.getDocument(uri3);
        assert !Files.exists(path3);


    }

    @Test
    public void IDontKnow()throws IOException, URISyntaxException{
        DocumentStoreImpl store = new DocumentStoreImpl(null);
        URI uri1 = new URI("http://startHere/hi/project/docM1");
        URI uri2 = new URI("http://startHere/hi/project/docJ1");
        URI uri3 = new URI("http://startHere/hi/project/docK1");
        URI uri4 = new URI("http://startHere/hi/project/docN1");
        URI uri5 = new URI("E");
        String string2 = uri1.getRawSchemeSpecificPart() + ".json";
        Path path = Paths.get(System.getProperty("user.dir"),string2);
        String string3 = uri2.getRawSchemeSpecificPart() + ".json";
        Path path2 = Paths.get(System.getProperty("user.dir"),string3);
        String string4 = uri3.getRawSchemeSpecificPart() + ".json";
        Path path3 = Paths.get(System.getProperty("user.dir"),string4);
        String string5 = uri4.getRawSchemeSpecificPart() + ".json";
        Path path4 = Paths.get(System.getProperty("user.dir"),string5);
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
        assert !Files.exists(path);
        store.putDocument(stream4, uri4, DocumentStore.DocumentFormat.BINARY);
        store.setMaxDocumentCount(3);
        store.setMaxDocumentBytes(40);

        assert !Files.exists(path3);
        assert Files.exists(path);
        store.getDocument(uri1);
        assert !Files.exists(path);
        assert Files.exists(path2);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.getDocument(uri3);
        assert !Files.exists(path3);
        assert !Files.exists(path4);
        store.getDocument(uri2);
        assert Files.exists(path4);

        //WARNING in reality when i brought 1 in 2 should've been kicked out




        //File tempFile = new File(uri1);
        //Path path = Files.createTempFile("startHere/hi/project/doc5", ".json");
        //Path path = Files.createTempFile("doc12",".json");
        //final URL url = new URL("http://startHere/hi/project/doc5");
        //url.openConnection().getResponseCode();
        //HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        //int responseCode = huc.getResponseCode();
        //System.out.println(responseCode);
        //assert Files.exists(path);
        //store.getDocument(uri1);
        //assert !Files.exists(path);//I can't tell if my code is actually wrong or not
        //Path path2 = Files.createTempFile("doc13",".json");
        //assert !Files.exists(path2);


//        assert tempFile.exists();
//        store.getDocument(uri1);
//        assert !tempFile.exists();

//        assert store.getDocument(uri1) == null;
//        assert store.getDocument(uri2) == null;
//        assert store.getDocument(uri3) == null;
//        assert store.getDocument(uri4) == null;
    }



}
