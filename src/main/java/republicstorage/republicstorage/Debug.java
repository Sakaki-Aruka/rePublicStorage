package republicstorage.republicstorage;

import org.bukkit.entity.Player;

import java.util.Map;

import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Debug {
    public void mapDebug(Player player){
        for (Map.Entry<String,Long> entry : itemAmountMap.entrySet()){
            player.sendMessage("Item:"+entry.getKey()+" / Amount:"+entry.getValue());
        }
    }
}
