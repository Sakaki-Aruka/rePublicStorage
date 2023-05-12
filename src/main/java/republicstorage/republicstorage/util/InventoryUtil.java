package republicstorage.republicstorage.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtil {
    public List<ItemStack> getItems(Player player){
        List<ItemStack> list = new ArrayList<>();
        for(int i=0;i<36;i++){
            if(player.getInventory().getItem(i) == null) continue;
            ItemStack item = player.getInventory().getItem(i);
            if(item.getMaxStackSize() == 1) continue;

        }
    }
}
