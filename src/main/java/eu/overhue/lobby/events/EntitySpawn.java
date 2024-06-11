package eu.overhue.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawn implements Listener {
    @EventHandler
    public void EntitySpawn(EntitySpawnEvent event){
        event.setCancelled(true);
    }
}
