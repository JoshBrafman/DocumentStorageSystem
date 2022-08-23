import edu.yu.cs.com1320.project.BTree;
import edu.yu.cs.com1320.project.impl.BTreeImpl;
//import org.testng.annotations.Test;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage5.impl.DocumentPersistenceManager;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

public class BTreeTest {

    @Test
    public void getBackOldFromPut() throws Exception {
        BTreeImpl<URI, Document> tree = new BTreeImpl<>();
        DocumentPersistenceManager pm = new DocumentPersistenceManager(null);
        tree.setPersistenceManager(pm);
        URI uri = new URI("http://overHere");
        DocumentImpl doc = new DocumentImpl(uri, "hi", null);
        assert tree.put(uri, doc) == null;
        assert tree.get(uri).equals(doc);
        URI uri2 = new URI("http://overHere");
        DocumentImpl doc2 = new DocumentImpl(uri, "2", null);
        tree.moveToDisk(uri);
        assert tree.put(uri, doc2).equals(doc);
    }





    @Test
    public void testGetOnFullTable(){
       BTreeImpl<Integer, String> table = new BTreeImpl<Integer, String>();
        table.put(0, "A");
        table.put(1, "B");
        table.put(2, "C");
        table.put(3, "D");
        table.put(4, "E");

        assert "A".equals(table.get(0));
        assert "B".equals(table.get(1));
        assert "C".equals(table.get(2));
        assert "D".equals(table.get(3));
        assert "E".equals(table.get(4));
        assert table.get(5) == null;

    }


    @Test
    public void testGetOnEmptyTable(){
        BTreeImpl<Integer, String> table = new BTreeImpl<Integer, String>();
        assert table.get(0) == null;
        assert table.get(6) == null;
    }
    @Test
    public void testPut(){
        BTree<Integer, String> table = new BTreeImpl<Integer, String>();
        assert table.put(0, "A") == null;
        assert table.put(0, "B").equals("A");
        assert table.put(0, "B").equals("B");
        assert table.put(0, "C").equals("B");
        //table.put(null, "A");
    }

    @Test
    public void testPuttingNull(){
        BTreeImpl<Integer, String> table = new BTreeImpl<>();
        table.put(0, "A");
        assert table.put(0, null).equals("A");
        assert table.get(0) == null;
        assert table.put(1, null) == null;
        assert table.get(0) == null;
    }

    @Test
    public void testWithCollisions(){
        BTreeImpl<Integer, String> table = new BTreeImpl<Integer, String>();

        table.put(0, "A");
        table.put(1, "B");
        table.put(2, "C");
        table.put(3, "D");
        table.put(4, "E");
        table.put(5, "F");
        table.put(6, "G");
        table.put(7, "H");
        table.put(8, "I");
        table.put(9, "J");
        table.put(10, "K");
        //table.put(11, "L");
        //table.put(12, "M");
        table.put(13, "N");
        table.put(14, "O");
        table.put(15, "P");
        table.put(16, "Q");
        table.put(17, "R");
        table.put(18, "S");
        table.put(19, "T");
        table.put(20, "U");
        table.put(21, "V");

        assert table.get(0).equals("A");
        assert table.get(5).equals("F");
        assert table.get(11) == null;
        assert table.get(10).equals("K");
        table.put(12, null);
        assert table.get(12) == null;
        assert table.put(12, "Z") == null;
        assert table.get(12).equals("Z");
        assert table.put(12, null).equals("Z");
        assert table.get(12) == null;
        assert table.get(21).equals("V");
        assert table.get(20).equals("U");
        assert table.put(20, "Z").equals("U");
    }

    @Test
    public void sameKey(){
        BTreeImpl<Integer, String> table = new BTreeImpl<Integer, String>();

        table.put(0, "A");
        assert table.put(0, null).equals("A");
        assert table.put(0, null) == null;
        assert table.get(0) == null;
    }

    @Test
    public void whenToRehash(){
        BTreeImpl<Integer, String> table = new BTreeImpl<Integer, String>();
        table.put(0, null);
        table.put(0, null);
        table.put(0, null);
        table.put(0, null);
        table.put(0, null);
        table.put(0, null); //should still be 5

        table.put(0, "A");
        table.put(1, "B");
        table.put(2, "C");
        table.put(0, null);
        table.put(1, null);
        table.put(2, null);
        table.put(3, "D");
        table.put(4, "E");
        table.put(5, "F");//should still be 5
        table.put(6, "G");
        table.put(7, "H");
        table.put(8, "I");//should be 10 noq
        table.put(9, "J");
        table.put(10, "K");
    }
}
