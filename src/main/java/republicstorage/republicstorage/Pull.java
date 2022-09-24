package republicstorage.republicstorage;

import org.bukkit.Bukkit;
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
                    player.sendMessage("§c[PublicStorage]:Invalid request error.");
                    return;
                }
            }catch(Exception e){
                // fail to exchange argument type
                player.sendMessage("§c[PublicStorage]:Invalid request error.");
                return;
            }
        }

        if(args[2].equalsIgnoreCase("limit")){
            // limit type request
            int limit = Math.round(itemAmountMap.get(args[1].toUpperCase(Locale.ROOT)) * 0.45f);
            if(limit < 1){
                player.sendMessage("§c[PublicStorage]:Storage Empty error.");
                return;
            }else if(limit >2000){
                player.sendMessage("§c[PublicStorage]:Limit over the limit error.");
                return;
            }

            ItemStack itemStack = new ItemStack(Material.valueOf(args[1].toUpperCase(Locale.ROOT)));
            itemStack.setAmount(limit);
            world.dropItemNaturally(location,itemStack);

            String name = args[1].toUpperCase(Locale.ROOT);
            long remaining = itemAmountMap.get(name) - limit;
            itemAmountMap.replace(name,remaining);
            return;

        }else{
            // amount set
            if(requestAmount > 2000 || requestAmount < 1){
                player.sendMessage("§c[PublicStorage]:Invalid request error.");
                return;
            }else if(requestAmount > itemAmountMap.get(args[1].toUpperCase(Locale.ROOT))){
                // over the amount on the storage
                player.sendMessage("§c[PublicStorage]:Invalid request error.[over the limit]");
                return;
            }
            ItemStack itemStack = new ItemStack(Material.valueOf(args[1].toUpperCase(Locale.ROOT)));
            itemStack.setAmount(requestAmount);
            world.dropItemNaturally(location,itemStack);

            String name = args[1].toUpperCase(Locale.ROOT);
            long remaining = itemAmountMap.get(name) - requestAmount;

            itemAmountMap.replace(name,remaining);
            return;
        }


    }
}
