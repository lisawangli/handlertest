package com.example.handlertest;

public class Handler {

    private MessageQueue mQueue;
    private Looper mLooper;

    public Handler(){
        //获取主线程的looper对象
        mLooper = Looper.myLooper();
        this.mQueue = mLooper.mQueue;
    }
    //发送消息，压入队列
    public void sendMessage(Message msg){
        msg.target = this;//将handler绑定msg的target
        mQueue.enqueueMessage(msg);
    }

    public void handlerMessage(Message msg){

    }

    //消息分发
    public void dispatchMessage(Message msg){
        handlerMessage(msg);
    }
}
