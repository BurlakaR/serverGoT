package com.common.model.Cards.CardTypes.Commanders.Lanisters;

import com.common.IClientController;
import com.common.Game;
import com.common.ISocketManager;
import com.common.model.Cards.CardTypes.Commander;

public class Tirion extends Commander {
    public Tirion() {
        super("", 1);
    }

    @Override
    public void executeOnClient(IClientController controller, Game game) {

    }

    @Override
    public void executeOnServer(Game game, ISocketManager socketManager) {
    }
}
