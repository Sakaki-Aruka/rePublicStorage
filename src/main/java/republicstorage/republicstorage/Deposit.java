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

import static republicstorage.republicstorage.SettingsLoad.*;

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
                    // not exist item
                    player.sendMessage("§cInvalid item found. Slot "+i+" / "+itemStack.getType().name());
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
                        if(itemStack == null){
                            continue;
                        }
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
                this.finishMessages(player,idUpper);
                return;
            }
        }else{
            // set amount number
            int requestAmount;
            try{
                requestAmount = Integer.valueOf(args[2]);

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
                //amount used "stack"
                if(args[2].contains("st")){
                    try{
                        int stack = Integer.valueOf(args[2].replace("st",""));

                        String id;
                        if(args[1].equalsIgnoreCase("hand")){
                            id = player.getInventory().getItemInMainHand().getType().toString();
                        }else{
                            id = args[1].toUpperCase(Locale.ROOT);
                        }

                        if(new stackSupport().supportMain(2001,stack,id,"deposit")){
                            requestAmount = stack * Material.valueOf(id).getMaxStackSize();
                        }else{
                            player.sendMessage("§c[PublicStorage]:A request amount -> over the limit(2000).Error.");
                            return;
                        }
                    }catch (Exception e){
                        player.sendMessage("§c[PublicStorage]:Invalid Amount.");

                        //debug
                        player.sendMessage("inMainHand:"+player.getInventory().getItemInMainHand().getType());
                        player.sendMessage("inMainHand-Name:"+player.getInventory().getItemInMainHand().getType().name());
                        player.sendMessage("inMainHand-toString:"+player.getInventory().getItemInMainHand().getType().toString());

                        return;
                    }
                }else{
                    player.sendMessage("§cInvalid amount.");
                    return;
                }

            }

            String idUpper;
            if(args[1].equalsIgnoreCase("hand")){
                idUpper = player.getInventory().getItemInMainHand().getType().toString();
            }else{
                idUpper = args[1].toUpperCase(Locale.ROOT);
            }


            // hashmap contains the key
            long LongRequest = requestAmount;
            long playerHave = this.getPlayerHave(Material.valueOf(idUpper),player);
            if(playerHave == 0){
                new Message().send(1,"Do not have.",player);
                return;
            }

            long diff = playerHave - LongRequest;
            long onStorage;
            if(itemAmountMap.containsKey(idUpper)){
                onStorage = itemAmountMap.get(idUpper);
            }else{
                onStorage = 0;
            }

            long remove;

            if(diff >= 0){
                // have equals remove
                remove = LongRequest;
            }else{
                // have shorter than remove
                remove = playerHave;
            }
            this.remove(remove,playerHave,player,Material.valueOf(idUpper));

            //debug
            player.sendMessage("playerHave:"+playerHave);
            player.sendMessage("playerRequest:"+LongRequest);
            player.sendMessage("remove:"+remove);


            itemAmountMap.replace(idUpper,onStorage + remove);
            String send = "deposit: "+onStorage+" -> "+(onStorage+remove);
            new Message().send(0,send,player);
            return;


        }
    }

    private void finishMessages(Player player,String idUpper){
        player.chat("/storage show "+idUpper);
        player.sendMessage("§aFinish deposit.");
        return;
    }

    private long getPlayerHave(Material material,Player player){
        long amount = 0;

        for(int i=0;i<36;i++){
            Inventory inventory = player.getInventory();
            try{
                if(inventory.getItem(i).getType().equals(material)){
                    amount += inventory.getItem(i).getAmount();
                }
            }catch (Exception e){
                //null
            }
        }

        return amount;
    }

    private void remove(long remove,long have,Player player,Material material){
        int slots = 36;
        long erase = 0;

        //ignore check
        if(!this.ignoreCheck(material.name())){
            new Message().send(1,"Invalid ID(Ignored)",player);
            return;
        }

        for(int i=0;i<slots;i++){

            //debug
            player.sendMessage("slot"+i+" in remove method.");

            ItemStack itemStack = player.getInventory().getItem(i);
            if(itemStack == null) {
                //null
            }else{
                if (itemStack.getType().equals(material)) {
                    if(erase + itemStack.getAmount() < remove){
                        erase = erase + itemStack.getAmount();
                        itemStack.setAmount(0);

                        //debug
                        player.sendMessage("erase:"+erase);

                    }else if(erase + itemStack.getAmount() >= remove){
                        int remaining = (int)(have - remove) % material.getMaxStackSize();
                        itemStack.setAmount(remaining);
                        return;

                    }
                }
            }
        }
    }

    private boolean ignoreCheck(String itemName){


        try{
            if(!(patternIgnore.isEmpty())){
                for (String loop : patternIgnore){
                    if(itemName.contains(loop)){

                        //debug
                        System.out.println("Ignored by PatternIgnore:"+itemName);
                        return false;
                    }
                }
            }

            Material material = Material.valueOf(itemName);
            short durability = material.getMaxDurability();
            int maxStack = material.getMaxStackSize();
            if(maxStack == 1){
                //exists durability and max stack is 1.
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        if(ignore.contains(itemName)){
            return false;
        }else{
            return true;
        }
    }
}
