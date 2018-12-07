package com.common.model.Cards.CardTypes.WesterosEvents;

import com.common.ClientController;
import com.common.Game;
import com.common.model.Cards.CardTypes.WesterosEvent;

public class FeastForRavens extends WesterosEvent {
    public FeastForRavens(){
        super("");
        message = "В фазе замыслов этого раунда игроки не могут отдавать приказы подмоги";
    }

    @Override
    public void executeOnClient(ClientController controller, Game game) {

    }

    @Override
    public void executeOnServer(Game game) {
    }
}
