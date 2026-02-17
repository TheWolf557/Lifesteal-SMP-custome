package me.nova.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LifestealSMP extends JavaPlugin {

    private static LifestealSMP instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        saveDefaultConfig();
        getLogger().info("Lifesteal SMP enabled!");
    }

    public static LifestealSMP getInstance() {
        return instance;
    }
}