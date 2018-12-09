package com.common.model.Orders;

import com.common.IClientController;
import com.common.Game;
import com.common.ISocketManager;

public class OrderRule extends Order {
    public OrderRule(boolean star) {
        super("OrderRule", star,0);
    }

    @Override
    public void executeOnClient(IClientController controller, Game game) {

    }

    @Override
    public void executeOnServer(Game game, ISocketManager socketManager) {
    }
}
