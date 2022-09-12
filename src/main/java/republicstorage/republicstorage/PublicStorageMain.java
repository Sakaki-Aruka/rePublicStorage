package republicstorage.republicstorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PublicStorageMain implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command,String label,String[] args){
        if(!(sender instanceof Player) || args.length != 3){
            return false;
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
        }

        return true;
    }
}
