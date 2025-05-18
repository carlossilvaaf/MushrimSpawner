package me.mushrim.spawner.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerPickupListener implements Listener {

    @EventHandler
    public void onSpawnerPickup(PlayerPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();

        if (item.getType() == Material.MOB_SPAWNER) {
            Player player = event.getPlayer();
            ItemMeta meta = item.getItemMeta();

            if (meta != null && !meta.hasDisplayName()) {
                meta.setDisplayName(ChatColor.GREEN + "Gerador de Desconhecido");
                item.setItemMeta(meta);
            }
        }
    }
}
