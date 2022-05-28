package me.Casper.Kopf.Gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.Casper.Kopf.Main;
import me.Casper.Kopf.Command.KopfCommand;
import me.Casper.Kopf.Utils.Utils;

public class KopfGui {
	
	public static Inventory inv;
	public static String inventroy_name;
	public static String modplayer;
	public static int inv_rows = 5 * 9;
	
	private static Main plugin;
	
	@SuppressWarnings("static-access")
	public KopfGui(Main plugin) {
			
		this.plugin = plugin;
							
	}
	
	public static void initialize() {
		
		inventroy_name = Utils.chat("&a&lMa&f&lgi&a&lc &a&lK&f&lop&a&lf");
		
		inv = Bukkit.createInventory(null, inv_rows);
		
		
		
	}
	

	@SuppressWarnings({ "static-access"})
	public static Inventory GUI (Player p) {
		//ItemStack a = new ItemStack(Material.)       //TESTING MATERIAL TYPE
		Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventroy_name);
		
		int days = KopfCommand.getWait(p);
		
		String daysS = "&6Sie haben keine Wartezeit!";
		if(days != 0)
			daysS = "&3Sie müssen " + days +" Tage warten";
		
		ItemStack head = Utils.createItemPlayerHead(p, daysS);
		
		toReturn.setItem(22, head);
		
		//toReturn.setContents(inv.getContents());
		return toReturn;
	}
	
	
	
	public static void clicked(Player p, int slot, ItemStack clicked, Inventory inv) {
}
}