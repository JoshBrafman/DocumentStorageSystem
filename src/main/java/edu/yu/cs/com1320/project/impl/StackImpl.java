package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.Stack;

public class StackImpl<T> implements Stack<T> {
    private class Entry {
        T value;
        Entry next;
    }

    private Entry head;
    private StackImpl next;

    public StackImpl(){

    }

    @Override
    public void push(T element) {
        Entry newest = new Entry();
        newest.value = element;
        newest.next = head;
        head = newest;
    }

    @Override
    public T pop() {
        if (head == null){
            return null;
        }
        Entry top = head;
        head = head.next;
        return top.value;
    }

    @Override
    public T peek() {
        if (head == null){
            return null;
        }
        return head.value;
    }

    @Override
    public int size() {
        if (head == null){
            return 0;
        }
        Entry current = head;
        int count = 1;
        while (current.next != null){
            current = current.next;
            count += 1;
        }
        return count;
    }

}

