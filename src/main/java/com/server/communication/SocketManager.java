package com.server.communication;

import com.common.Message;
import com.common.Port;
import com.common.TestCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class SocketManager {
    static int port=10000;
    ServerSocket server;
    ArrayList<ServerSocket> sockets = new ArrayList<>();
    ArrayList<Socket> socketsClient = new ArrayList<>();

    Queue<Message> messagesReceive=new LinkedBlockingQueue<>();
    Queue<Message> messagesSend=new LinkedBlockingQueue<>();

    @Autowired
    Bridge bridge;

    @PostConstruct
    public void init() throws IOException {
        server=new ServerSocket(port);
        for(int i=0;i<6;i++){
            sockets.add(new ServerSocket(port+i+1));
        }
        connect();
        listenStart();
        sender();
    }

    public void connect(){
        new Thread(()->{
            Socket client;
            for(int i=0; i<6; i++){
                try {
                    client=server.accept();
                    bridge.send(client, new Port(port+i+1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void listenStart(){
        for (ServerSocket socket:
             sockets) {
            new Thread(()->{
                try {
                    Socket client=socket.accept();
                    socketsClient.add(client);
                    listener(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void sender(){
        new Thread(()->{
            while(true){
                if(!messagesSend.isEmpty()){
                    for (Socket socket:
                         socketsClient) {
                        bridge.send(socket, messagesSend.peek());
                    }
                    messagesSend.poll();
                }
            }
        });
    }

    public void listener(Socket clientSocket){

            while(true) {
                try {
                    messagesReceive.add((TestCommand) bridge.receive(clientSocket));
                }catch (Exception e){
                    messagesReceive.add(new TestCommand(clientSocket.getLocalSocketAddress().toString(), " disconnected"));
                    break;
                }
            }

    }

    public void send(Message message){
        messagesSend.add(message);
    }

    public Message receive(){
        while(true){
            if(!messagesReceive.isEmpty()) return messagesReceive.poll();
        }
    }


}
