package com.common;
import com.common.model.Decks.CommanderDeck;
import com.common.model.Map.MapNodes.MapNode;
import com.common.model.Orders.OrderAttack;
import com.common.model.Orders.OrderFire;
import com.common.model.Orders.OrderRule;

import java.util.ArrayList;

public class Player extends Message {
    private String name;
    private int barrelSupply;
    private int numberOfCastles;
    private int ironThronePosition;
    private int valyrianSwordPosition;
    private int ravenPosition;
    private int starNumber;
    private CommanderDeck commaders;
    private ArrayList<MapNode> ownedNodes = new ArrayList<MapNode>(); //do we need it?


    public Player(String name, int barrelSupply, int numberOfCastles, int ironThronePosition,
                  int valyrianSwordPosition, int ravenPosition, CommanderDeck commnders){
        this.name = name;
        this.barrelSupply = barrelSupply;
        this.numberOfCastles = numberOfCastles;
        this.ironThronePosition = ironThronePosition;
        this.valyrianSwordPosition = valyrianSwordPosition;
        this.ravenPosition = ravenPosition;
        this.commaders = commnders;
    }


    public int getBarrelSupply() {
        return barrelSupply;
    }

    public void setBarrelSupply(int barrelSupply) {
        this.barrelSupply = barrelSupply;
    }

    public int getNumberOfCastles() {
        return numberOfCastles;
    }

    public void setNumberOfCastles(int numberOfCastles) {
        this.numberOfCastles = numberOfCastles;
    }

    public int getIronThronePosition() {
        return ironThronePosition;
    }

    public void setIronThronePosition(int ironThronePosition) {
        this.ironThronePosition = ironThronePosition;
    }

    public int getValyrianSwordPosition() {
        return valyrianSwordPosition;
    }

    public void setValyrianSwordPosition(int valyrianSwordPosition) {
        this.valyrianSwordPosition = valyrianSwordPosition;
    }

    public int getRavenPosition() {
        return ravenPosition;
    }

    public void setRavenPosition(int ravenPosition) {
        this.ravenPosition = ravenPosition;
    }

    public String getName() {
        return name;
    }

    public CommanderDeck getCommaders() {
        return commaders;
    }

    public int getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(int starNumber) {
        this.starNumber = starNumber;
    }

    @Override
    public void executeOnClient(IClientController controller, Game game) {

    }

    @Override
    public void executeOnServer(Game game, ISocketManager socketManager) {
    }
}
