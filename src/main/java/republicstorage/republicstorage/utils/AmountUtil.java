package republicstorage.republicstorage.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static republicstorage.republicstorage.SettingsLoad.LIMIT_AMOUNT;

public class AmountUtil {

    private final String NUMBER_REGEX = "^([\\d]+)$";
    private final String AMOUNT_REGEX = "^(all|([\\d]+)|([\\d]+)(?i)st)$";
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

    public boolean isCorrectPullAmount(String string){
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
        return 0 < in && in < LIMIT_AMOUNT;
    }


}
