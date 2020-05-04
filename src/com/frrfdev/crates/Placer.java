package com.frrfdev.crates;

import org.bukkit.World;
import org.bukkit.block.Block;

public class Placer {
    private Block block;
    private World world;
	private static String crateType;
    
    public Placer(Block block, World world) {
    	this.block = block;
    	this.world = world; 
    }
    
    public void placeBlock() {
    	Crate.spawnCrate(world, this.block.getLocation(), crateType);
    } 
    
    public static void setCrateType() {
    	String type =  Main.getInstance().getConfig().getString("crateType").toLowerCase();
    	
    	if(!type.equals("chest") && !type.equals("shulker")) type = "chest";
    	
    	crateType = type;
    }
    
    public static void toogleCrateType() {
    	if(crateType == "chest") {
    		crateType = "shulker";
    	} else {
    		crateType = "chest";
    	}
    }
    
    public static String getCrateType() {
    	return crateType;
    }
} 

