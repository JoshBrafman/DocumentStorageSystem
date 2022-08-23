import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class StageFiveDocumentImplTest {
    private URI textUri;
    private String textString;

    private URI binaryUri;
    private byte[] binaryData;

    @BeforeEach
    public void setUp() throws Exception {
        this.textUri = new URI("http://edu.yu.cs/com1320/txt");
        this.textString = "This is text content. Lots of it.";

        this.binaryUri = new URI("http://edu.yu.cs/com1320/binary");
        this.binaryData = "This is a PDF, brought to you by Adobe.".getBytes();

    }

    @Test
    public void newConstructor(){
        DocumentImpl doc = new DocumentImpl(this.textUri, this.textString, null);
        System.out.println(doc.getWordMap());
        Map<String, Integer> map = doc.getWordMap();
        DocumentImpl doc2 = new DocumentImpl(this.textUri, this.textString, map);
        System.out.println(doc.getWordMap());
        System.out.println(doc2.getWordMap());
        HashMap set = new HashMap<String, Integer>();
        set.put("hi", 3);
        doc2.setWordMap(set);
        System.out.println(doc2.getWordMap());
        DocumentImpl doc3 = new DocumentImpl(this.textUri, this.textString, set);
        System.out.println("doc3 " + doc3.getWordMap());



    }

}
