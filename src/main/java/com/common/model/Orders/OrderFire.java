package com.common.model.Orders;

import com.common.IClientController;
import com.common.Game;
import com.common.ISocketManager;

public class OrderFire extends Order {
    public OrderFire(boolean star) {
        super("OrderFire", star, 0);
    }

    @Override
    public void executeOnClient(IClientController controller, Game game) {
        super.executeOnClient(controller, game);
        target.setOrder(new EmptyOrder());
    }

    @Override
    public void executeOnServer(Game game, ISocketManager socketManager) {
        super.executeOnServer(game, socketManager);
        target.setOrder(new EmptyOrder());
    }
}
