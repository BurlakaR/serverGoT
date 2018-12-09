package com.common.model.Orders;

import com.common.IClientController;
import com.common.Game;
import com.common.ISocketManager;

public class EmptyOrder extends Order {
    public EmptyOrder() {
        super("EmptyOrder", false, 0);
        this.setH(80); this.setW(80);
    }

    @Override
    public void executeOnClient(IClientController controller, Game game) {

    }

    @Override
    public void executeOnServer(Game game, ISocketManager socketManager) {
    }

    @Override
    public boolean orderIsEmpty(){return true;}
}
