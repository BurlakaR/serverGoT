package com.common.model.Map.MapNodes;

import com.common.*;
import com.common.model.Orders.EmptyOrder;
import com.common.model.Orders.Order;
import com.common.model.Units.Squad;
import com.common.model.utils.EmptyLogo;
import com.common.model.utils.ForNode;
import com.common.model.utils.Logo;

import java.util.ArrayList;

public class MapNode extends ForNode {
    private Player owner;
    private Squad squad = new Squad();
    private Order order = new EmptyOrder();
    private Logo logo = new EmptyLogo();
    private ArrayList<MapNode> neighbors = new ArrayList<MapNode>();
    private String name;
    private Boolean hasPoweerCoin;

    public MapNode(int x, int y, int w, int h, String name) {
        super(x, y, w, h, name);
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public ArrayList<MapNode> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<MapNode> neighbors){ this.neighbors = neighbors;}

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
        owner=squad.get(0).getPlayer();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
        owner=logo.getPlayer();
    }

    public Player getOwner(){
        return owner;
    }

    //one more erally bad plays for those guys
    @Override
    public void executeOnClient(Game game, SocketManagerCommon socketManager, ClientController controller) {

    }

    @Override
    public Message executeOnServer(Game game, SocketManagerCommon socketManager) {
        return null;
    }
}
