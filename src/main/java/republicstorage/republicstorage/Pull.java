package republicstorage.republicstorage;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Pull {
    public void pullMain(String[] args, Player player){
        Location location = player.getLocation();
        World world = player.getWorld();

        if(args.length != 3){
            player.sendMessage("§c[PublicStorage]:Too much arguments.");
            return;
        }else if(!(itemAmountMap.containsKey(args[1].toUpperCase(Locale.ROOT)))){
            // not exist item
            player.sendMessage("§c[PublicStorage]:No item on the storage.");
            return;
        }

        int requestAmount= 0;
        if(!(args[2].equalsIgnoreCase("limit"))){
            try{
                requestAmount = Integer.valueOf(args[2]);
                if(requestAmount > 2000){
                    return;
                }
            }catch(Exception e){
                // fail to exchange argument type
                return;
            }
        }

        if(args[2].equalsIgnoreCase("limit")){
            // limit type request
            int limit = Math.round(itemAmountMap.get(args[1].toUpperCase(Locale.ROOT)) * 0.45f);
            if(limit < 1){
                player.sendMessage("§c[PublicStorage]:Storage Empty error.");
                return;
            }

            ItemStack itemStack = new ItemStack(Material.valueOf(args[1].toUpperCase(Locale.ROOT)));
            itemStack.setAmount(limit);
            world.dropItemNaturally(location,itemStack);
        }else{
            // amount set
            if(requestAmount > 2000 || requestAmount < 1){
                return;
            }else if(requestAmount > itemAmountMap.get(args[1].toUpperCase(Locale.ROOT))){
                // over the amount on the storage
                return;
            }
            ItemStack itemStack = new ItemStack(Material.valueOf(args[1].toUpperCase(Locale.ROOT)));
            itemStack.setAmount(requestAmount);
            world.dropItemNaturally(location,itemStack);
            return;
        }


    }
}
