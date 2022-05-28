package me.Casper.Kopf.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.Casper.Kopf.Main;
import me.Casper.Kopf.Gui.KopfGui;


public class InventoryClickListener implements Listener {
	
	@SuppressWarnings("unused")
	private Main plugin;
	
	public InventoryClickListener(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
	
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
	String title = e.getWhoClicked().getOpenInventory().getTitle();
	/*
	if (title.equals(TestUI.inventroy_name) || title.equals(LobbySelectorUI.inventroy_name)) {
	if (e.getCurrentItem() == null) {
		return;
	}
	if (title.equals(LobbySelectorUI.inventroy_name)) {
		e.setCancelled(true);
		LobbySelectorUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory());
	} else if (e.getWhoClicked().getWorld().equals(plugin.lobbyw)) {
		((Player)e.getWhoClicked()).setItemOnCursor(e.getCurrentItem());
		((Player)e.getWhoClicked()).setItemOnCursor(null);
		e.setCancelled(true);
		return;
	}
	} else */ if (title.equals(KopfGui.inventroy_name)){
		((Player)e.getWhoClicked()).setItemOnCursor(e.getCurrentItem());
		((Player)e.getWhoClicked()).setItemOnCursor(null);
		KopfGui.clicked((Player)e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory());
		e.setCancelled(true);
	}
	{
		return;
	}
}
}
