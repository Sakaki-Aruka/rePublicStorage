package republicstorage.republicstorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import republicstorage.republicstorage.utils.AmountUtil;
import republicstorage.republicstorage.utils.ArgsUtil;

import java.util.*;

import static republicstorage.republicstorage.RePublicStorage.getInstance;
import static republicstorage.republicstorage.SettingsLoad.*;

public class Pull {
//    public void pullMain(String[] args, Player player){
//        Location location = player.getLocation();
//        World world = player.getWorld();
//
//        if(args.length != 3){
//            player.sendMessage("§c[PublicStorage]:Too much arguments.");
//            return;
//        }else if(!(itemAmountMap.containsKey(args[1].toUpperCase(Locale.ROOT)))){
//            // not exist item
//            player.sendMessage("§c[PublicStorage]:No item on the storage.");
//            return;
//        }
//
//        int requestAmount= 0;
//        if(!(args[2].equalsIgnoreCase("limit"))){
//            try{
//                requestAmount = Integer.valueOf(args[2]);
//                if(requestAmount > 2000){
//                    player.sendMessage("§c[PublicStorage]:Invalid request error.");
//                    return;
//                }
//            }catch(Exception e){
//                // fail to exchange argument type
//                if(args[2].contains("st")){
//                    // stack mode -> set pull amount to use "stack"
//                    try{
//                        int stack = Integer.valueOf(args[2].replace("st",""));
//                        int reqAmount = stack * Material.valueOf(args[1].toUpperCase(Locale.ROOT)).getMaxStackSize();
//                        //check a request that over the limit->(2000)
//                        if(new stackSupport().supportMain(2001,stack,args[1],"pull")){
//                            this.pullSupport(args[1].toUpperCase(Locale.ROOT),player,reqAmount);
//                            return;
//                        }else{
//                            player.sendMessage("§c[PublicStorage]:Invalid amount to pull error.");
//                            return;
//                        }
//
//                    }catch (Exception exception){
//                        player.sendMessage("§c[PublicStorage]:Cannot read stack amount error.");
//                        return;
//                    }
//
//
//                }else if(args[2].equalsIgnoreCase("min")){
//                    // min -> empty inventory amount * material max stack size
//
//                }
//
//                player.sendMessage("§c[PublicStorage]:Invalid request error.");
//                return;
//            }
//        }
//
//        if(args[2].equalsIgnoreCase("limit")){
//            // limit type request
//            int limit = Math.round(itemAmountMap.get(args[1].toUpperCase(Locale.ROOT)) * 0.45f);
//            if(limit < 1){
//                player.sendMessage("§c[PublicStorage]:Storage Empty error.");
//                return;
//            }else if(limit >2000){
//                player.sendMessage("§c[PublicStorage]:Limit over the limit error.");
//                return;
//            }
//
//            this.pullSupport(args[1],player,limit);
//            return;
//
//        }else{
//            // amount set
//            if(requestAmount > 2000 || requestAmount < 1){
//                player.sendMessage("§c[PublicStorage]:Invalid request error.");
//                return;
//            }else if(requestAmount > itemAmountMap.get(args[1].toUpperCase(Locale.ROOT))){
//                // over the amount on the storage
//                player.sendMessage("§c[PublicStorage]:Invalid request error.[over the limit]");
//                return;
//            }
//
//
//            this.pullSupport(args[1],player,requestAmount);
//            return;
//        }
//
//
//    }
//
//    public void pullSupport(String id,Player player,int setAmount){
//        ItemStack itemStack = new ItemStack(Material.valueOf(id.toUpperCase(Locale.ROOT)));
//        itemStack.setAmount(setAmount);
//        player.getWorld().dropItemNaturally(player.getLocation(),itemStack);
//
//        String name = id.toUpperCase(Locale.ROOT);
//        long remaining = itemAmountMap.get(name) - setAmount;
//        long before = itemAmountMap.get(name);
//        player.sendMessage("§a[PublicStorage]Result:"+before+" -> "+remaining);
//
//        itemAmountMap.replace(name,remaining);
//        return;
//    }

    public static Set<UUID> PULL_INTERVAL = new HashSet<>();

    public void pull(String[] args, Player player){
        if(PULL_INTERVAL.contains(player.getUniqueId())) return; // cool-down
        if(! new ArgsUtil().checker(args)){
            player.sendMessage("Invalid query received.");
            player.sendMessage("A Pull Query must follow the grammar.");
            player.sendMessage("/storage pull [id] [amount|limit]");
            return;
        }

        // /storage pull [id] [amount|limit|stack]
        // [amount | limit | stack] -> 1 | limit | 1st
        String id = args[1].toUpperCase();
        if(!itemAmountMap.containsKey(id)) return;
        String amount = args[2];
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("=== Public Storage Pull === %s",nl));

        long pull = new AmountUtil().getAmountFromPullAmount(amount,id);
        long old = itemAmountMap.get(id);
        if(pull < 1) return;
        long update = old - pull;
        itemAmountMap.put(id,update);

        ItemStack item = new ItemStack(Material.valueOf(id),Math.toIntExact(pull));
        player.getWorld().dropItem(player.getLocation(),item);
        PULL_INTERVAL.add(player.getUniqueId());

        builder.append(String.format("%s : %d -> %d (pull %d) %s",id,old,update,pull,nl));
        builder.append(String.format("You will be able to execute a pull command in %d seconds after.%s",PULL_INTERVAL_DELAY / 20,nl));
        player.sendMessage(builder.toString());

        new BukkitRunnable() {
            @Override
            public void run(){
                PULL_INTERVAL.remove(player.getUniqueId());
            }
        }.runTaskLater(getInstance(),PULL_INTERVAL_DELAY);

    }
}
