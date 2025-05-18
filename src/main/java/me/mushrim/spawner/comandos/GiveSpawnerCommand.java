package me.mushrim.spawner.comandos;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class GiveSpawnerCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public GiveSpawnerCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private final List<String> mobs = new ArrayList<>();

    {
        for (EntityType type : EntityType.values()) {
            if (type.isSpawnable() && type.isAlive()) {
                mobs.add(type.name());
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mushrim.givespawner")) {
            sender.sendMessage(ChatColor.RED + "Você não tem permissão.");
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage(ChatColor.RED + "Uso correto: /givesp <mob> <quantidade> <jogador>");
            return true;
        }

        String mobName = args[0].toUpperCase();
        int quantidade;
        Player target = Bukkit.getPlayer(args[2]);

        if (!mobs.contains(mobName)) {
            sender.sendMessage(ChatColor.RED + "Mob inválido. Use TAB para listar os mobs disponíveis.");
            return true;
        }

        try {
            quantidade = Integer.parseInt(args[1]);
            if (quantidade < 1 || quantidade > 64) {
                sender.sendMessage(ChatColor.RED + "Quantidade deve ser entre 1 e 64.");
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Quantidade inválida.");
            return true;
        }

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            return true;
        }

        EntityType entityType = EntityType.valueOf(mobName);

        // Cria o item spawner base
        ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, quantidade);
        spawner = setSpawnerMob(spawner, entityType);

        // Define display name e lore
        ItemMeta meta = spawner.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Gerador de " + capitalize(mobName));
        meta.setLore(Collections.singletonList(ChatColor.GRAY + "Mob: " + mobName));
        spawner.setItemMeta(meta);

        // Dá o spawner para o jogador
        Map<Integer, ItemStack> sobraram = target.getInventory().addItem(spawner);
        sobraram.values().forEach(item -> target.getWorld().dropItemNaturally(target.getLocation(), item));

        sender.sendMessage(ChatColor.GREEN + "Spawner entregue para " + target.getName());
        target.sendMessage(ChatColor.GREEN + "Você recebeu " + quantidade + " spawner(s) de " + capitalize(mobName) + "!");

        return true;
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        text = text.toLowerCase();
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    /**
     * Altera o spawner para spawnar o mob correto usando NMS (1.8.9)
     */
    private ItemStack setSpawnerMob(ItemStack spawner, EntityType entityType) {
        // Converte para NMS
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(spawner);
        NBTTagCompound tag = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        NBTTagCompound blockEntityTag = new NBTTagCompound();
        blockEntityTag.setString("EntityId", entityType.getName());

        tag.set("BlockEntityTag", blockEntityTag);
        nmsStack.setTag(tag);

        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String start = args[0].toUpperCase();
            List<String> matches = new ArrayList<>();
            for (String mob : mobs) {
                if (mob.startsWith(start)) {
                    matches.add(mob.toLowerCase());
                }
            }
            return matches;
        } else if (args.length == 3) {
            List<String> players = new ArrayList<>();
            String start = args[2].toLowerCase();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(start)) {
                    players.add(p.getName());
                }
            }
            return players;
        }
        return Collections.emptyList();
    }
}
