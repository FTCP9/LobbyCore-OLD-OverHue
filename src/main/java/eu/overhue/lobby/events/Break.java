package eu.overhue.lobby.events;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Break implements Listener {
    public void onBreak(BlockBreakEvent event){
        event.setCancelled(true);
    }
}
