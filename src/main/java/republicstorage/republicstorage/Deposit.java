package republicstorage.republicstorage;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import republicstorage.republicstorage.utils.AmountUtil;
import republicstorage.republicstorage.utils.ArgsUtil;


import static republicstorage.republicstorage.SettingsLoad.*;

public class Deposit{
    private final int MAIN_HAND_SLOT = 98;
    public void deposit(String[] args, Player player){
        if(! new ArgsUtil().checker(args)){
            player.sendMessage("Invalid query received.");
            player.sendMessage("A Deposit Query must follow the grammar.");
            player.sendMessage("/storage deposit [id|hand|all] [1,1st,all]");
            return;
        }

        String id = args[1].toUpperCase();
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("=== Public Storage Deposit === %s",nl));

        if(id.equalsIgnoreCase("all")){
            for(int i=0;i < player.getInventory().getSize();i++){
                if(player.getInventory().getItem(i) == null) continue;
                if(player.getInventory().getItem(i).getType().equals(Material.AIR)) continue;
                ItemStack item = player.getInventory().getItem(i);
                long old = getAmount(item.getType().name());
                long add = item.getAmount();
                itemAmountMap.put(item.getType().name(),old + add);
                builder.append(String.format("%s : %d -> %d (add %d) %s",item.getType().name(),old,old + add,add,nl));

                removeItem(player.getInventory(),i,add);
            }

        }else if (id.equalsIgnoreCase("hand")){
            if (player.getInventory().getItemInMainHand() == null) return;
            if (player.getInventory().getItemInMainHand().equals(Material.AIR)) return;
            ItemStack item = player.getInventory().getItemInMainHand();
            Material material = item.getType();
            long old = getAmount(id);
            long add;
            if((add = new AmountUtil().getAmountFromString(args[2],material)) == -1) return;
            if(add > item.getAmount()) add = item.getAmount();
            itemAmountMap.put(material.name(),old + add);
            builder.append(String.format("%s : %d -> %d (deposit %d) %s",item.getType().name(),old,old + add,add,nl));

            removeItem(player.getInventory(),MAIN_HAND_SLOT,add);
        }

        builder.append(String.format("=== Public Storage Deposit End === %s",nl));
        player.sendMessage(builder.toString());
    }

    private long getAmount(String id){
        return itemAmountMap.containsKey(id) ? itemAmountMap.get(id) : 0;
    }

    private void removeItem(Inventory inventory, int slot, long remove){
        int now = inventory.getItem(slot).getAmount();
        int removeInt = Math.toIntExact(remove);
        if(now - removeInt > 0) inventory.getItem(slot).setAmount(now - Math.toIntExact(remove));
        else inventory.getItem(slot).setAmount(0);
    }

}
