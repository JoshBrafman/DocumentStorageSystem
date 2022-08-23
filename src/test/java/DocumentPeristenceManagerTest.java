import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.impl.DocumentImpl;
//import edu.yu.cs.com1320.project.stage5.impl.DocumentPersistenceManager;
import edu.yu.cs.com1320.project.stage5.impl.DocumentPersistenceManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocumentPeristenceManagerTest {
    @Test
    public void serialize() throws IOException {
        DocumentPersistenceManager dpm = new DocumentPersistenceManager(new File("C:\\Users\\joshu\\Documents\\hi"));
        URI uri = URI.create("https://www.yello/x.z.y.z/docwhatsupdoc");
        Path path = Paths.get(new File("C:\\Users\\joshu\\Documents\\hi").getAbsolutePath(), uri.getRawSchemeSpecificPart() + ".json");
        System.out.println(uri);
        Document doc = new DocumentImpl(uri, "hi", null);
        dpm.delete(uri);
        assert !Files.exists(path);
        dpm.serialize(uri, doc);
        assert Files.exists(path);
       Document doc2 = dpm.deserialize(uri);
        assert Files.exists(path);
       assert dpm.delete(uri);
       assert !Files.exists(path);
       assert !dpm.delete(uri);
        assert !Files.exists(path);
        assert doc2.getDocumentTxt().equals("hi");
        dpm.serialize(uri, doc);
        assert Files.exists(path);

    }
}
