package com._650a.movietheatrecore.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import com._650a.movietheatrecore.Main;
import com._650a.movietheatrecore.configuration.Configuration;
import com._650a.movietheatrecore.screen.Screen;

public class ScreenDetailMenu {

    private final Main plugin;
    private final Screen screen;
    private final Configuration configuration;

    public ScreenDetailMenu(Main plugin, Screen screen) {
        this.plugin = plugin;
        this.screen = screen;
        this.configuration = new Configuration();
    }

    public void open(Player player) {
        player.openInventory(build());
    }

    public Inventory build() {
        ScreenDetailHolder holder = new ScreenDetailHolder();
        Inventory inventory = Bukkit.createInventory(holder, 27, configuration.gui_accent_color() + "MTC Screen: " + screen.getName());
        holder.setInventory(inventory);

        NamespacedKey screenKey = new NamespacedKey(plugin, "screen-id");
        NamespacedKey actionKey = new NamespacedKey(plugin, "action");

        String primary = configuration.gui_primary_color();
        inventory.setItem(10, actionItem(Material.BOOK, primary + "Assign Media",
                "assign-media", actionKey, screenKey, "Choose media for this screen."));
        inventory.setItem(12, actionItem(Material.REDSTONE_TORCH, primary + "Playback Controls",
                "playback", actionKey, screenKey, "Play, pause, or stop."));
        inventory.setItem(13, actionItem(Material.NOTE_BLOCK, primary + "Audio Radius",
                "set-radius", actionKey, screenKey, "Set audio radius in blocks."));
        inventory.setItem(15, actionItem(Material.BARRIER, ChatColor.RED + "Delete Screen",
                "delete-screen", actionKey, screenKey, "Remove all frames and data."));
        inventory.setItem(16, actionItem(Material.ARROW, ChatColor.GRAY + "Back",
                "back", actionKey, screenKey, "Return to screen list."));

        return inventory;
    }

    private ItemStack actionItem(Material material, String name, String action, NamespacedKey actionKey, NamespacedKey screenKey, String lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(List.of(ChatColor.GRAY + lore));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);
        meta.getPersistentDataContainer().set(actionKey, PersistentDataType.STRING, action);
        if (screen.getUUID() != null) {
            meta.getPersistentDataContainer().set(screenKey, PersistentDataType.STRING, screen.getUUID().toString());
        }
        item.setItemMeta(meta);
        return item;
    }
}
