package republicstorage.republicstorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PublicStorageMain implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(!(sender instanceof Player) || args.length != 3){
            if(!(args[0].equalsIgnoreCase("debug") || args[0].equalsIgnoreCase("modify")) && !(sender.isOp())){
                return false;
            }else if(args[0].equalsIgnoreCase("show") && args.length != 2){
                return false;
            }
        }

        switch (args[0]){
            case "deposit":
                new Deposit().depositMain(args,(Player) sender);
                break;

            case "debug":
                new Debug().mapDebug((Player) sender);
                break;

            case "pull":
                new Pull().pullMain(args,(Player) sender);
                break;

            case "modify":
                new Debug().mapModify((Player)sender,args);
                break;

            case "show":
                new Show().showMain((Player) sender,args);
                break;
        }

        return true;
    }
}
