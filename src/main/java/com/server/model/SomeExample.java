package com.server.model;

import com.common.Message;
import com.server.communication.SocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
public class SomeExample {
    @Autowired
    SocketManager socketManager;




    @PostConstruct
    public void init() throws InterruptedException {
        printer();
    }

    public void printer(){
        new Thread(()->{
            try {
                Thread.sleep(50000);
                System.out.println("Sleep stop");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(socketManager.getClientNumber());

            ArrayList<Message> buf=socketManager.mupltipleReceive();

            for (Message m:
                 buf) {
                socketManager.multipleSend(m);
            }

        }).start();
    }
}
