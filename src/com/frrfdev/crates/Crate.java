package com.frrfdev.crates;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.Container;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class Crate {   	
	private final int SLOTS = 27;
	
    private static double spawnChance;
    private Location location;
    private Material material;    
    private Rarity rarity;
    
	public Crate(Location l) {
        this.location = l;
        this.material = Material.CHEST;
        
        Rarity.getRarities().sort(Comparator.comparing(Rarity::getChance));
    }
	
	public static void setSpawnChance() {
		spawnChance = Main.getInstance().getConfig().getDouble("spawnChance");
	}
    
    public Material getMaterial() {
        return this.material;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public Rarity getRarity() {
    	return this.rarity;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
    public static boolean willSpawn() {
    	Random rand = new Random();
    	if(rand.nextDouble() * 100 <= Crate.spawnChance) return true;
    	
    	return false;
    }
    
    public static void spawnCrate(World world, Location location, String crateType, Rarity rarity) {
    	Crate crate = new Crate(location);
    	crate.rarity = rarity;
        new BukkitRunnable() {
            @Override
            public void run() {
            	Container container;
            	
            	if(crateType.toLowerCase().equals("shulker")) {
	            	crate.getLocation().getBlock().setType(Material.valueOf((crate.getRarity().getColor().name() + "_SHULKER_BOX").toUpperCase()));
	        		container = (ShulkerBox) crate.getLocation().getBlock().getState();            		
            	} else {
            		crate.getLocation().getBlock().setType(Material.CHEST);
            		container = (Chest) crate.getLocation().getBlock().getState();
            	}	                	

        		crate.getLocation().getBlock().setMetadata("crate", new FixedMetadataValue(Main.getInstance(), "crate"));
            	
            	crate.generateItems(container.getInventory());
            	world.playSound(crate.getLocation(), crate.getRarity().getSound(), 1.0F, 8.0F);
            	world.spawnParticle(Particle.REDSTONE, crate.getLocation(), 100, 0, 1, 0, 1, new Particle.DustOptions(crate.getRarity().getColor().getColor(), 100));
            }
        }.runTaskLater(Main.getInstance(), 1L);
    }
    
    public static void spawnCrate(World world, Location location, String crateType) {
    	spawnCrate(world, location, crateType, Crate.getRandomRarity());    	
    }
    
    public void setRarity(Rarity rarity) {
    	this.rarity = rarity;
    }
    
    public static Rarity getRandomRarity() {
    	Random rand = new Random();
    	double rarity = rand.nextDouble() * 100;
    	
    	double chance = 0;
    	for(Rarity r: Rarity.getRarities()) {
    		chance += r.getChance();
    		
    		if(rarity <= chance) {
    			return r;
    		}
    	} 	
    	
    	System.out.println("Exception on getting random rarity: No rarities found");
    	throw new RuntimeException();
    }
    
    public void generateItems(Inventory inventory) {  
    	List<LootRarity> lootRarities = this.getRarity().getLootRarities();
    	List<Loot> lootTable = this.getRarity().getLootTable();
    	
    	for(int i = 0; i < SLOTS; i++) {
    		if(Utils.getRandomInt(100) + 1 <= this.getRarity().getItemChance()) {
    			int chance = 0;
	    		double selectedChance = Utils.getRandomDouble();
    	    	for(LootRarity rarity : lootRarities) {
    	    		chance += rarity.getChance();
    	    		if(selectedChance <= chance) {
    	    			List<Loot> suitableLoots = lootTable.parallelStream().filter(l -> l.getRarity().getName() == rarity.getName()).collect(Collectors.toList());
    	    			
    	    			Loot loot = suitableLoots.get(Utils.getRandomInt(suitableLoots.size()));
    	    			int quantity = Utils.getRandomInt(loot.getMax(), loot.getMin());
    	    			
    	    			ItemStack item = new ItemStack(loot.getItem(), quantity);    			
    	    			if(loot.isEnchanted()) {
    	    				loot.enchantItem(item, loot.getEnchantments());
    	    			}
    	    			
    	    			inventory.setItem(i, item);
    	    			break;
    	    		} 
    	    	}
    	    }
    	}
    }
}

