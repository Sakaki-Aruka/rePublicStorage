package republicstorage.republicstorage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

import static republicstorage.republicstorage.SettingsLoad.ignore;
import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Deposit{
    public void depositMain(String[] args, Player player){
        //
    }

    boolean ignoreCheck(String itemName){
        if(ignore.contains(itemName)){
            return false;
        }else{
            return true;
        }
    }
}
