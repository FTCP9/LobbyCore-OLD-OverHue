package eu.overhue.lobby.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigUtil {
    private static File file;
    private static FileConfiguration config;

    public ConfigUtil(Plugin plugin, String path) {
        this(plugin.getDataFolder().getAbsolutePath() + "/" + path);
        plugin = plugin;
    }

    public ConfigUtil(String path) {
        file = new File(path);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public boolean save(){
        try{
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
        return false;
    }
    public File getFile() {
        return file;

    }
    public FileConfiguration getConfig() {
        return config;
    }
    public void reloadConfig(){
        config = YamlConfiguration.loadConfiguration(file);
    }

}
