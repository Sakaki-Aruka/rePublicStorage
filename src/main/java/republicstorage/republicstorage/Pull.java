package republicstorage.republicstorage;

import org.bukkit.World;
import org.bukkit.entity.Player;

import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Pull {
    public void pullMain(String[] args, Player player){
        if(!(args[1].equalsIgnoreCase("limit")) || args.length != 3){
            player.sendMessage("§[PublicStorage]:Cannot use.[Invalid argument error]");
            return;
        }else if(!(itemAmountMap.containsKey(args[2]))){
            // not exist item on the map
            player.sendMessage("[PublicStorage]:Cannot use.[Invalid ItemID error]");
            return;
        }



    }
}
