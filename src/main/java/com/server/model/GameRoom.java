package com.server.model;

import com.common.Game;
import com.common.IntegerMessage;
import com.common.Message;
import com.common.model.GameEvents.GatheringOrders;
import com.common.model.Map.Map;
import com.server.communication.SocketManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class GameRoom {
    int id;
    int number;
    int port;
    ArrayList<Socket> socketsClient = new ArrayList<>();

    public static SocketManager getSocketManager() {
        return socketManager;
    }

    public static void setSocketManager(SocketManager socketManager) {
        GameRoom.socketManager = socketManager;
    }

    static SocketManager socketManager;
    ServerSocket forCLient;

    Game game;

    public GameRoom(int id, int number, int port) {
        this.id = id;
        this.number = number;
        this.port = port;
        try {
            forCLient=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean add(Socket client) {
        if(socketsClient.size()<number) {
            socketManager.send(new IntegerMessage(port), client);

            try {
                client = forCLient.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socketsClient.add(client);
            if(socketsClient.size()==number){
                new Thread(()->{
                    start();
                }).start();
            }
            return true;
        }

        return false;
    }

    public int getId() {
        return id;
    }

    public int getPort(){
        return port;
    }

    public void start(){
        game = new Game();
        game.setNumberOfPlayers((short)3);
        Collections.shuffle(socketsClient);

        socketManager.multipleSend(game,socketsClient);
        for(int i=0; i<socketsClient.size(); i++){
            System.out.println(game.getPlayer(i));
            socketManager.send(game.getPlayer(i), socketsClient.get(i));
        }
        socketManager.multipleSend(new GatheringOrders(), socketsClient);
        ArrayList<Message> maps = socketManager.mupltipleReceive(socketsClient);
        for (Message m : maps){
            game.getMap().addOrders((Map)m);
        }
        socketManager.multipleSend(game, socketsClient);

    }
}
