package com.server.model;

import com.common.Message;
import com.server.communication.SocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SomeExample {
    @Autowired
    SocketManager socketManager;

    Queue<Message> messagesReceive=new LinkedBlockingQueue<>();
    Queue<Message> messagesSend=new LinkedBlockingQueue<>();

    @PostConstruct
    public void init(){
        socketManager.setMessages(messagesReceive, messagesSend);
        printer();
    }

    public void printer(){
        new Thread(()->{
            while(true){
                if(!messagesReceive.isEmpty()) messagesReceive.poll().print();
            }
        }).start();
    }
}
