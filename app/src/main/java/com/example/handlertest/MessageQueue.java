package com.example.handlertest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {
    //通过数组的结构去存储Message对象
    Message[] items;
    //入队和出队元素的索引位置
    int putIndex;
    int takeIndex;
    //计数器
    int count;
    private Lock lock;
    //条件变量
    private Condition notEmpty;
    private Condition notFull;

    public MessageQueue(){
        //消息队列的大小限制
        this.items = new Message[50];
        this.lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();

    }

    //消息入栈
    public void enqueueMessage(Message msg){
        try {
            lock.lock();
            //消息队列满了，子线程停止发送消息，阻塞
            while (count==items.length){
                notFull.await();
            }
        items[putIndex] = msg;
        //循环取值
        putIndex = (++putIndex==items.length)?0:putIndex;
        count++;
        //有新的message对象
            notEmpty.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 消息出队
     * @return
     */
    public Message next(){
        Message msg = null;
        try {
            lock.lock();
            //消息队列为空，主线程looper停止轮询，阻塞
            while (count==0){
                notEmpty.await();
            }
            msg = items[takeIndex];//取出
            items[takeIndex] = null;
            takeIndex = (++takeIndex == items.length)?0:takeIndex;
            count--;
            //使用了一个message对象，则继续等待
            notFull.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return msg;
    }
}
