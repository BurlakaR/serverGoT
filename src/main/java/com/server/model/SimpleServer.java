package com.server.model;

import com.common.IntegerMessage;
import com.server.communication.SocketManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class SimpleServer {
    @Autowired
    SocketManager socketManager;

    ArrayList<GameRoom> gameRooms=new ArrayList<>();
    BlockingDeque<Integer> ids=new LinkedBlockingDeque<>();

    @PostConstruct
    public void init() throws InterruptedException {
        for(int i=1; i<1000;i++){
            ids.add(i);
        }
        createRoom();
        fullRoom();
    }


    public void createRoom(){
        new Thread(()->{
            while(true) {
                IntegerMessage buf = socketManager.createRoom();
                int number = buf.getMessage();
                int id=ids.poll();
                System.out.println("Create:"+id);
                gameRooms.add(new GameRoom(id,number,9999+id,socketManager ));
                socketManager.send(new IntegerMessage(id), buf.getSender());
            }
        }).start();

    }

    public void fullRoom(){
        new Thread(()->{
            while(true) {
                IntegerMessage buf = socketManager.connectRoom();
                int id=buf.getMessage();
                System.out.println("Trying to connect to room:"+id);//delete this later
                boolean flag=false;
                for (GameRoom g:
                     gameRooms) {
                    if(id==g.getId()){
                        flag=g.add(buf.getSender());
                        break;
                    }
                }
                if(!flag){
                    socketManager.send(new IntegerMessage(-1), buf.getSender());
                }

            }
        }).start();
    }



}
