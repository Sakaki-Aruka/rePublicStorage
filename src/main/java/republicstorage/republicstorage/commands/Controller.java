package republicstorage.republicstorage.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import republicstorage.republicstorage.objects.QueryType;

import java.util.Collections;
import java.util.Map;

import static republicstorage.republicstorage.SettingsLoad.itemAmountMap;

public class Controller implements CommandExecutor {
    public static final String nl = System.getProperty("line.separator");
    public static final String bar = String.join("",Collections.nCopies(20,"="));
    public boolean onCommand(CommandSender sender, Command command,String string,String[] args){
        QueryType type;
        if(args.length < 1) return false;
        try{
            type = QueryType.valueOf(args[0]);
        }catch (Exception e){
            return false;
        }

        if(type.equals(QueryType.SHOW)){
            // deposit -> /storage show [itemID]
            if(args.length != 2) return false;
            String id = args[1].toUpperCase();
            if(!itemAmountMap.containsKey(id)) return false;
            Long amount = itemAmountMap.get(id);
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%s%s",bar,nl));
            builder.append(String.format("Public Storage (Show)%s",nl));
            builder.append(String.format("%s : %d%s",id,amount,nl));
            builder.append(String.format("%s%s",bar,nl));
            sender.sendMessage(builder.toString());
            return true;
        }else if(type.equals(QueryType.HELP)){
            // help -> /storage help
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%s%s",bar,nl));
            builder.append(String.format("Public Storage (Help)%s",nl));
            builder.append(String.format("help -> This command.%s",nl));
            builder.append(String.format("show -> To show amount of the target item.%s",nl));
            builder.append(String.format("pattern -> To show amount of the target items from the string.%s",nl));
            builder.append(String.format("pull -> To pull items from the public storage.%s",nl));
            builder.append(String.format("deposit -> To deposit items to the public storage.%s",nl));
            builder.append(String.format("undo -> You can undo the trades.%s%s%s",nl,bar,nl));
            sender.sendMessage(builder.toString());
            return true;
        }else if(type.equals(QueryType.PATTERN)){
            // pattern -> /storage pattern [query string]
            if(args.length != 2) return false;
            String query = args[1];
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%s%s",bar,nl));
            builder.append(String.format("Public Storage (Pattern)%s",nl));
            for(Map.Entry<String,Long> entry : itemAmountMap.entrySet()){
                if(!entry.getKey().contains(query.toUpperCase())) continue;
                builder.append(String.format("%s -> %d%s",entry.getKey(),entry.getValue(),nl));
            }
            builder.append(String.format("%s%s",bar,nl));
            sender.sendMessage(builder.toString());
            return true;
        }else if(type.equals(QueryType.PULL)){
            // pull -> /storage pull [itemID] [amount]

        }
    }
}
