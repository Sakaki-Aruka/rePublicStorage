package republicstorage.republicstorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;
import static republicstorage.republicstorage.SettingsLoad.tabComplete;

public class PublicStorageMain implements CommandExecutor, TabCompleter {
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        Player player =(Player) sender;
        if(args.length == 0){
            player.sendMessage("§cInvalid Storage command.");
            return false;
        }

        if(!player.hasPermission("storage.default")){
            player.sendMessage("§cYou do not have the permission to use this command.");
            return false;
        }

        if(!(sender instanceof Player) || args.length != 3){
            if(args[0].equalsIgnoreCase("show") && args.length != 2){
                return false;
            }else if (args[0].equalsIgnoreCase("write") && args.length > 1){
                return false;
            }
        }

        switch (args[0]){
            case "deposit":
                new Deposit().deposit(args,player);
                break;

            case "debug":
                if(sender.isOp()){
                    new Debug().mapDebug(player);
                }else{
                    sender.sendMessage("You cannot use this command.");
                    return false;
                }

                break;

            case "pull":
                new Pull().pull(args,player);
                break;

            case "modify":
                if(sender.isOp()){
                    new Debug().mapModify(player,args);
                }else{
                    sender.sendMessage("You cannot use this command.");
                    return false;
                }

                break;

            case "show":
                new Show().showMain(player,args);
                break;

            case "pattern":
                new Show().patternShowMain(player,args);
                break;

            case "help":
                new Help().helpMain(player,args);
                break;

        }

        return true;
    }

    public List<String> onTabComplete(CommandSender sender,Command command,String alias,String[] args){
        ArrayList<String> returnArray;

        if(args.length==1){
            ArrayList<String> category = new ArrayList<>(Arrays.asList("deposit","pull","show","debug","modify","pattern","help"));
            returnArray = new ArrayList<>();
            for(String loop : category){
                if(loop.contains(args[0])){
                    returnArray.add(loop);
                }
            }
            return returnArray;

        }else{
            if(args.length ==2){
                if(args[0].equalsIgnoreCase("deposit")){
                    returnArray = tabCompleterSupport(args[1].toUpperCase(Locale.ROOT));
                    if("all".contains(args[1])){
                        returnArray.add("all");
                    }
                    if("hand".contains(args[1])){
                        returnArray.add("hand");
                    }

                    return returnArray;
                }else if(args[0].equalsIgnoreCase("pull") || args[0].equalsIgnoreCase("show")){
                    returnArray = tabCompleterSupport(args[1].toUpperCase(Locale.ROOT));
                    return returnArray;
                }
            }else if(args.length ==3){
                if(args[0].equalsIgnoreCase("deposit") && !(args[1].equalsIgnoreCase("all"))){
                    returnArray = new ArrayList<>(Arrays.asList("all","64"));
                    return returnArray;
                }else if(args[0].equalsIgnoreCase("pull")){
                    returnArray = new ArrayList<>(Arrays.asList("limit","64"));
                    return returnArray;
                }
            }
        }
        return null;
    }

    public ArrayList<String> tabCompleterSupport(String input){
        ArrayList<String> returnArray = new ArrayList<>();
        for(Map.Entry<String,Long> entry : itemAmountMap.entrySet()){
            if(entry.getKey().contains(input)){
                returnArray.add(entry.getKey());
            }
        }
        return returnArray;
    }
}
