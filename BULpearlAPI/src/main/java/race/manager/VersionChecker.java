package race.manager;

import manager.*;
import org.bukkit.event.Listener;

import pearleffect.*;
import race.Main;

public class VersionChecker implements Listener {
    private final Main cg = Main.getInstance();

    public void versionCheck() {
        String versionsrv = cg.getServer().getVersion();

        if (versionsrv.contains("1.8")) {
            cg.getServer().getPluginManager().registerEvents(new PearlDamage_1_8(), cg);
            cg.getServer().getPluginManager().registerEvents(new PearlThrowing_1_8(), cg);
            cg.getServer().getPluginManager().registerEvents(new ActionBarManager_1_8(), cg);
            cg.getServer().getPluginManager().registerEvents(new CraftManager_1_8(), cg);
            cg.getCommand("bulpearl").setExecutor(new CMDCraft_1_8());
            System.out.println("[BULpearl] Version of your server found : 1.8");
            CraftManager_1_8 ccc18 = new CraftManager_1_8();
            ccc18.createCraft();
        } else if (versionsrv.contains("1.9")) {
            cg.getServer().getPluginManager().registerEvents(new PearlDamage_1_9(), cg);
            cg.getServer().getPluginManager().registerEvents(new PearlThrowing_1_9(), cg);
            cg.getServer().getPluginManager().registerEvents(new ActionBarManager_1_9(), cg);
            cg.getServer().getPluginManager().registerEvents(new CraftManager_1_9(), cg);
            cg.getCommand("bulpearl").setExecutor(new CMDCraft_1_9());
            System.out.println("[BULpearl] Version of your server found : 1.9");
            CraftManager_1_9 ccc19 = new CraftManager_1_9();
            ccc19.createCraft();
        } else if (versionsrv.contains("1.14")) {
            cg.getServer().getPluginManager().registerEvents(new PearlDamage_1_14(), cg);
            cg.getServer().getPluginManager().registerEvents(new PearlThrowing_1_14(), cg);
            cg.getServer().getPluginManager().registerEvents(new CraftManager_1_14(), cg);
            cg.getCommand("bulpearl").setExecutor(new CMDCraft_1_14());
            System.out.println("[BULpearl] Version of your server found : 1.14");
            CraftManager_1_14 ccc14 = new CraftManager_1_14();
            ccc14.createCraft();
        } else if (versionsrv.contains("1.15")) {
            cg.getServer().getPluginManager().registerEvents(new PearlDamage_1_15(), cg);
            cg.getServer().getPluginManager().registerEvents(new PearlThrowing_1_15(), cg);
            cg.getServer().getPluginManager().registerEvents(new CraftManager_1_15(), cg);
            cg.getCommand("bulpearl").setExecutor(new CMDCraft_1_15());
            System.out.println("[BULpearl] Version of your server found : 1.15");
            CraftManager_1_15 ccc15 = new CraftManager_1_15();
            ccc15.createCraft();
        } else if (versionsrv.contains("1.16")) {
            cg.getServer().getPluginManager().registerEvents(new PearlDamage_1_16(), cg);
            cg.getServer().getPluginManager().registerEvents(new PearlThrowing_1_16(), cg);
            cg.getServer().getPluginManager().registerEvents(new CraftManager_1_16(), cg);
            cg.getCommand("bulpearl").setExecutor(new CMDCraft_1_16());
            System.out.println("[BULpearl] Version of your server found : 1.16");
            CraftManager_1_16 ccc16 = new CraftManager_1_16();
            ccc16.createCraft();
        } else {
            System.out.println("[BULpearl] Can't found your Minecraft Server version, disable BULpearl");
            cg.onDisable();
        }
    }
}
