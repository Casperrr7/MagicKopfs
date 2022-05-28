package me.Casper.Kopf.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.md_5.bungee.api.ChatColor;

public class Utils {
	
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static ItemStack createItem(Inventory inv, String itemId, int amount, int invSlot, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.getMaterial(itemId), amount);
		
		if(!item.getType().equals(Material.ARROW)) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.chat(displayName));
		for (String s : loreString) {
			lore.add(Utils.chat(s));
		}
		meta.setLore(lore);
		item.setItemMeta(meta);
		}
		inv.setItem(invSlot - 1, item);
		return item;
	}
	
	public static ItemStack createItemPlayerHead(OfflinePlayer player, String displayName, String... loreString) {
		
		ItemStack item;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		List<String> lore = new ArrayList();
		
		item = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setDisplayName(Utils.chat(displayName));
		for (String s : loreString) {
			lore.add(Utils.chat(s));
		}
		meta.setLore(lore);
		meta.setOwningPlayer(player);
		item.setItemMeta(meta);
		return item;
	}
		
	}