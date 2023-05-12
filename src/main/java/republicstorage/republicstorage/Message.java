package republicstorage.republicstorage;

import org.bukkit.entity.Player;

public class Message {
    public void send(int type, String optional, Player player){
        if(type==0) {
            //successful
            player.sendMessage("§a[PublicStorage]:" + optional);
            return;
        }else if(type==1){
            //error
            player.sendMessage("§c[PublicStorage]:"+ optional);
            return;
        }
    }
}
