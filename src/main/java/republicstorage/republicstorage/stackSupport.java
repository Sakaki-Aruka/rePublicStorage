package republicstorage.republicstorage;

import org.bukkit.Material;

import java.util.Locale;

import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class stackSupport {
    public boolean supportMain(int limit,int stacks,String id,String type){
        Material material = Material.valueOf(id.toUpperCase(Locale.ROOT));
        int reqAmount = stacks * material.getMaxStackSize();
        if(0 < reqAmount && reqAmount < limit && itemAmountMap.get(id.toUpperCase(Locale.ROOT)) - reqAmount > 1 && type.equalsIgnoreCase("pull")){
            // pull
            return true;
        }else if(0 < reqAmount && reqAmount < limit && type.equalsIgnoreCase("deposit")){
            // deposit
            return true;
        }else{
            return false;
        }
    }
}
