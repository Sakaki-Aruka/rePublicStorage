package republicstorage.republicstorage;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SettingsLoad {
    public static FileConfiguration FC;

    public static Map<String,Long> itemAmountMap = new HashMap<>();
    public static ArrayList<String> ignore;
    public static ArrayList<String> tabComplete = new ArrayList<>();

    public void fc(FileConfiguration fileConfiguration){
        FC = fileConfiguration;
        this.configLoad();
    }

    private void configLoad(){
        int items = FC.getInt("items");
        ignore = new ArrayList<>(Arrays.asList(FC.getString("ignore").split(",")));

        for(int i=0;i < items;i++){
            String itemName = FC.getString(i+".name");
            long itemAmount = FC.getLong(i+".amount");

            itemAmountMap.put(itemName,itemAmount);
            tabComplete.add(itemName);
        }
    }
}
