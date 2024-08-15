package cc.originx.lrd1122;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class CommandManager implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 1) {
            if (strings[0].equalsIgnoreCase("save") && commandSender instanceof Player && commandSender.hasPermission("saveitems.save")) {
                Player player = (Player) commandSender;
                ItemStack item = player.getInventory().getItemInMainHand();
                File dir = new File(SaveItems.instance.getDataFolder(), "items");
                if (!dir.exists()) dir.mkdir();
                File file = new File(SaveItems.instance.getDataFolder() + "\\items", strings[1] + ".yml");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                config.set("i", item);
                try {
                    config.save(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (strings[0].equalsIgnoreCase("get") && commandSender instanceof Player && commandSender.hasPermission("saveitems.get")) {
                Player player = (Player) commandSender;
                if (strings.length > 2) {
                    File file = new File(SaveItems.instance.getDataFolder() + "\\items", strings[1] + ".yml");
                    if (file.exists()) {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                        ItemStack item = config.getItemStack("i").clone();
                        item.setAmount(Integer.parseInt(strings[2]));
                        player.getInventory().addItem(item);
                        player.updateInventory();
                    }
                }
            } else if (strings[0].equalsIgnoreCase("setitem") && commandSender.hasPermission("saveitems.setitem")) {
                if (strings.length > 3) {
                    Player player = null;
                    if(commandSender instanceof Player){
                        player = (Player) commandSender;
                    }
                    if(strings.length > 4){
                        player = Bukkit.getPlayer(strings[4]);
                    }
                    File file = new File(SaveItems.instance.getDataFolder() + "\\items", strings[1] + ".yml");
                    if (file.exists()) {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                        ItemStack item = config.getItemStack("i").clone();
                        item.setAmount(Integer.parseInt(strings[2]));
                        player.getInventory().setItem(Integer.parseInt(strings[3]), item);
                        player.updateInventory();
                    }
                }
            }else if (strings[0].equalsIgnoreCase("give") && commandSender instanceof Player && commandSender.hasPermission("saveitems.give")) {
                if (strings.length > 3) {
                    Player player = Bukkit.getPlayer(strings[3]);
                    File file = new File(SaveItems.instance.getDataFolder() + "\\items", strings[1] + ".yml");
                    if (file.exists()) {
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                        ItemStack item = config.getItemStack("i").clone();
                        item.setAmount(Integer.parseInt(strings[2]));
                        player.getInventory().addItem(item);
                        player.updateInventory();
                    }
                }
            }
        } else{
            if(commandSender.hasPermission("saveitems.help")) {
                commandSender.sendMessage("/saveitems save <key>");
                commandSender.sendMessage("/saveitems get <key> <amount>");
                commandSender.sendMessage("/saveitems setitem <key> <amount> <slot> <target>");
                commandSender.sendMessage("/saveitems give <key> <amount> <target>");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of();
    }
}
