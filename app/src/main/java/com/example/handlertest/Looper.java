package com.example.handlertest;

/**
 * 保证数据的安全,轮询消息队列
 */
public final class Looper {

    //每一个主线程都会有一个looper对象,looper对象保存在ThreadLocal，保证子线程隔离
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    //有一个对应的消息队列
    MessageQueue mQueue;
    private Looper(){
        mQueue = new MessageQueue();

    }

    /**
     * looper对象的初始化
     */
    public static void prepare(){
        if (sThreadLocal==null){
            throw new RuntimeException("only one looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    /**
     * 获取looper对象
     * @return
     */
    public static Looper myLooper(){
        return sThreadLocal.get();
    }

    public static void loop(){
        Looper me = myLooper();
        if (me==null){
            throw new RuntimeException("looper prepared was not on this thread");
        }
        MessageQueue queue = me.mQueue;
       for(;;){
           Message message = queue.next();
           if (message==null){
               continue;
           }
           //转发给handler，进行消息分发
           message.target.dispatchMessage(message);
       }
    }
}
