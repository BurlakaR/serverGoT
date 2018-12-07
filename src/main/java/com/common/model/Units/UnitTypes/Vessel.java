package com.common.model.Units.UnitTypes;

import com.common.*;
import com.common.model.Units.Unit;

public class Vessel extends Unit {
    public Vessel(Player player)
    {
        super(player, ("Vessel"+player.getName()));
        cost = 1;
        damage = 1;
        damageToCastles = 1; //would be counted when supporting other squads
    }

    @Override
    public void executeOnClient(ClientController controller, Game game) {

    }

    @Override
    public void executeOnServer(Game game) {
    }
}
