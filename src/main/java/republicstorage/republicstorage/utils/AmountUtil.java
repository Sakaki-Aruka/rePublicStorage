package republicstorage.republicstorage.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static republicstorage.republicstorage.SettingsLoad.*;

public class AmountUtil {

    private final String NUMBER_REGEX = "^([\\d]+)$";
    private final String AMOUNT_REGEX = "^(all|([\\d]+)|([\\d]+)(?i)st)$";
    private final String AMOUNT_REGEX_NO_ALL = "^(([\\d]+)|([\\d]+)(?i)st)$";
    public long getAmountFromString(String string, Material material){
        if(!string.matches(AMOUNT_REGEX)) return -1; // wrong input (not a number or "all")
        if(string.equalsIgnoreCase("all")) return (long) material.getMaxStackSize();

        long in;
        if(string.matches(NUMBER_REGEX)) in = Long.valueOf(string);
        else in = Long.valueOf(string.replace("st",""));
        return in < 0 ? -1 : in;
    }

    public long getPlayerHaveAmount(Player player, Material material){
        long amount = 0;
        List<ItemStack> items = Arrays.asList(player.getInventory().getContents());
        for(ItemStack item : items){
            if(!item.getType().equals(material)) continue;
            amount += item.getAmount();
        }
        return amount;
    }

    public boolean isOverZero(String string){
        if(!string.matches(AMOUNT_REGEX)) return false;
        if(string.equalsIgnoreCase("all")) return true;
        Long l;
        if(string.matches("^([\\d]+)(?i)st$")){
            Matcher m = Pattern.compile("^([\\d]+)(?i)st$").matcher(string);
            l = Long.valueOf(m.group(1));
        }else{
            l = Long.valueOf(string);
        }
        return l != 0;
    }

    public boolean isCorrectAmount(String string){
        if(!string.matches(NUMBER_REGEX)) return false;
        long in = Long.valueOf(string);
        return 0 < in && in < LIMIT_AMOUNT;
    }

    public boolean isCorrectPullAmount(String string, String id){
        if(string.equalsIgnoreCase("limit")) return true;
        if(!string.matches(NUMBER_REGEX)) return false;
        long in;
        if(string.matches("^([\\d]+)(?i)st$")){
            Pattern p = Pattern.compile("^([\\d]+)(?i)st$");
            Matcher m = p.matcher(string);
            in = Long.valueOf(m.group(1));
        }else{
            in = Long.valueOf(string);
        }
        if (LIMIT_AMOUNT < in) return false;
        long onStorage = itemAmountMap.containsKey(id) ? itemAmountMap.get(id) : 0;
        if (onStorage < 0) itemAmountMap.put(id,0l);  // self repairing
        return onStorage <= in;

    }

    public long getAmountFromPullAmount(String a, String id){
        long amount;
        Material material;
        if(!itemAmountMap.containsKey(id)) return 0;
        if(itemAmountMap.get(id) <= 1) return 0;
        try{
            material = Material.valueOf(id.toUpperCase());
        }catch (Exception e){
            return 0;
        }

        if(a.equalsIgnoreCase("limit")) {
            amount = Math.round(itemAmountMap.get(id) * PULL_LIMIT_RATE);
        }else{
            if(!a.matches(AMOUNT_REGEX_NO_ALL)) return 0;
            Matcher m = Pattern.compile(AMOUNT_REGEX).matcher(a);
            amount = a.matches("^([\\d]+)(?i)st$") ? Integer.valueOf(m.group(1))* material.getMaxStackSize() : Integer.valueOf(m.group(1));
        }

        if (amount > 2000) amount = 2000;
        return amount;
    }


}
