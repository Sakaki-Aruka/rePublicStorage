package republicstorage.republicstorage;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;

import static republicstorage.republicstorage.SettingsLoad.*;

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
                if(args[1].equalsIgnoreCase("ignore")){
                    //
                    if(args[2].equalsIgnoreCase("add") || args[2].equalsIgnoreCase("remove")){
                        try{
                            Material test = Material.valueOf(args[3].toUpperCase());
                        }catch (Exception exception){
                            player.sendMessage("[PublicStorage]:Invalid item id.");
                            return;
                        }
                        switch(args[2]){
                            case "add":
                                if(!(ignore.contains(args[3].toUpperCase(Locale.ROOT)))){
                                    ignore.add(args[3].toUpperCase(Locale.ROOT));
                                    player.sendMessage("[Modify Result]:"+args[3].toUpperCase(Locale.ROOT)+" added to the ignore item list.");
                                }else{
                                    player.sendMessage("[Modify Result]:The item has already exist on the ignore list. So, did not add.");
                                }
                                break;

                            case "remove":
                                if(ignore.contains(args[3])){
                                    ignore.remove(args[3].toUpperCase(Locale.ROOT));
                                    player.sendMessage("[Modify Result]:"+args[3].toUpperCase(Locale.ROOT)+" removed to the ignore item list.");
                                }else{
                                    player.sendMessage("Not find the item in the ignore item list. So, could not remove that.");
                                }
                                break;
                        }
                        return;
                    }else{
                        player.sendMessage("[PublicStorage Modify]:Invalid operation(section:ignore)");
                        return;
                    }
                }
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
