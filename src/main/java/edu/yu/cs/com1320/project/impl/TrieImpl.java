package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Trie;
import java.util.*;

public class TrieImpl<Value> implements Trie<Value> {

    private static final int alphabetSize = 150;
    private Node root;

    class Node<T>
    {
        protected Set<T> val;
        protected Node[] links;
        Node(){
            this.val = new HashSet<>();
            links = new Node[alphabetSize];
        }
    }

    @Override
    public List<Value> getAllSorted(String key, Comparator<Value> comparator)
    {
        Node x = this.get(this.root, key.toLowerCase(), 0);
        if (x == null || x.val == null) {
            ArrayList<Value> empty = new ArrayList<>();
            return empty;
        }
        ArrayList<Value> list = new ArrayList(x.val);
        list.sort(comparator);
        return list;
    }

    @Override
    public List<Value> getAllWithPrefixSorted (String prefix, Comparator<Value> comparator)
    {
        Set<Value> results = new HashSet<>();
        Node x = this.get(this.root, prefix.toLowerCase(), 0);
        if(x!= null) {
            this.collect(x,prefix.toLowerCase(), results);
        }
        ArrayList<Value> list = new ArrayList(results);
        list.sort(comparator);
        return list;
    }

    private void collect(Node x,String prefix,Set<Value> results){
        //if this node has values, add them to results
        if (x.val != null) {
            results.addAll(x.val);
        }
        //visit each non-null child/link
        for (char c = 0; c < this.alphabetSize; c++) {
            if(x.links[c]!=null){
                this.collect(x.links[c], prefix.toLowerCase(), results);
            }
        }
    }

    private Set<Value> getAllWithPrefixSorted(String prefix){
        Set<Value> results = new HashSet<>();
        Node x = this.get(this.root, prefix.toLowerCase(), 0);
        if(x!= null) {
            this.collect(x,prefix.toLowerCase(), results);
        }
        return results;
    }
    @Override
    public Set<Value> deleteAllWithPrefix(String prefix){
        Set<Value> values = this.getAllWithPrefixSorted(prefix.toLowerCase());
        this.root = deleteAllWithPrefix(this.root,prefix.toLowerCase(),0);
        return values;
    }

    private Node deleteAllWithPrefix(Node x, String key, int d){
        if (x == null)
        {
            return null;
        }
        //we're at the node to del - clear the set of values
        if (d >= key.length())
        {
            x.val.clear();
        }
        //continue down the trie to the target node
        else
        {
            char c = key.charAt(d);
            x.links[c] = this.deleteAllWithPrefix(x.links[c], key, d + 1);
        }
        //this node has a val – don't delete it
        if (x.val.size() != 0)
        {
            return x;
        }
        //delete any node with string length greater than key.length, as well as any node which has no children and did not have a value
        if (d < key.length()){
            for (int c = 0; c < alphabetSize; c++)
            {
                if (x.links[c] != null)
                {
                    return x;
                }
            }
        }
        return null;
    }
    /**
     * A char in java has an int value.
     * see http://docs.oracle.com/javase/8/docs/api/java/lang/Character.html#getNumericValue-char-
     * see http://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.1.2
     */
    private Node get(Node x, String key, int d)
    {
        //link was null - return null, indicating a miss
        if (x == null)
        {
            return null;
        }
        //we've reached the last node in the key,
        //return the node
        if (d == key.length())
        {
            return x;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        return this.get(x.links[c], key.toLowerCase(), d + 1);
    }
    @Override
    public void put(String key, Value val)
    {
        {
            this.root = put(this.root, key.toLowerCase(), val, 0);
        }
    }
    /**
     *
     * @param x
     * @param key
     * @param val
     * @param d
     * @return
     */
    private Node put(Node x, String key, Value val, int d)
    {
        //create a new node
        if (x == null)
        {
            x = new Node();
        }
        //we've reached the last node in the key,
        //add the value for the key and return the node
        if (d == key.length())
        {
            x.val.add(val);
            return x;
        }
        //proceed to the next node in the chain of nodes that
        //forms the desired key
        char c = key.charAt(d);
        x.links[c] = this.put(x.links[c], key.toLowerCase(), val, d + 1);
        return x;
    }
    @Override
    public Set<Value> deleteAll(String key)
    {
        Set<Value> values = new HashSet<>();
        this.deleteAll(this.root, key.toLowerCase(), 0, values);
        return values;
    }

    private Node deleteAll(Node x, String key, int d, Set<Value> values)
    {
        if (x == null)
        {
            return null;
        }
        //we're at the node to del - copy and then clear the set of values
        if (d == key.length())
        {
            values.addAll(x.val);
            x.val.clear();
        }
        //continue down the trie to the target node
        else
        {
            char c = key.charAt(d);
            x.links[c] = this.deleteAll(x.links[c], key.toLowerCase(), d + 1, values);
        }
        //this node has a val – do nothing, return the node
        if (x.val.size() != 0)
        {
            return x;
        }
        //remove subtrie rooted at x if it is completely empty
        for (int c = 0; c <alphabetSize; c++)
        {
            if (x.links[c] != null)
            {
                return x; //not empty
            }
        }
        //empty - set this link to null in the parent
        return null;
    }

    @Override
    public Value delete(String key, Value val){
        Set<Boolean> emptyIfDidNotDelete = new HashSet<>();
        this.delete(this.root, key.toLowerCase(), 0, val, emptyIfDidNotDelete);
        if (emptyIfDidNotDelete.contains(true)){
            return val;
        }else{
            return null;
        }
    }

    private Node delete(Node x, String key, int d,Value val, Set<Boolean> set){
        if (x == null)
        {
            return null;
        }
        //we're at the node to delete
        if (d == key.length())
        {
            set.add(x.val.remove(val));
        }
        else
        {
            char c = key.charAt(d);
            x.links[c] = this.delete(x.links[c], key.toLowerCase(), d + 1, val, set);
        }
        if (x.val.size() != 0)
        {
            return x;
        }
        //remove subtrie rooted at x if it is completely empty
        for (int c = 0; c <alphabetSize; c++)
        {
            if (x.links[c] != null)
            {
                return x; //not empty
            }
        }
        return null;
    }
}

