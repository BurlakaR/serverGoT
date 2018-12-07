package com.common.model.GameEvents;

import com.common.*;

public class GatheringOrders extends Message {

    @Override
    public void executeOnClient(ClientController controller, Game game) {
        controller.putOrders();
    }

    @Override
    public void executeOnServer(Game game) {
    }
}
