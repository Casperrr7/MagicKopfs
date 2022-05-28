package me.Casper.Kopf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.Casper.Kopf.Command.KopfCommand;
import me.Casper.Kopf.Gui.KopfGui;
import me.Casper.Kopf.Listener.InventoryClickListener;
import me.Casper.Kopf.Utils.Utils;



public class Main  extends JavaPlugin {
	
    public File playerConfigFile;
    public static FileConfiguration playerConfig;
    
    public static int KopfWait;
    public static int KopfWait2;
    
	@Override
	public void onEnable() {
		new KopfCommand(this);
		new InventoryClickListener(this);
		new KopfGui(this);
		
		KopfGui.initialize();
		createConfigs();
		reloadConfigSet();
	}
	
	public void onDisable() {
		saveConfig();
		savePlayerConfig();
	}
	
	public static ItemStack nameLore(ItemStack i, String name, String... loreString) {
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(Utils.chat(name));
		im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		List<String> lore = new ArrayList<String>();
		for (String s : loreString) {
			lore.add(Utils.chat(s));
		}
		
		im.setLore(lore);
		i.setItemMeta(im);
		
		return i;
	}
	
	
	private void createConfigs() {
		//MapsConfig
		        playerConfigFile = new File(getDataFolder(), "Players.yml");
		        if (!playerConfigFile.exists()) {
		        	playerConfigFile.getParentFile().mkdirs();
		            this.saveResource("Players.yml", false);
		        }
		   
		        playerConfig = new YamlConfiguration();
		        try {
		            playerConfig.load(playerConfigFile);
		        }catch (IOException | InvalidConfigurationException | NullPointerException e) {
		            e.printStackTrace();
		        }
		        savePlayerConfig();
		    }
	
	
    public void savePlayerConfig() {
        try {
            playerConfig.save(playerConfigFile);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int WaitTime(Player p) {
    	int w = 0;
    	if(playerConfig.contains("Players." + p.getUniqueId())) {
    	}
    	
    	return w;
    }
    
    public void reloadConfigSet() {
    	reloadConfig();
    	if(getConfig().contains("KopfWaitDaysNormal")) {
    		try {
    		int waitt = getConfig().getInt("KopfWaitDaysNormal");
    		
    		KopfWait = waitt * 24*60* 60 * 1000;
    		} catch(Exception e)
    		{
    			Bukkit.getConsoleSender().sendMessage(Utils.chat("&4&lMAKE SURE THE LINE KopfWaitDaysNormal IN CONFIG IS AN INT!"));
    		}
    	} else {
    		getConfig().set("KopfWaitDaysNormal", 32);
    		saveConfig();
    		KopfWait = 1 *24*60*  60 * 1000;
    	}
    	
    	if(getConfig().contains("KopfWaitDaysPremium")) {
    		try {
    		int waitt = getConfig().getInt("KopfWaitDaysPremium");
    		
    		KopfWait2 = waitt * 24*60* 60 * 1000;
    		} catch(Exception e)
    		{
    			Bukkit.getConsoleSender().sendMessage(Utils.chat("&4&lMAKE SURE THE LINE KopfWaitDaysPremium IN CONFIG IS AN INT!"));
    		}
    	} else {
    		getConfig().set("KopfWaitDaysPremium", 14);
    		saveConfig();
    		KopfWait2 = 1 *24*60*  60 * 1000;
    	}
    }
}
