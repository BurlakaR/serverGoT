package com.common.model.Orders;

import com.common.IClientController;
import com.common.Game;
import com.common.ISocketManager;
import com.common.model.Units.Squad;

public class OrderAttack extends Order {
    private boolean leavePowerCoinOnPreviousNode;
    private Squad squad;

    public OrderAttack(boolean star, int power) {
        super("OrderAttack", star, power);
        if(power<0) this.setImgName(this.getImgName()+"1");
        else if(power==0) this.setImgName(this.getImgName()+"2");
    }

    @Override
    public void executeOnClient(IClientController controller, Game game) {

    }

    @Override
    public void executeOnServer(Game game, ISocketManager socketManager) {
    }

    public boolean isLeavePowerCoinOnPreviousNode() {
        return leavePowerCoinOnPreviousNode;
    }

    public void setLeavePowerCoinOnPreviousNode(boolean leavePowerCoinOnPreviousNode) {
        this.leavePowerCoinOnPreviousNode = leavePowerCoinOnPreviousNode;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }
}
