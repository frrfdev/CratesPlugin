package com.frrfdev.crates;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockListener implements Listener { 
	private static boolean destroyCrates; 
	private static List<Material> effectiveBlocks = Main.getInstance().getConfig().getStringList("effectiveBlocks").stream().map(Material::valueOf).collect(Collectors.toList());
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {  
    	World w = Bukkit.getWorlds().get(0);
    	Block block = event.getBlock();
    	if(block.hasMetadata("crate") && destroyCrates) {
    		Utils.destroyCrate(block);
    	} else {
        	if(effectiveBlocks.contains(block.getType())) {
        		if(Crate.willSpawn()) {
        			new Placer(event.getBlock(), w).placeBlock();
        		}
        	}
    	}  
    }
    
    public static void setDestroyCrates() {
    	destroyCrates = Main.getInstance().getConfig().getBoolean("destroyCrates");
    }
    
    public static boolean getDestroyCrates() {
    	 return destroyCrates;
    }
    
    public static void toogleDestroyCrates() {
    	destroyCrates =  !destroyCrates;
    }
}
