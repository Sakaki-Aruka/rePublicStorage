package republicstorage.republicstorage.objects;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class TradingHistory {
    private QueryType type;
    private UUID player;
    private List<ItemStack> items;
    private UUID tradeID;

    public TradingHistory(QueryType type,UUID player,List<ItemStack> items,UUID tradeID){
        this.type = type;
        this.player = player;
        this.items = items;
        this.tradeID = tradeID;
    }

    public QueryType getType() {
        return type;
    }

    public void setType(QueryType type) {
        this.type = type;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public UUID getTradeID() {
        return tradeID;
    }

    public void setTradeID(UUID tradeID) {
        this.tradeID = tradeID;
    }
}
