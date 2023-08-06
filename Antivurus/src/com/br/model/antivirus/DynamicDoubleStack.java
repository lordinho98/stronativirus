package com.br.model.antivirus;

import java.util.EmptyStackException;

public class DynamicDoubleStack<T> {
    private Node<T> forwardTop;
    private Node<T> backwardTop;

    public DynamicDoubleStack() {
        this.forwardTop = null;
        this.backwardTop = null;
    }

    public void pushForward(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = forwardTop;
        forwardTop = newNode;
    }

    public DetectedFile popForward() {
        if (isEmptyForward()) {
            throw new EmptyStackException();
        }

        T data = forwardTop.data;
        forwardTop = forwardTop.next;
        return (DetectedFile) data;
    }

    public boolean isEmptyForward() {
        return forwardTop == null;
    }

    public void pushBackward(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = backwardTop;
        backwardTop = newNode;
    }

    public T popBackward() {
        if (isEmptyBackward()) {
            throw new EmptyStackException();
        }

        T data = backwardTop.data;
        backwardTop = backwardTop.next;
        return data;
    }

    public boolean isEmptyBackward() {
        return backwardTop == null;
    }

    private static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
}