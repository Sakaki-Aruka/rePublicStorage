package republicstorage.republicstorage;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Map;

import static republicstorage.republicstorage.SettingsLoad.*;

public final class RePublicStorage extends JavaPlugin implements CommandExecutor {

    public static RePublicStorage instance;

    public void load(){
        new SettingsLoad().fc(getConfig());
    }

    @Override
    public void onEnable() {
        instance = this;
        this.load();
        saveDefaultConfig();
        getCommand("storage").setExecutor(new PublicStorageMain());
        getCommand("storageloads").setExecutor(this);
    }

    @Override
    public void onDisable() {
        this.writeMain();
    }


    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args){
        if(args[0].equalsIgnoreCase("write")){
            writeMain();
        }else if(args[0].equalsIgnoreCase("forceload")){
            readMain();
        }else{
            return false;
        }

        return true;
    }

    public void readMain(){
        itemAmountMap.clear();
        ignore.clear();
        tabComplete.clear();
        try{
            patternIgnore.clear();
        }catch (Exception e){
            e.printStackTrace();
        }

        reloadConfig();
        this.load();

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

        if(!(patternIgnore.isEmpty())){
            String pattern = String.join(",",patternIgnore);
            FC.set("patternIgnore",pattern);
        }
        return;
    }

    public static RePublicStorage getInstance(){
        return instance;
    }
}
