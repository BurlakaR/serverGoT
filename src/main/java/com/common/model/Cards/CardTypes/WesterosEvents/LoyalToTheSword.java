package com.common.model.Cards.CardTypes.WesterosEvents;

import com.common.ClientController;
import com.common.Game;
import com.common.Message;
import com.common.SocketManagerCommon;
import com.common.model.Cards.CardTypes.WesterosEvent;

public class LoyalToTheSword extends WesterosEvent {
    public LoyalToTheSword(){
        super("");
        message = "Владелец Валерийского меча выбирает один из трёх вариантов: " +
                "\nВ фазе замыслов этого раунда не отдаются приказы обороны" +
                "\nВ фазе замыслов этого раунда не отдаются приказы похода +1" +
                "\nНичего не происходит";
    }
    @Override
    public void executeOnClient(Game game, SocketManagerCommon socketManager, ClientController controller) {

    }

    @Override
    public Message executeOnServer(Game game, SocketManagerCommon socketManager) {
        return null;
    }
}
