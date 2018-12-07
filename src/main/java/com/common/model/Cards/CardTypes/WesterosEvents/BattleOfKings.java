package com.common.model.Cards.CardTypes.WesterosEvents;

import com.common.ClientController;
import com.common.Game;
import com.common.Message;
import com.common.SocketManagerCommon;
import com.common.model.Cards.CardTypes.WesterosEvent;

public class BattleOfKings extends WesterosEvent {
    public BattleOfKings(){
        super("");
        message = "Все жетоны на треках влияния снимаются. Дома вступают в борьбу за влияние " +
                "на каждом из треков, начиная с железного трона";
    }

    @Override
    public void executeOnClient(Game game, SocketManagerCommon socketManager, ClientController controller) {

    }

    @Override
    public Message executeOnServer(Game game, SocketManagerCommon socketManager) {
        return null;
    }
}
