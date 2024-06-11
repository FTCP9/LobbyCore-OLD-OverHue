package eu.overhue.lobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class itemgrab implements Listener {
    @EventHandler
    public void onChestMoveItems (InventoryClickEvent event){
        event.setCancelled(true);
    }
}
