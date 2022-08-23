package edu.yu.cs.com1320.project.stage5.impl;

import edu.yu.cs.com1320.project.stage5.Document;
import java.net.URI;
import java.util.*;

public class DocumentImpl implements Document  {
    private URI uri;
    private String text;
    private byte[] data;
    private Map<String, Integer> wordToCount;
    private Set<String> words;
    private transient long time;

    public DocumentImpl(URI uri, String txt, Map<String, Integer> wordMap){
        if (txt == null || uri == null || txt.isBlank() || uri.toASCIIString().isBlank()){
            throw new IllegalArgumentException();
        }
        this.uri = uri;
        this.text = txt;
        this.wordToCount = new HashMap<>();
        this.words = new HashSet<>();
        String alphaNumericsOnly = txt.replaceAll("[^A-Za-z0-9 ]", "");
        String lowerCaseAlphaNumericsOnly = alphaNumericsOnly.toLowerCase();
        String[] wordArray = lowerCaseAlphaNumericsOnly.split(" ");
        if (wordMap == null) {
            for (String word : wordArray) {
                words.add(word);
                if (!wordToCount.containsKey(word)) {
                    wordToCount.put(word, 1);
                } else {
                    wordToCount.put(word, wordToCount.get(word) + 1);
                }
            }
        }else{
            this.wordToCount = wordMap;
            this.words = wordMap.keySet();
        }
    }

    public DocumentImpl(URI uri, byte[] binaryData){
        if (binaryData == null || binaryData.length == 0 || uri == null || uri.toASCIIString().isBlank()){
            throw new IllegalArgumentException();
        }
        this.uri = uri;
        this.data = binaryData;
    }

    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(this.data);
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (this == obj){
            return true;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }
        Document other = (Document)obj;
        return this.hashCode() == other.hashCode();
    }
    @Override
    public String getDocumentTxt(){
        return this.text;
    }
    @Override
    public byte[] getDocumentBinaryData(){
        return this.data;
    }
    @Override
    public URI getKey(){
        return this.uri;
    }
    public int wordCount(String word){
        if (this.text == null){
            return 0;
        }
        String lowerCase = word.toLowerCase();
        return wordToCount.getOrDefault(lowerCase, 0);
    }
    public Set<String> getWords(){
        if (this.text == null){
            return new HashSet<>();
        }
        return words;
    }

    public long getLastUseTime(){
        return this.time;
    }
    public void setLastUseTime(long timeInNanoseconds){
        this.time = timeInNanoseconds;
    }

    @Override
    public Map<String, Integer> getWordMap() {
        Map<String, Integer> copy = new HashMap<>(this.wordToCount);
        return copy;
    }

    @Override
    public void setWordMap(Map<String, Integer> wordMap) {
        this.wordToCount = wordMap;
    }

    @Override
    public int compareTo(Document other){
        return Long.compare(this.getLastUseTime(), other.getLastUseTime());
    }

}

