package republicstorage.republicstorage;

import org.bukkit.entity.Player;

public class Help {
    public void helpMain(Player player,String[] args){
        //show helps (use)
        /*
        /storage help
        -> show all helps

        /storage help [category]
        -> show selected category helps

        [categories]
        - admin : about commands that can use only admins

        - pull : about pull commands / arguments
        - deposit : same
        - show : same
        - pattern : same

         */

        if(args[1].equalsIgnoreCase("admin")){
            //admin
        }else if(args[1].equalsIgnoreCase("pull")){
            //pull
        }else if(args[1].equalsIgnoreCase("deposit")){
            //deposit
        }else if(args[1].equalsIgnoreCase("show")){
            //show
        }else if(args[1].equalsIgnoreCase("pattern")){
            //pattern
        }
    }
}
