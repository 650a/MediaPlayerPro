package com._650a.movietheatrecore.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com._650a.movietheatrecore.Main;
import com._650a.movietheatrecore.configuration.Configuration;

public class AdminMenu {

    private final Main plugin;
    private final Configuration configuration;

    public AdminMenu(Main plugin) {
        this.plugin = plugin;
        this.configuration = new Configuration();
    }

    public void open(Player player) {
        player.openInventory(build());
    }

    public Inventory build() {
        AdminMenuHolder holder = new AdminMenuHolder();
        Inventory inventory = Bukkit.createInventory(holder, 27, configuration.gui_accent_color() + "MTC Admin");
        holder.setInventory(inventory);

        String primary = configuration.gui_primary_color();
        inventory.setItem(10, menuItem(Material.BOOK, primary + "Media", "Browse and manage media entries."));
        inventory.setItem(12, menuItem(Material.ITEM_FRAME, primary + "Screens", "Manage screens and layouts."));
        inventory.setItem(14, menuItem(Material.REDSTONE_TORCH, primary + "Playback", "Control what is playing."));
        inventory.setItem(16, menuItem(Material.COMPARATOR, primary + "Settings", "Reload and view status."));

        return inventory;
    }

    private ItemStack menuItem(Material material, String name, String loreText) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(List.of(ChatColor.GRAY + loreText));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
        return item;
    }
}
