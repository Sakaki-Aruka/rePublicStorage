package republicstorage.republicstorage;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

import static republicstorage.republicstorage.SettingsLoad.ignore;
import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Deposit{
    public void depositMain(String[] args, Player player){

        int requestAmount =0;
        try{
            if(!(args[1].equalsIgnoreCase("all")) && !(args[1].equalsIgnoreCase("hand"))){
                requestAmount = Integer.valueOf(args[2]);
            }
        }catch (Exception exception){
            System.out.println("[PublicStorage]:Request amount type error.[Not int]");
            return;
        }

        if(args[1].equalsIgnoreCase("all")){
            // storage deposit all
            for(int i=0;i < player.getInventory().getSize();i++){
                ItemStack itemStack = player.getInventory().getItem(i);
                if(itemStack == null || this.ignoreCheck(itemStack.getType().name()) || itemStack.getEnchantments() != null){
                    // ignore item or no item on a target slot
                    continue;
                }else{
                    // exist uploadable item
                    String name = itemStack.getType().name();
                    if(itemAmountMap.containsKey(name)){
                        long amountOnMap = itemAmountMap.get(name) + itemStack.getAmount();
                        itemAmountMap.replace(name,amountOnMap);
                    }else{
                        long amountPut = itemStack.getAmount();
                        itemAmountMap.put(name,amountPut);
                    }

                    itemStack.setAmount(0);

                }
            }

        }else if(args[1].equalsIgnoreCase("hand")){
            // storage deposit hand
            String name = player.getInventory().getItemInOffHand().getType().name();
            if(!(this.ignoreCheck(name))){
                player.sendMessage("§cDenied to upload.[Invalid item error]");
                //ignore check
                return;
            }

            for(int i=0;i < player.getInventory().getSize(); i++){
                ItemStack loopStack = player.getInventory().getItem(i);
                if(loopStack == null || !(loopStack.getType().name().equalsIgnoreCase(name)) || loopStack.getEnchantments() != null){
                    // no item or not match item on a slot
                    continue;
                }else if(loopStack.getType().name().equalsIgnoreCase(name)){
                    // match items name
                    long amountOnMap = itemAmountMap.get(name) + loopStack.getAmount();
                    itemAmountMap.replace(name,amountOnMap);
                    loopStack.setAmount(0);
                }
            }

        }else if(itemAmountMap.containsKey(args[1].toUpperCase(Locale.ROOT))){
            // storage deposit [ItemId]
            String requestId = args[1].toUpperCase(Locale.ROOT);
            if(this.ignoreCheck(requestId)){
                player.sendMessage("§cDenied to upload.[Invalid item error]");
                return;
            }
            for (int i=0;i < player.getInventory().getSize();i++){
                ItemStack loopStack = player.getInventory().getItem(i);
                if(loopStack == null || !(loopStack.getType().name().equalsIgnoreCase(requestId)) || loopStack.getEnchantments() != null){
                    continue;
                }else if(loopStack.getType().name().equalsIgnoreCase(requestId)){
                    // match request item

                    if(requestAmount - loopStack.getAmount() <= loopStack.getMaxStackSize()){
                        loopStack.setAmount(0);
                        requestAmount -= loopStack.getAmount();
                        long amountOnMap = itemAmountMap.get(requestId) + loopStack.getAmount();
                        itemAmountMap.replace(requestId,amountOnMap);

                    }else if(requestAmount - loopStack.getAmount() <= 0){
                        loopStack.setAmount(loopStack.getAmount() - requestAmount);
                        long amountOnMap = itemAmountMap.get(requestId) + (loopStack.getAmount() - requestAmount);
                        itemAmountMap.replace(requestId,amountOnMap);
                        return;
                    }
                    if(i==player.getInventory().getSize() -1 && requestAmount - loopStack.getAmount() > 0){
                        player.sendMessage("[PublicStorage]:Remaining "+requestAmount);
                        return;
                    }
                }
            }
        }else{
            player.sendMessage("§c[PublicStorage]:Cannot use.[Invalid argument]");
            return;
        }

    }

    boolean ignoreCheck(String itemName){
        if(ignore.contains(itemName)){
            return false;
        }else{
            return true;
        }
    }
}
