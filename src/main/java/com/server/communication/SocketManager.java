package com.server.communication;

import com.common.ISocketManager;
import com.common.IntegerMessage;
import com.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@Component
public class SocketManager implements ISocketManager {
    static int portCreation=9998;
    static int portConnection=9999;
    ServerSocket serverCreation;
    ServerSocket serverConnection;

    @Autowired
    Bridge bridge;


    @PostConstruct
    public void init() throws IOException {
        serverCreation=new ServerSocket(portCreation);
        serverConnection=new ServerSocket(portConnection);
    }

    public IntegerMessage createRoom(){
            try{
            Socket client=serverCreation.accept();
                IntegerMessage buf = (IntegerMessage)receive(client);
                buf.setSender(client);
                return buf;
            } catch (IOException e) {
                    e.printStackTrace();
                    return null;
            }

    }

    public IntegerMessage connectRoom(){
        try{
            Socket client=serverConnection.accept();
            IntegerMessage buf = (IntegerMessage)receive(client);
            buf.setSender(client);
            return buf;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void send(Message message, Socket client){
        bridge.send(client, message);
    }

    public Message receive(Socket client){
        return (Message)bridge.receive(client);
    }

    public void multipleSend(Message message, ArrayList<Socket> socketsClient){
        for (Socket s:
             socketsClient) {
            bridge.send(s, message);
        }
    }

    public ArrayList<Message> mupltipleReceive(ArrayList<Socket> socketsClient){
        final int[] mescounter = {0};
        ArrayList<Message> buf=new ArrayList<>();
        for (Socket s:
             socketsClient) {
            buf.add(new IntegerMessage(0));
        }
        System.out.println(buf.toString());
        for(int clientcounter=0;clientcounter<socketsClient.size(); clientcounter++) {
            final int i=clientcounter;
            new Thread(()->{
                buf.set(i,receive(socketsClient.get(i)));
                mescounter[0]++;
            }).start();
        }
        while(!(mescounter[0]==socketsClient.size())){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        System.out.println(buf.toString());
        return buf;
    }



}
