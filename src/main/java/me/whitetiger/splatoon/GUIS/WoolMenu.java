package me.whitetiger.splatoon.GUIS;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WoolMenu {

    private Menu menu;

    public WoolMenu() {
        Menu woolMenu = ChestMenu.builder(2).title("Choose Wool").build();

        List<Material> wools;

        wools = Arrays.stream(Material.values()).filter(material -> material.name().toLowerCase().contains("wool")).collect(Collectors.toList());

        for (int x = 0; x <=15; x++) {
            Slot woolSlot = menu.getSlot(x);
            woolSlot.setItem(new ItemStack(wools.get(x)));
            int finalX = x;
            woolSlot.setClickHandler(((player, clickInformation) -> {
                new AddInklingGui(wools.get(finalX)).openMenu(player);
            }));
        }
    }

    public void openMenu(Player player) {
        menu.open(player);
    }
}
