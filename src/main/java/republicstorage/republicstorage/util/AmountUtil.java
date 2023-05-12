package republicstorage.republicstorage.util;

import org.bukkit.Material;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmountUtil {
    public int getAmount(String string,Material material){
        string = string.replace(" ","");
        if(string.contains("st")) return getAmountFromStack(string,material);
        Pattern pattern = Pattern.compile("^([\\d]+)$");
        Matcher matcher = pattern.matcher(string);
        if(!matcher.find()) return -1;
        return Integer.valueOf(matcher.group(1));
    }

    private int getAmountFromStack(String string, Material material){
        if(!string.contains("st")) return -1;
        Pattern pattern = Pattern.compile("^([\\d]+)st$");
        Matcher matcher = pattern.matcher(string);
        if(!matcher.find()) return -1;
        int amount = Integer.valueOf(matcher.group(1));
        return amount * material.getMaxStackSize();
    }


}
