package republicstorage.republicstorage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class RePublicStorage extends JavaPlugin {
    public void load(){
        new SettingsLoad().fc(getConfig());
    }

    @Override
    public void onEnable() {
        this.load();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
