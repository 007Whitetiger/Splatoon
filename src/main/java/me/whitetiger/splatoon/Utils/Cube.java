package me.whitetiger.splatoon.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Cube {

    private final Location firstLocation;
    private final Location secondLocation;
    private final int x1, y1, z1;
    private final int x2, y2, z2;
    private final World world;
    
    public Cube(Location firstLocation, Location secondLocation) {
        if (!firstLocation.getWorld().equals(secondLocation.getWorld())) throw new ExceptionInInitializerError("Please add two locations in the same world!");
        this.world = firstLocation.getWorld();
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.x1 = Math.min(firstLocation.getBlockX(), secondLocation.getBlockX());
        this.y1 = Math.min(firstLocation.getBlockY(), secondLocation.getBlockY());
        this.z1 = Math.min(firstLocation.getBlockZ(), secondLocation.getBlockZ());
        this.x2 = Math.max(firstLocation.getBlockX(), secondLocation.getBlockX());
        this.y2 = Math.max(firstLocation.getBlockY(), secondLocation.getBlockY());
        this.z2 = Math.max(firstLocation.getBlockZ(), secondLocation.getBlockZ());

    }

    public void loopOverArea(Consumer<Block> blockConsumber) {
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    blockConsumber.accept(world.getBlockAt(x, y, z));
                }
            }
        }
    }

    public void loopOverArea(BiConsumer<Block, Integer> blockConsumber) {
        int i = 0;
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    blockConsumber.accept(world.getBlockAt(x, y, z), i++);
                }
            }
        }
    }

    public List<Material> getBlocMaterials() {
        List<Material> materialsInBlock = new ArrayList<>();
        
        loopOverArea((block) -> {
            materialsInBlock.add(block.getType());
        });

        return materialsInBlock;
    }


    public void setMaterial(Material material) {
        loopOverArea((block) -> {
            block.setType(material);
        });
    }

    public void setMaterial(List<Material> materials) {
        loopOverArea((block, i) -> {
            block.setType(materials.get(i));
        });
    }
}
