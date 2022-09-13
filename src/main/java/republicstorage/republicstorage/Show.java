package republicstorage.republicstorage;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import static republicstorage.republicstorage.SettingsLoad.ignore;
import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Show {
    public void showMain(Player player,String[] args){
        ArrayList<String> ids = new ArrayList<>(Arrays.asList(args[1].split(",")));
        for(String loop : ids){
            String upperLoop = loop.toUpperCase(Locale.ROOT);
            if(itemAmountMap.containsKey(upperLoop)){
                player.sendMessage("[Result(Show)]:"+upperLoop+" / "+itemAmountMap.get(upperLoop));
            }else if(args[1].equalsIgnoreCase("ignore")){
                // show ignore items list
                player.sendMessage("§3[Result(Ignore)]:"+String.join(",",ignore));
            }else{
                player.sendMessage("§c[Result]:\""+upperLoop+"\" -> Not Found");
            }

        }
    }
}
