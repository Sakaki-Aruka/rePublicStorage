package republicstorage.republicstorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PublicStorageMain implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        Player player =(Player) sender;

        if(!(sender instanceof Player) || args.length != 3){
            if(!(args[0].equalsIgnoreCase("debug") || args[0].equalsIgnoreCase("modify")) && !(sender.isOp())){
                return false;
            }else if(args[0].equalsIgnoreCase("show") && args.length != 2){
                return false;
            }else if (args[0].equalsIgnoreCase("write") && args.length > 1){
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
}
