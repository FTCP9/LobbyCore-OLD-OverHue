package eu.overhue.lobby.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class quit implements Listener {
    @EventHandler
    public void quit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(player.hasPermission("lobby.fly")){
            if (player.isFlying()){
                player.setAllowFlight(false);
                player.setFlying(false);
            } else {
                return;
            }
        } else {
            return;
        }
    }
}
