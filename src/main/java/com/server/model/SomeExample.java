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



    @PostConstruct
    public void init(){
        printer();
    }

    public void printer(){
        new Thread(()->{
            while(true){
                socketManager.receive().print();
            }
        }).start();
    }
}
