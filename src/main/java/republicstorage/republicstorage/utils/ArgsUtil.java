package republicstorage.republicstorage.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class ArgsUtil {
    public boolean checker(String[] args){
        List<String> list = Arrays.asList(args);
        if(list.isEmpty() || list.size() == 1) return false;
        if(list.get(0).equalsIgnoreCase("deposit")) {
            if(list.size() != 3) return false;
            // TODO : write ID checker (contains "hand")
            // /storage deposit stone [1,1st,all]
            String id = list.get(1).toUpperCase();
            String amount = list.get(2);
            if(!isCorrectID(id,Arrays.asList("hand","all"))) return false;
            if(isAll(list)) return true;
            if(! new AmountUtil().isOverZero(amount)) return false;

        }else if(list.get(0).equalsIgnoreCase("pull")) {
            // TODO : write ID checker
            // /storage pull stone [limit,1,1st]
            if(list.size() != 3) return false;
            String id = list.get(1);
            String amount = list.get(2);
            if(!isCorrectID(id,null)) return false;
            if(! new AmountUtil().isCorrectPullAmount(amount)) return false;

        }
        return true;
    }

    private boolean isAll(List<String> list){
        if(list.size() != 2) return false;
        return list.get(1).equalsIgnoreCase("all");
    }

    private boolean isCorrectID(String id, List<String> add){
        for(String s : itemAmountMap.keySet()){
            if(s.equalsIgnoreCase(id)) return true;
        }
        if(add == null || add.isEmpty()) return false;
        for(String t : add){
            if(t.equalsIgnoreCase(id)) return true;
        }
        return false;
    }

}
