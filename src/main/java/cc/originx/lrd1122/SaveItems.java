package cc.originx.lrd1122;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SaveItems extends JavaPlugin {

    public static SaveItems instance;
    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginCommand("saveitems").setExecutor(new CommandManager());
        getLogger().info("定制插件联系QQ: 354001412");
    }

    @Override
    public void onDisable() {
    }
}
