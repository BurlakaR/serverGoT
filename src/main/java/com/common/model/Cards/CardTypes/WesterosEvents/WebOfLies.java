package com.common.model.Cards.CardTypes.WesterosEvents;

import com.common.model.Cards.CardTypes.WesterosEvent;
import com.common.model.Game;

public class WebOfLies extends WesterosEvent {
    public WebOfLies() {
        message = "В фазе замыслов этого раунда игроки не могут отдавать приказы подмоги";
        img = "";
    }


    @Override
    public void triggerCard(Game game) {

    }
}
