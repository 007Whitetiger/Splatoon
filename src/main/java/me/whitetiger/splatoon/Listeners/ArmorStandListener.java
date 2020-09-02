package me.whitetiger.splatoon.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ArmorStandListener implements Listener {

    @EventHandler
    public void onArmorStandInteract(PlayerArmorStandManipulateEvent event) {
        if (event.getRightClicked().hasMetadata("SplatoonArmorStand")) {
            event.setCancelled(true);
        }
    }
}
