package eu.overhue.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class damage implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        event.setCancelled(true);
    }
}