package com.example.handlertest;

public class Message {
    public int what;
    Handler target;
    Object obj;

    @Override
    public String toString() {
        return obj.toString();
    }
}
