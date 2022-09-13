package republicstorage.republicstorage;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
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
        getCommand("storagewrite").setExecutor(this);
    }

    @Override
    public void onDisable() {
        this.writeMain();
    }


    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        writeMain();
        return true;
    }

    public void writeMain(){
        FC.set("items",itemAmountMap.size());
        saveConfig();
        FC.set("ignore",String.join(",",ignore));
        saveConfig();

        int loop = 0;
        for(Map.Entry<String,Long> entry : itemAmountMap.entrySet()){
            long amount = entry.getValue();
            String name = entry.getKey();
            FC.set("item"+loop+".name",name.toUpperCase(Locale.ROOT));
            saveConfig();
            FC.set("item"+loop+".amount",amount);
            saveConfig();

            loop++;
        }

        /*
        itemAmountMap.clear();
        ignore.clear();
        tabComplete.clear();

        reloadConfig();
        this.load();
        */


        return;
    }
}
