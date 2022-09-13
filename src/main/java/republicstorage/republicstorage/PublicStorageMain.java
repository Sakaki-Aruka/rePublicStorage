package republicstorage.republicstorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static republicstorage.republicstorage.SettingsLoad.tabComplete;

public class PublicStorageMain implements CommandExecutor, TabCompleter {
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        Player player =(Player) sender;

        if(!(sender instanceof Player) || args.length != 3){
            if(!(args[0].equalsIgnoreCase("debug") || args[0].equalsIgnoreCase("modify")) && !(sender.isOp())){
                return false;
            }else if(args[0].equalsIgnoreCase("show") && args.length != 2){
                return false;
            }else if (args[0].equalsIgnoreCase("write") && args.length > 1){
                return false;
            }else if(args[0].equalsIgnoreCase("deposit") && !(args[1].equalsIgnoreCase("all"))){
                return false;
            }
        }

        switch (args[0]){
            case "deposit":
                new Deposit().depositMain(args,player);
                break;

            case "debug":
                new Debug().mapDebug(player);
                break;

            case "pull":
                new Pull().pullMain(args,player);
                break;

            case "modify":
                new Debug().mapModify(player,args);
                break;

            case "show":
                new Show().showMain(player,args);
                break;

        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender,Command command,String alias,String[] args){
        ArrayList<String> returnArray;

        if(args.length==1 && !(sender.isOp())){
            returnArray = new ArrayList<>(Arrays.asList("deposit","pull","show"));
            return returnArray;
        }else if(sender.isOp()){
            if(args.length ==1){
                returnArray = new ArrayList<>(Arrays.asList("deposit","pull","show","modify","debug"));
                return returnArray;
            }else if(args.length ==2){
                if(args[1].equalsIgnoreCase("deposit")){
                    returnArray = (ArrayList<String>) tabComplete.clone();
                    returnArray.add("all");
                    returnArray.add("hand");
                    return returnArray;
                }else if(args[1].equalsIgnoreCase("pull") || args[1].equalsIgnoreCase("show")){
                    returnArray = (ArrayList<String>) tabComplete.clone();
                    return returnArray;
                }
            }
        }
        return null;
    }
}
