package com.example.handlertest;

import java.util.UUID;

public class HandlerTest {

    public static void main(String args[]){
        Looper.prepare();
        final Handler handler = new Handler(){

            @Override
            public void handlerMessage(Message msg) {

                System.out.println(Thread.currentThread().getName()+",receied:"+msg.toString());
            }
        };
        for (int i = 0; i < 10 ; i++) {
            new Thread(){
                @Override
                public void run() {
                    while (true){
                        Message msg = new Message();
                        msg.what =1;
                        synchronized (UUID.class){
                            msg.obj = Thread.currentThread().getName()+",send message:"+UUID.randomUUID().toString();
                        }
                        System.out.println(msg);
                        handler.sendMessage(msg);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
        Looper.loop();
    }
}
