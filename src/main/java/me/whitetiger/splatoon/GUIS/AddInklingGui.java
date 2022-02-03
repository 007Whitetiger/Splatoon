package me.whitetiger.splatoon.GUIS;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddInklingGui {
    private final Menu menu;

    public AddInklingGui(Material woolMaterial) {
        menu = ChestMenu.builder(3)
                .title("Create Inkling")
                .build();

        Mask mask = BinaryMask.builder(menu)
                .item(new ItemStack(Material.BLACK_STAINED_GLASS))
                .pattern("111111111")
                .pattern("100000001")
                .pattern("111111111").build();
        mask.apply(menu);

        Slot slot = menu.getSlot(10);

        slot.setItem(new ItemStack(woolMaterial));

        slot.setClickHandler((player, info) -> {
            new WoolMenu().openMenu(player);
        });
    }

    public AddInklingGui() {
        this(Material.BLUE_WOOL);
    }

    public void openMenu(Player player) {
        menu.open(player);
    }
}
