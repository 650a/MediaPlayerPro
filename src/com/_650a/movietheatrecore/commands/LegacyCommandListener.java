package com._650a.movietheatrecore.commands;

import java.util.Locale;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import com._650a.movietheatrecore.configuration.Configuration;

public class LegacyCommandListener implements Listener {

    private final Configuration configuration = new Configuration();

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        if (message == null || message.isBlank()) {
            return;
        }
        String command = message.trim().substring(1).split("\\s+")[0].toLowerCase(Locale.ROOT);
        if (isLegacyCommand(command)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(configuration.legacy_command_notice());
        }
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        String command = event.getCommand();
        if (command == null || command.isBlank()) {
            return;
        }
        String label = command.trim().split("\\s+")[0].toLowerCase(Locale.ROOT);
        if (isLegacyCommand(label)) {
            event.setCancelled(true);
            CommandSender sender = event.getSender();
            sender.sendMessage(configuration.legacy_command_notice());
        }
    }

    private boolean isLegacyCommand(String command) {
        return command.equals("mp") || command.equals("mediaplayer");
    }
}
