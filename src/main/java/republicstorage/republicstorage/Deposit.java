package republicstorage.republicstorage;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static republicstorage.republicstorage.SettingsLoad.ignore;
import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Deposit{
    public void depositMain(String[] args, Player player){

        if(args[1].equalsIgnoreCase("all") && args.length==2){
            // all deposit
            Inventory inventory = player.getInventory();
            for (int i=0;i < player.getInventory().getSize(); i++){

                String itemName;
                try{
                    itemName= inventory.getItem(i).getType().name();
                } catch (Exception exception){
                    continue;
                }
                ItemStack itemStack = inventory.getItem(i);


                //debug (ignoreCheck use)
                if(itemStack.getEnchantments().isEmpty() && ignoreCheck(itemName)){
                    // exist item and no enchantment
                    if(itemAmountMap.containsKey(itemName)){
                        // exist on the storage hashmap
                        long value = itemAmountMap.get(itemName) + itemStack.getAmount();
                        itemAmountMap.replace(itemName,value);
                        itemStack.setAmount(0);

                    }else{
                        // not exist on the hashmap
                        long value = itemStack.getAmount();
                        itemAmountMap.put(itemName,value);
                        itemStack.setAmount(0);
                    }

                }else{
                    //debug
                    player.sendMessage("enchant:"+itemStack.getEnchantments());

                    // not exist item
                    player.sendMessage("§cInvalid item.1");
                    continue;
                }
            }
        }else if(args[2].equalsIgnoreCase("all")){

            String idUpper = args[1].toUpperCase(Locale.ROOT);
            PlayerInventory playerInventory = player.getInventory();

            if(args[1].equalsIgnoreCase("hand") && playerInventory.getItemInMainHand() != null){
                // id:hand && amount:all
                if(this.ignoreCheck(playerInventory.getItemInMainHand().getType().name())){
                    // not ignore in main hand
                    String mainHandID = playerInventory.getItemInMainHand().getType().name();
                    for (int i=0;i < playerInventory.getSize();i++){

                        ItemStack itemStack;
                        try{
                            itemStack = playerInventory.getItem(i);
                            if(itemStack == null){
                                continue;
                            }
                        }catch (Exception exception){
                            // no item on the slot
                            continue;
                        }

                        if(itemStack.getType().name().equals(mainHandID)){
                            if(itemAmountMap.containsKey(itemStack.getType().name())){
                                // exist on the hashmap
                                long value = itemAmountMap.get(mainHandID) + itemStack.getAmount();
                                itemAmountMap.replace(mainHandID,value);
                                itemStack.setAmount(0);

                            }else{
                                // not exist on the hashmap
                                long value = itemStack.getAmount();
                                itemAmountMap.put(mainHandID,value);
                                itemStack.setAmount(0);

                            }
                        }
                    }

                }else{
                    player.sendMessage("§cInvalid item. [ignore item]");
                    return;
                }
            }else{
                // id:[ID] && amount:all
                try{
                    Material material = Material.valueOf(idUpper);
                }catch (Exception exception){
                    player.sendMessage("§cInvalid item.");
                    return;
                }
                for(int i=0;i < playerInventory.getSize();i++){

                    ItemStack itemStack;
                    try{
                        itemStack = playerInventory.getItem(i);
                    }catch (Exception exception){
                        continue;
                    }

                    if(itemStack.getType().name().equalsIgnoreCase(idUpper) && itemAmountMap.containsKey(idUpper)){
                        // exist the id on the hashmap
                        long value = itemAmountMap.get(idUpper) + itemStack.getAmount();
                        itemAmountMap.replace(idUpper,value);
                        itemStack.setAmount(0);

                    }else if(itemStack.getType().name().equalsIgnoreCase(idUpper) && !(itemAmountMap.containsKey(idUpper))){
                        // not exist the id on the hashmap
                        long value = itemStack.getAmount();
                        itemAmountMap.put(idUpper,value);
                        itemStack.setAmount(0);

                    }
                }

            }
        }else{
            // set amount number
            int requestAmount;
            int requestCopy;
            try{
                requestAmount = Integer.valueOf(args[2]);
                requestCopy = requestAmount;
                if(requestAmount < 0 || 2000 < requestAmount){
                    player.sendMessage("§cInvalid amount.[<0 or >2000");
                    return;
                }
                try{
                    Material test = Material.valueOf(args[1].toUpperCase(Locale.ROOT));
                }catch (Exception exception){
                    player.sendMessage("§cInvalid ItemID.");
                    return;
                }
            }catch(Exception exception){
                player.sendMessage("§cInvalid amount.");
                return;
            }

            String idUpper = args[1].toUpperCase(Locale.ROOT);
            PlayerInventory playerInventory = player.getInventory();


            // hashmap contains the key
            for (int i=0;i < playerInventory.getSize();i++){
                try{
                    if(player.getInventory().getItem(i).getType().name().equalsIgnoreCase(idUpper)){
                        if(requestAmount - playerInventory.getItem(i).getAmount() < 0){
                            // finish loop
                            playerInventory.getItem(i).setAmount(playerInventory.getItem(i).getAmount() - requestAmount);

                            if(itemAmountMap.containsKey(idUpper)){
                                itemAmountMap.replace(idUpper,itemAmountMap.get(idUpper)+(long)requestCopy);
                                player.sendMessage("§aFinish deposit.");

                                return;
                            }else{
                                long value = requestCopy;
                                itemAmountMap.put(idUpper,value);
                                player.sendMessage("§aFinish deposit.");
                            }


                        }else{
                            if(i == playerInventory.getSize()-1){
                                player.sendMessage("§cShortage:"+requestAmount);

                                if(itemAmountMap.containsKey(idUpper)){
                                    itemAmountMap.replace(idUpper,itemAmountMap.get(idUpper) +(long)requestCopy - requestAmount);
                                    return;
                                }else{
                                    long value = requestCopy - requestAmount;
                                    itemAmountMap.put(idUpper,value);
                                }

                            }else{
                                requestAmount -= player.getInventory().getItem(i).getAmount();
                                playerInventory.getItem(i).setAmount(0);
                            }
                        }
                    }
                }catch (Exception exception){
                    continue;
                }
            }
        }
    }

    boolean ignoreCheck(String itemName){

        //debug
        //return true;


        if(ignore.contains(itemName)){
            return false;
        }else{
            return true;
        }
    }
}
