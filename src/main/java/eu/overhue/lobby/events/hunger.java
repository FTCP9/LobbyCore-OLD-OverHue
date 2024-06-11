package eu.overhue.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class hunger implements Listener {

    @EventHandler
    public void antihunger(FoodLevelChangeEvent event){
        event.setCancelled(true);
    }
}
