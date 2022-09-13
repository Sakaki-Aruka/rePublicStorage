package republicstorage.republicstorage;

import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;

import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Debug {
    public void mapDebug(Player player){
        // only administrator
        if(!(player.isOp())){
            return;
        }

        for (Map.Entry<String,Long> entry : itemAmountMap.entrySet()){
            player.sendMessage("Item:"+entry.getKey()+" / Amount:"+entry.getValue());
        }
    }

    public void mapModify(Player player,String[] args){
        // only administrator
        if(args.length != 4 || !(player.isOp())){
            return;
        }

        String operation = args[1];
        double scaleDouble = 0.0;
        int scaleInt = 0;

        try{
            if(operation.equalsIgnoreCase("addition") || operation.equalsIgnoreCase("subtraction")){
                scaleInt = Integer.valueOf(args[2]);
                if(scaleInt < 1){
                    player.sendMessage("Invalid scale.[minus scale]");
                    return;
                }
            }else if(operation.equalsIgnoreCase("multi")){
                scaleDouble = Double.valueOf(args[2]);
            }else{
                return;
            }

        }catch (Exception exception){
            player.sendMessage("§cInvalid scale");
            return;
        }
        String id = args[3].toUpperCase(Locale.ROOT);
        long copy = itemAmountMap.get(id);

        if(operation.equalsIgnoreCase("addition")){
            // add
            long amount = itemAmountMap.get(id) + scaleInt;
            itemAmountMap.replace(id,amount);
            player.sendMessage("§aModify successful."+copy+" -> "+amount);

        }else if(operation.equalsIgnoreCase("subtraction")){
            // sub
            long amount = itemAmountMap.get(id) - scaleInt;
            if(amount < 0){
                player.sendMessage("Invalid scale.[over minus]");
                return;
            }
            itemAmountMap.replace(id,amount);
            player.sendMessage("§aModify successful."+copy+" -> "+amount);

        }else if(operation.equalsIgnoreCase("multi")){
            // multiplication
            long amount = Math.round(itemAmountMap.get(id) * scaleDouble);
            if(amount < 1){
                player.sendMessage("Invalid scale.[small scale]");
                return;
            }
            itemAmountMap.replace(id,amount);
            player.sendMessage("§aModify successful."+copy+" -> "+amount);

        }else{
            player.sendMessage("§cInvalid operation");
            return;
        }


    }
}
