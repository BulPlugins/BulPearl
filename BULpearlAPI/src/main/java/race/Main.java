package race;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import race.manager.CommandsManager;
import race.manager.UpdateCheckerManager;
import race.manager.VersionChecker;

import java.io.File;
import java.util.ArrayList;

public class Main extends JavaPlugin {

	final File file = new File(this.getDataFolder(), "craftregister.yml");
	final YamlConfiguration confcraft = YamlConfiguration.loadConfiguration(file);
	private static Main instance;

	public void onEnable() {
		
        new UpdateCheckerManager(this, 93260).getVersion(version -> {
            if (!this.getDescription().getVersion().equalsIgnoreCase(version)) {
                Bukkit.getLogger().info("------------------------------------------------------------------");
                Bukkit.getLogger().info("There is a new update available for BULPearl !");
                Bukkit.getLogger().info("Download here : https://www.spigotmc.org/resources/bulpearl-ender-pearl-cooldown-damage-modifier-and-other.93260/updates");
                Bukkit.getLogger().info("------------------------------------------------------------------");    
            }
        });

		createCustomConfig();
		instance = this;
		saveDefaultConfig();
		getCommand("bulpearl").setExecutor(new CommandsManager());
		getServer().getPluginManager().registerEvents(new VersionChecker(), this);
		System.out.println("[BULpearl] Finding your current server version..");
		VersionChecker vc = new VersionChecker();
		vc.versionCheck();
		System.out.println("[BULpearl] Enable BULPearl");
	}

	public void onDisable() {
		System.out.println("[BULpearl] Disable BULPearl");
	}

	public static Main getInstance() {
		return instance;
	}
	
	public ArrayList<String> getWorld() {
		ArrayList<String> ww = new ArrayList<String>(getConfig().getStringList("cooldown_blacklist_world"));
		return ww;
	}

	private void createCustomConfig() {
		File helpme;
		FileConfiguration customConfig;

		helpme = new File(getDataFolder(), "helpme.yml");
		if (!helpme.exists()) {
			helpme.getParentFile().mkdirs();
			saveResource("helpme.yml", false);
		}

		customConfig= new YamlConfiguration();
	}
}
