package republicstorage.republicstorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Map;

import static republicstorage.republicstorage.SettingsLoad.*;

public final class RePublicStorage extends JavaPlugin implements CommandExecutor {
    public void load(){
        new SettingsLoad().fc(getConfig());
    }

    @Override
    public void onEnable() {
        this.load();
        saveDefaultConfig();
        getCommand("storage").setExecutor(new PublicStorageMain());
        getCommand("storagereload").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        Player player = (Player) sender;
        if(player instanceof Player && !(player.isOp())){
            return false;
        }

        player.sendMessage("§a[PublicStorage]:Config reloading");

        FC.set("items",itemAmountMap.size());

        int loop = 0;
        for(Map.Entry<String,Long> entry : itemAmountMap.entrySet()){
            long amount = entry.getValue();
            String name = entry.getKey();
            FC.set("item"+loop+".name",name.toUpperCase(Locale.ROOT));
            FC.set("item"+loop+".amount",amount);
            saveConfig();

            loop++;
        }

        itemAmountMap.clear();
        ignore.clear();
        tabComplete.clear();
        reloadConfig();
        this.load();

        player.sendMessage("§a[PublicStorage]:Config reload finish!");
        for(Map.Entry<String,Long> entry : itemAmountMap.entrySet()){
            player.sendMessage("item:"+entry);
        }

        return true;
    }

}
