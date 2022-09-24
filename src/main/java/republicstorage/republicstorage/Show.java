package republicstorage.republicstorage;

import org.bukkit.entity.Player;

import java.util.*;

import static republicstorage.republicstorage.SettingsLoad.*;

public class Show {
    public void showMain(Player player,String[] args){
        ArrayList<String> ids = new ArrayList<>(Arrays.asList(args[1].split(",")));
        for(String loop : ids){
            String upperLoop = loop.toUpperCase(Locale.ROOT);
            if(itemAmountMap.containsKey(upperLoop)){
                player.sendMessage("[Result(Show)]:"+upperLoop+" / "+itemAmountMap.get(upperLoop));
            }else if(args[1].equalsIgnoreCase("ignore")){
                // show ignore items list
                player.sendMessage("§3[Result(Ignore)]:"+String.join(",",ignore));
            }else if(args[1].equalsIgnoreCase("Pattern")){
                //show patternIgnore list
                player.sendMessage("§3[Result(PatternIgnore)]:"+String.join(",",patternIgnore));
            }else{
                player.sendMessage("§c[Result]:\""+upperLoop+"\" -> Not Found");
            }

        }
    }

    public void patternShowMain(Player player,String[] args){
        String requestID = args[1];
        player.sendMessage("[Result(PatternShow)]:");
        //collect pattern matching item-id and amount
        int ids = 0;

        for(Map.Entry<String,Long> entry : itemAmountMap.entrySet()){
            if(entry.getKey().contains(requestID.toUpperCase(Locale.ROOT))){
                player.sendMessage("[Result(Pattern)]:"+entry.getKey()+" / "+entry.getValue());
                ids++;
            }
        }

        if(ids > 30){
            player.sendMessage("§e[Warning(PatternShow)]:Too many matched.]");
            player.sendMessage("§e[Warning(PatternShow)]:"+ids+" items list was shown.");
        }
    }
}
