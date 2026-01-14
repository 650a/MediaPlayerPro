package com._650a.movietheatrecore.gui;

import java.util.ArrayList;
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
import com._650a.movietheatrecore.media.MediaEntry;
import com._650a.movietheatrecore.screen.Screen;

public class MediaLibraryMenu {

    private final Main plugin;
    private final Screen screen;
    private final Configuration configuration;

    public MediaLibraryMenu(Main plugin, Screen screen) {
        this.plugin = plugin;
        this.screen = screen;
        this.configuration = new Configuration();
    }

    public void open(Player player) {
        player.openInventory(build());
    }

    public Inventory build() {
        String title = screen == null ? "MTC Media" : "MTC Media: " + screen.getName();
        MediaLibraryHolder holder = new MediaLibraryHolder(screen == null ? null : screen.getUUID());
        Inventory inventory = Bukkit.createInventory(holder, 54, configuration.gui_accent_color() + title);
        holder.setInventory(inventory);

        NamespacedKey actionKey = new NamespacedKey(plugin, "action");
        NamespacedKey mediaKey = new NamespacedKey(plugin, "media-name");

        ItemStack add = new ItemStack(Material.HOPPER);
        ItemMeta addMeta = add.getItemMeta();
        addMeta.setDisplayName(configuration.gui_primary_color() + "Add Media");
        addMeta.setLore(List.of(ChatColor.GRAY + "Click to add a new URL."));
        addMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);
        addMeta.getPersistentDataContainer().set(actionKey, PersistentDataType.STRING, "add-media");
        add.setItemMeta(addMeta);
        inventory.setItem(53, add);

        int slot = 0;
        for (MediaEntry entry : plugin.getMediaLibrary().listEntries()) {
            if (slot >= 53) {
                break;
            }
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(configuration.gui_primary_color() + entry.getName());
            List<String> lore = new ArrayList<>();
            if (screen != null) {
                lore.add(ChatColor.GRAY + "Click to assign");
            } else {
                lore.add(ChatColor.GRAY + "URL: " + entry.getUrl());
            }
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_PLACED_ON,
                    ItemFlag.HIDE_POTION_EFFECTS,
                    ItemFlag.HIDE_UNBREAKABLE);
            meta.getPersistentDataContainer().set(mediaKey, PersistentDataType.STRING, entry.getName());
            item.setItemMeta(meta);
            inventory.setItem(slot++, item);
        }

        return inventory;
    }
}
