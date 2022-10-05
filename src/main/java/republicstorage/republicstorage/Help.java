package republicstorage.republicstorage;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
            if(player.isOp()) {
                TextComponent tc = new TextComponent();
                tc.setText("§bClick here. (Jump to the description page in GitHub rePublicStorage/usage.md)");
                ClickEvent ce = new ClickEvent(ClickEvent.Action.OPEN_URL,"https://github.com/Sakaki-Aruka/rePublicStorage/blob/master/usage.md");
                tc.setClickEvent(ce);
                player.spigot().sendMessage(tc);
                return;
            }
            //is not an operator
            new Message().send(1,"You cannot use.",player);
            return;

        }else if(args[1].equalsIgnoreCase("pull")){
            //pull
            this.line("Public Storage Pull",player);
            this.main("/storage pull [ItemID] [amount(number,'limit','st')]",player);
            this.sub("[amount(number)] : A player can pull 1 to 2000 items at once.",player);
            this.sub("[amount(limit)] : To pull items amount of that on storage 45% amount.",player);
            this.sub("[amount(st)] : You can use 'stacks' when pull. example): 1st -> 64 (in STONE)",player);
            this.lineEnd("Public Storage Pull",player);
            return;

        }else if(args[1].equalsIgnoreCase("deposit")){
            //deposit
            this.line("Public Storage Deposit",player);
            this.main("/storage deposit [Deposit-Item('all','hand',ItemID)] [amount('all','st',number)]",player);

            this.main("[Deposit-Item] :",player);
            this.sub("[Deposit-Item(all)] : To deposit all items to the storage. (not denied by the storage)",player);
            this.sub("[Deposit-Item(hand)] : To deposit items that you have that in your main-hand.",player);
            this.sub("[Deposit-Item(ItemID)] : To deposit items that was written.",player);

            this.main("[amount] :",player);
            this.sub("[amount(all)] : To deposit all [Deposit-Item] to the storage. ",player);
            this.sub("[amount(st)] : To deposit [Deposit-Item] that amount of written stacks * MaxStackSize (example: STONE 2st = STONE 128)",player);
            this.sub("[amount(number)] : To deposit written amount.",player);

            this.lineEnd("Public Storage Deposit",player);
            return;

        }else if(args[1].equalsIgnoreCase("show")){
            //show
            this.line("Public Storage Show",player);
            this.main("/storage show [ItemID]",player);
            this.sub("[ItemID] : Enter item-id(uppercase).",player);
            this.lineEnd("Public Storage Show",player);
            return;

        }else if(args[1].equalsIgnoreCase("pattern")){
            //pattern
            this.line("Public Storage Pattern",player);
            this.main("/storage pattern [Keyword]",player);
            this.sub("[Keyword] : Enter keyword (use to search).",player);
            this.lineEnd("Public Storage Pattern",player);
            return;

        }
    }

    private void main(String message,Player player){
        player.sendMessage("-> "+message);
    }

    private void sub(String message,Player player){
        player.sendMessage("---> "+message);
    }

    public void line(String title,Player player){
        player.sendMessage("--- "+title+" ---\n");
    }

    public void lineEnd(String title,Player player){
        player.sendMessage("--- "+title+" End ---\n");
    }
}
