package edu.yu.cs.com1320.project.impl;

import edu.yu.cs.com1320.project.MinHeap;

public class MinHeapImpl<E extends Comparable<E>> extends MinHeap<E> {
    public MinHeapImpl(){
        this.elements =  (E[]) new Comparable[10];
    }

    public void reHeapify(E element){
        if (this.getArrayIndex(element) == -1){
            return;
        }
        this.downHeap(this.getArrayIndex(element));
        this.upHeap(this.getArrayIndex(element));
    }

    protected int getArrayIndex(E element){
        int x = -1;
        for (int j = 0; j <= this.count; j++){
            if (this.elements[j] != null && this.elements[j].equals(element)){
                x = j;
                break;
            }
        }
        return x;
    }

    protected void doubleArraySize(){
        E[] old = this.elements;
        this.elements =  (E[]) new Comparable[old.length * 2];
        for (int j = 0; j <= this.count; j++){
            this.elements[j] = old[j];
        }
    }

}
