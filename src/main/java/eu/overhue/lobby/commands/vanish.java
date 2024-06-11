package eu.overhue.lobby.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class vanish implements CommandExecutor {
    private ArrayList<String> HiddenPlayers = new ArrayList<String>();

    public boolean onCommand(CommandSender sender, Command cmd, String commandLable, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED +"Silly console... You can't hide!");
            return true;
        }

        Player p = (Player) sender;

        if(p.hasPermission("vanish")) {
            if(HiddenPlayers.contains(p.getName())) {
                HiddenPlayers.remove(p.getName());
                for (Player pls : Bukkit.getOnlinePlayers()) {
                    if (!pls.canSee(p)) {
                            pls.showPlayer(p);
                            return true;
                        }
                        return true;
                    }
                    return true;
                }
                if(!(HiddenPlayers.contains(p.getName()))) {
                    HiddenPlayers.add(p.getName());
                    for (Player pls : Bukkit.getOnlinePlayers()) {
                        pls.hidePlayer(p);
                        return true;
                    }
                    return true;
                }
            } else {
                p.sendMessage(ChatColor.RED +"You do not have access to that command!");
                return true;
            }
        return true;
    }
}
