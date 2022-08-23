package edu.yu.cs.com1320.project.stage5.impl;

import com.google.gson.*;
import edu.yu.cs.com1320.project.stage5.Document;
import edu.yu.cs.com1320.project.stage5.PersistenceManager;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * created by the document store and given to the BTree via a call to BTree.setPersistenceManager
 */
public class DocumentPersistenceManager implements PersistenceManager<URI, Document> {
    private Gson gson;
    private String baseDir;

    public DocumentPersistenceManager(File baseDir){
         this.gson = new Gson();
         if (baseDir == null){
             this.baseDir = "user.dir";
         }else {
             baseDir.mkdirs();
             this.baseDir = baseDir.getAbsolutePath();
         }
    }

    @Override
    public void serialize(URI uri, Document val) throws IOException {
        Path path = this.makePath(uri);
        Files.createDirectories(path.getParent());
        File file = new File(String.valueOf(path));
        String json = gson.toJson(val);
        PrintWriter out = new PrintWriter(new FileWriter(String.valueOf(path)));
        out.write(json);
        out.close();
    }

    @Override
    public Document deserialize(URI uri) throws IOException {
        Path path = this.makePath(uri);
        Reader reader = Files.newBufferedReader(path);
        Document doc = gson.fromJson(reader, DocumentImpl.class);
        doc.setLastUseTime(java.lang.System.nanoTime());
        return doc;
    }

    @Override
    public boolean delete(URI uri) throws IOException {
        Path path = this.makePath(uri);
        Files.createDirectories(path.getParent());
        File file = new File(String.valueOf(path));
        return file.delete();
    }

    private Path makePath(URI uri){
        String string = uri.getRawSchemeSpecificPart();
        string += ".json";
        Path path;
        if (this.baseDir.equals("user.dir")) {
            path = Paths.get(System.getProperty(this.baseDir), string);
        }else{
            path = Paths.get(this.baseDir, string);
        }
        return path;
    }
}
