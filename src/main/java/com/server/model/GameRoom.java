package com.server.model;

import com.common.*;
import com.common.model.Cards.CardTypes.WesterosEvent;
import com.common.model.GameEvents.GatheringOrders;
import com.common.model.Map.Map;
import com.common.model.Orders.Order;
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
    ArrayList<Socket> socketsOfClients = new ArrayList<>();

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
        if(socketsOfClients.size()<number) {
            socketManager.send(new IntegerMessage(port), client);

            try {
                client = forCLient.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socketsOfClients.add(client);
            if(socketsOfClients.size()==number){
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

    private void start(){
        game = new Game();
        game.setNumberOfPlayers(number);
        Collections.shuffle(socketsOfClients);

        socketManager.multipleSend(game, socketsOfClients);
        for(int i = 0; i< socketsOfClients.size(); i++){
            System.out.println(game.getPlayer(i));
            socketManager.send(new IntegerMessage(i), socketsOfClients.get(i));
        }

        while(true){
            turn();
        }

    }

    private void turn(){
        game.nextMove();
        if(game.getMoveNumber() != 1){
            triggerEvents();
        }
        gatherAndSyncOrders();
        executeOrders();
    }

    private void executeOrders(){
        ArrayList<Player> orderedPlayers = game.getIronThrone();
        while(game.getMap().thereAreUnusedOrders()){
            for(Player p : orderedPlayers){
                Socket playerSocket = getPlayersSocket(p);
                Order nextOrder = Validator.getNextOrderForExecution(game, p);
                if(nextOrder != null){
                    socketManager.send(nextOrder, playerSocket);
                    Order order = (Order)socketManager.receive(playerSocket);
                    order.executeOnServer(game, socketManager);
                    socketManager.multipleSend(game, socketsOfClients);
                }
            }
        }
    }

    private void triggerEvents(){
        WesterosEvent card;
        card = game.getFirstEventsDeck().getTop();
        card.executeOnServer(game, socketManager);
        socketManager.multipleSend(card, socketsOfClients);
        card = game.getSecondEventsDeck().getTop();
        card.executeOnServer(game, socketManager);
        socketManager.multipleSend(card, socketsOfClients);
        card = game.getThirdEventsDeck().getTop();
        card.executeOnServer(game, socketManager);
        socketManager.multipleSend(card, socketsOfClients);
    }

    private void gatherAndSyncOrders(){
        socketManager.multipleSend(new GatheringOrders(), socketsOfClients);
        ArrayList<Message> maps = socketManager.mupltipleReceive(socketsOfClients);
        for (Message m : maps){
            game.getMap().addOrders((Map)m);
        }
        socketManager.multipleSend(game, socketsOfClients);
    }

    //there would be a map instead of this pure madness
    private Socket getPlayersSocket(Player p){
        ArrayList<Player> players = game.getPlayers();
        for (int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(p.getName())){
                return socketsOfClients.get(i);
            }
        }
        return null;
    }
}
