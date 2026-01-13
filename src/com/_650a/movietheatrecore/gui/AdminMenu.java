package com._650a.movietheatrecore.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com._650a.movietheatrecore.Main;

public class AdminMenu {

    private final Main plugin;

    public AdminMenu(Main plugin) {
        this.plugin = plugin;
    }

    public void open(Player player) {
        player.openInventory(build());
    }

    public Inventory build() {
        AdminMenuHolder holder = new AdminMenuHolder();
        Inventory inventory = Bukkit.createInventory(holder, 27, ChatColor.DARK_PURPLE + "MovieTheatreCore Admin");
        holder.setInventory(inventory);

        inventory.setItem(10, menuItem(Material.BOOK, ChatColor.AQUA + "Media", "Browse and manage media entries."));
        inventory.setItem(12, menuItem(Material.ITEM_FRAME, ChatColor.AQUA + "Screens", "Manage screens and layouts."));
        inventory.setItem(14, menuItem(Material.REDSTONE_TORCH, ChatColor.AQUA + "Playback", "Control what is playing."));
        inventory.setItem(16, menuItem(Material.COMPARATOR, ChatColor.AQUA + "Settings", "Reload and view status."));

        return inventory;
    }

    private ItemStack menuItem(Material material, String name, String loreText) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(List.of(ChatColor.GRAY + loreText));
        item.setItemMeta(meta);
        return item;
    }
}
