package republicstorage.republicstorage.processors;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import republicstorage.republicstorage.objects.QueryType;
import republicstorage.republicstorage.objects.TradingHistory;
import republicstorage.republicstorage.util.AmountUtil;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static republicstorage.republicstorage.SettingsLoad.TRADE_LIMIT;
import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Pull {
    public boolean pull(String[] args, CommandSender sender){
        // pull -> /storage pull [itemID] [amount]
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if(args.length != 3) return false;
        String id = args[1].toUpperCase();
        if(!itemAmountMap.containsKey(id)) return false;
        int amount = new AmountUtil().getAmount(args[2],Material.valueOf(id));
        if(amount == -1) return false;
        if(amount > TRADE_LIMIT) return false;

        List<ItemStack> list = Arrays.asList(new ItemStack(Material.valueOf(id),amount));
        UUID tradeID = UUID.randomUUID();
        TradingHistory history = new TradingHistory(QueryType.PULL,player.getUniqueId(),list,tradeID);
    }
}
