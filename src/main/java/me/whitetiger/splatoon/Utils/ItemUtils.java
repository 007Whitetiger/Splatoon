package me.whitetiger.splatoon.Utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {

    public static ItemStack fastName(Material material, String name) {
        ItemStack tempItem = new ItemStack(material);

        ItemMeta itemMeta = tempItem.getItemMeta();

        itemMeta.setDisplayName(name);

        tempItem.setItemMeta(itemMeta);
        return tempItem;
    }

    public static ItemStack fastLoreAndName(Material material, String name, List<String> lore) {
        ItemStack tempItem = new ItemStack(material);

        ItemMeta itemMeta = tempItem.getItemMeta();

        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);

        tempItem.setItemMeta(itemMeta);
        return tempItem;
    }

    public static ItemStack fastLore(Material material, List<String> lore) {
        ItemStack tempItem = new ItemStack(material);

        ItemMeta itemMeta = tempItem.getItemMeta();
        itemMeta.setLore(lore);

        tempItem.setItemMeta(itemMeta);
        return tempItem;
    }
}
