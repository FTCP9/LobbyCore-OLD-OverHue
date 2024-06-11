package eu.overhue.lobby;

import eu.overhue.lobby.Utils.ConfigUtil;
import eu.overhue.lobby.Utils.SpawnUtil;
import eu.overhue.lobby.commands.SetSpawn;
import eu.overhue.lobby.commands.vanish;
import eu.overhue.lobby.events.*;
import eu.overhue.lobby.scoreboard.main;
import eu.overhue.lobby.commands.Spawn;
import fr.mrmicky.fastboard.FastBoard;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Core extends JavaPlugin implements Listener {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    public static Core Instance;

    private LuckPerms luckPerms;


    @Override
    public void onLoad(){
        Core instance = this;
    }

    @Override
    public void onEnable() {
        System.out.println("§aOverHue lobby core has been enabled!");
        saveDefaultConfig();

        ConfigUtil config = new ConfigUtil(this,"config.yml");

        this.luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        getServer().getPluginManager().registerEvents(this, this);

        List<String> selections= (List<String>) getConfig().getList("spawn");

        SpawnUtil spawnUtil = new SpawnUtil(this);

        Bukkit.getPluginManager().registerEvents(new main(), this);
        Bukkit.getPluginManager().registerEvents(new damage(), this);
        Bukkit.getPluginManager().registerEvents(new hunger(), this);
        Bukkit.getPluginManager().registerEvents(new item(), this);
        Bukkit.getPluginManager().registerEvents(new drop(), this);
        Bukkit.getPluginManager().registerEvents(new itemgrab(), this);
        Bukkit.getPluginManager().registerEvents(new openmenu(), this);
        Bukkit.getPluginManager().registerEvents(new quitmessage(), this);
        Bukkit.getPluginManager().registerEvents(new joinemssage(), this);
        Bukkit.getPluginManager().registerEvents(new Break(), this);
        Bukkit.getPluginManager().registerEvents(new join(spawnUtil), this);
        getCommand("zlreload").setExecutor(new ReloadCommand());
        getCommand("vanish").setExecutor(new vanish());
        getCommand("spawn").setExecutor(new Spawn(spawnUtil));
        getCommand("Setspawn").setExecutor(new SetSpawn(spawnUtil));
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (FastBoard board : main.boards.values()) {
                main.updateBoard(board);
            }
        }, 0, 20);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
        {
            public void run()
            {
                for (Player p : Bukkit.getOnlinePlayers())
                {

                    p.getWorld().setTime(0L);

                }
            }
        }, 100L, 100L);
    }

    private class ReloadCommand implements CommandExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (cmd.getName().equalsIgnoreCase("zlreload")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!player.hasPermission("ohlobby.reload")) {
                        player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                        return true;
                    }
                }

                reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "OHLobby has been reloaded!");
                return true;
            }

            return false;
        }

    }







@EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent event) {
        final String message = event.getMessage();
        final Player player = event.getPlayer();

        // Get a LuckPerms cached metadata for the player.
        final CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        final String group = metaData.getPrimaryGroup();

        String format = getConfig().getString(getConfig().getString("group-formats." + group) != null ? "group-formats." + group : "chat-format")
                .replace("{prefix}", metaData.getPrefix() != null ? metaData.getPrefix() : "")
                .replace("{suffix}", metaData.getSuffix() != null ? metaData.getSuffix() : "")
                .replace("{prefixes}", metaData.getPrefixes().keySet().stream().map(key -> metaData.getPrefixes().get(key)).collect(Collectors.joining()))
                .replace("{suffixes}", metaData.getSuffixes().keySet().stream().map(key -> metaData.getSuffixes().get(key)).collect(Collectors.joining()))
                .replace("{world}", player.getWorld().getName())
                .replace("{name}", player.getName())
                .replace("{displayname}", player.getDisplayName())
                .replace("{username-color}", metaData.getMetaValue("username-color") != null ? metaData.getMetaValue("username-color") : "")
                .replace("{message-color}", metaData.getMetaValue("message-color") != null ? metaData.getMetaValue("message-color") : "");

        format = colorize(translateHexColorCodes(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, format) : format));

        event.setFormat(format.replace("{message}", player.hasPermission("oh.colorcodes") && player.hasPermission("oh.rgbcodes")
                ? colorize(translateHexColorCodes(message)) : player.hasPermission("oh.colorcodes") ? colorize(message) : player.hasPermission("oh.rgbcodes")
                ? translateHexColorCodes(message) : message).replace("%", "%%"));
    }

    private String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String translateHexColorCodes(final String message) {
        final char colorChar = ChatColor.COLOR_CHAR;

        final Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);

            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }

    @Override
    public void onDisable() {
    }

}
