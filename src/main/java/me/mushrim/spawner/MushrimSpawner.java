package me.mushrim.spawner;

import me.mushrim.spawner.comandos.GiveSpawnerCommand;
import me.mushrim.spawner.listeners.SpawnerPickupListener;
import org.bukkit.plugin.java.JavaPlugin;

public class MushrimSpawner extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("givesp").setExecutor(new GiveSpawnerCommand(this));
        getServer().getPluginManager().registerEvents(new SpawnerPickupListener(), this);
        getLogger().info("MushrimSpawner ativado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MushrimSpawner desativado.");
    }
}
