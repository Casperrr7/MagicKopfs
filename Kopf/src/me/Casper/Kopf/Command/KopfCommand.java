package me.Casper.Kopf.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.Casper.Kopf.Main;
import me.Casper.Kopf.Gui.KopfGui;
import me.Casper.Kopf.Utils.Utils;

public class KopfCommand implements CommandExecutor, TabCompleter {
		private static Main plugin;
			
			@SuppressWarnings("static-access")
			public KopfCommand(Main plugin) {
				this.plugin = plugin;
								
				plugin.getCommand("kopf").setExecutor(this);
			}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	@Override
		public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
				Player p = (Player) sender;
				plugin.savePlayerConfig();
				if(!p.hasPermission("Kopf.use")) {
					p.sendMessage(Utils.chat("&7[&3&lKopf&7] &cSie haben keine Berechtigung dafür!"));
					return true;
				}
				if(args.length == 0) {
					p.openInventory(KopfGui.GUI(p));
					return true;
				} if(args.length == 1) {
					if(args[0].equalsIgnoreCase("bypass")) {
						if(!p.hasPermission("Kopf.Time.Bypass")) {
							p.sendMessage(Utils.chat("&7[&3&lKopf&7] &cSie haben keine Berechtigung dafür!"));
							return true;
						}
						
						if(plugin.playerConfig.contains("Players." + p.getUniqueId())) {
							plugin.playerConfig.set("Players." + p.getUniqueId(), null);
							p.sendMessage(Utils.chat("&7[&3&lKopf&7] &6Du hast die Zeit übersprungen! Sie können den Befehl jetzt verwenden!"));
							plugin.savePlayerConfig();
							return true;
						} else {
							p.sendMessage(Utils.chat("&7[&3&lKopf&7] &cSie haben keine Wartezeit!"));
							return true;
						}
					} else if(args[0].equalsIgnoreCase("reload")) {
						if(!p.hasPermission("Kopf.Admin")) {
							p.sendMessage(Utils.chat("&7[&3&lKopf&7] &cSie haben keine Berechtigung dafür!"));
							return true;
						} else {
							p.sendMessage(Utils.chat("&7[&3&lKopf&7] &6Reloaded Config"));
							plugin.reloadConfigSet();
							plugin.saveConfig();
							return true;
						}
					} else {
						int days = getWait(p);
						if(days != 0) {
							p.sendMessage(Utils.chat("&7[&3&lKopf&7] &cDu musst noch " + days + " tage warten!"));
							return true;
						}
						//deprecated but still works
						OfflinePlayer po = Bukkit.getOfflinePlayer(args[0]);
						if(po != null) {
						ItemStack playerSkull = Utils.createItemPlayerHead(po, "&e" + args[0] + "'s Head");
						giveDirect(p, playerSkull);
						setWait(p);
						p.sendMessage(Utils.chat("&7[&3&lKopf&7] &6Kopf erzeugt!"));
						} else {
							p.sendMessage(Utils.chat("&7[&3&lKopf&7] &cDiese Person existiert nicht"));
						}
					}
				} else {
					p.sendMessage(Utils.chat("&7[&3&lKopf&7] &cFalsche Verwendung! /kopf <Name>"));
				}
				
					return true;			
			}
	
	@SuppressWarnings("static-access")
	private static void setWait(Player p) {
		if(plugin.KopfWait > 0) {
		plugin.playerConfig.set("Players." + p.getUniqueId(), System.currentTimeMillis());
		}
		plugin.savePlayerConfig();
	}
	
	@SuppressWarnings("static-access")
    public static int getWait(Player p) {
		int days = 0;
		if(plugin.playerConfig.contains("Players." + p.getUniqueId())) {
			int kopfWait = plugin.KopfWait;
			if(p.hasPermission("Kopf.Tier2"))
				kopfWait = plugin.KopfWait2;
			
			long date1 = plugin.playerConfig.getLong("Players." + p.getUniqueId()) + kopfWait;
			long date2 = System.currentTimeMillis();;
			long dif = date1 - date2;
			int seconds = (int) (dif / 1000);	

			if(dif > 0) {			
				if(seconds >= 60*60*24){
					int days2 = seconds / (60*60*24);
					seconds = seconds % (60*60*24);
					
					days = days2 +1;
				} else {
					days = 1;
				}
			} else {
				plugin.playerConfig.set("Players." + p.getUniqueId(), null);
				plugin.savePlayerConfig();
			}
		}
		return days;
	}
	
	public void giveDirect(Player p, ItemStack i) {
		HashMap<Integer, ItemStack> leftOver = new HashMap<Integer, ItemStack>();
        leftOver.putAll((p.getInventory().addItem(i)));
        if (!(leftOver.isEmpty())) {
            Location loc = p.getLocation();
            p.getWorld().dropItem(loc, new ItemStack(Material.getMaterial(String.valueOf(leftOver.get(0).getType().name())), leftOver.get(0).getAmount()));
	}
	}
	
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command arg1, String alias, String[] args) {
		Player p = (Player) sender;
		plugin.reloadConfig();
		List<String> list = new ArrayList<String>();
		String imput = args[0].toLowerCase();
		String imput2 = "";
		if(args.length > 1)
			imput2 = args[1].toLowerCase();
		
		
		if(args.length == 1) {
			ListAdd(list, "<Name>", imput);
			if(p.hasPermission("Kopf.Time.Bypass")) 
			ListAdd(list, "bypass", imput);
			if(p.hasPermission("Kopf.Admin")) 
			ListAdd(list, "reload", imput);
			
		}
			
		if(list.isEmpty())
			list.add("");
		return list;
		
	}
	
	public List<String> ListAdd(List<String> list, String s, String imput) {
		if(s.startsWith(imput)) {
			list.add(s);
		}
		
		return list;
	}
}