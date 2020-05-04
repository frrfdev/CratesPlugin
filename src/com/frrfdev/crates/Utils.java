package com.frrfdev.crates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public abstract class Utils {
	private static FileConfiguration config = Main.getInstance().getConfig();
	
	public static List<Rarity> getRaritiesFromConfig() {
    	List<Rarity> rarityList = new ArrayList<Rarity>();    	
		
		ConfigurationSection raritiesSection = config.getConfigurationSection("crates");		
    	Set<String> rarityKeys = raritiesSection.getKeys(false);
    	
    	if(rarityKeys.size() > 0) {
	    	for(String rarityKey : rarityKeys){
	    		String rarityPath = "crates." + rarityKey; 
	    		
	    		Rarity rarity = new Rarity();
	    		
	    	    String name = config.getString(rarityPath + ".name"); 
	    	    rarity.setName(name);
	    	    
	    	    String colorName = config.getString(rarityPath + ".color");
	    	    DyeColor color = DyeColor.valueOf(colorName);
	    	    rarity.setColorName(colorName);
	    	    rarity.setColor(color);
	    	    
	    	    double chance = config.getDouble(rarityPath + ".rarity");
	    	    rarity.setChance(chance);
	    	    
	    	    double itemChance = config.getDouble(rarityPath + ".itemChance");
	    	    rarity.setItemChance(itemChance);
	    	    
	    	    String soundName = config.getString(rarityPath + ".sound");	    	    
	    	    Sound sound = soundName == null || soundName.isEmpty()  ? Sound.ENTITY_EXPERIENCE_ORB_PICKUP : Sound.valueOf(soundName.toUpperCase());
	    	    rarity.setSound(sound);
	    	    
	    	    rarity.setLootTable(getRarityLootTableFromConfig(rarity, rarityPath, rarityKey));
	    	    
	    	    rarityList.add(rarity);
	    	}
	    	
	    	fixRarityChance(rarityList);
	    	
	    	return rarityList;
    	} else {
    		System.out.println("Exception while reading rarities: No rarities found.");
    		System.out.println("Check if any rarities have been inserted in the config.yml");
    		throw new RuntimeException();
    	}
    }
	
	public static List<Loot> getRarityLootTableFromConfig(Rarity rarity, String rarityPath, String rarityKey) {
		List<Loot> loots = new ArrayList<Loot>();
		
		ConfigurationSection lootsSection = config.getConfigurationSection(rarityPath + ".loots");
	    
	    Set<String> lootKeys = lootsSection.getKeys(false);
	    
	    if(lootKeys.size() > 0) {
	    	for(String lootKey : lootKeys){
	    		loots.add(getItemFromConfig(rarityKey, lootKey, rarity));
	    	}
	    } else {
	    	System.out.println("Exception while reading the rarity loottable: No items found");
	    	System.out.println("Check if any item have been inserted in the config.yml in the section '" + rarity.getName() + "'");
	    	throw new RuntimeException();
	    }
	    
	    return loots;
	}
	
	public static Loot getItemFromConfig(String rarityKey, String lootKey, Rarity rarity) {
		String lootPath = "crates." + rarityKey + ".loots." + lootKey;
   		try {
   	   		
   			LootRarity lootRarity = LootRarity.getLootRarityByName(config.getString(lootPath + ".rarity"));
   			rarity.addToLootRarities(lootRarity);
   			
	   		Material item = Material.valueOf(config.getString(lootPath + ".name").toUpperCase());
	   		int max = config.getInt(lootPath + ".max");
	    	int min = config.getInt(lootPath + ".min");
	    	boolean isEnchanted = config.getBoolean(lootPath + ".enchanted");
	    	
	    	if(isEnchanted) {	    		
	    		return new Loot(item, lootRarity, max, min, isEnchanted, getItemEnchantmentInfoFromConfig(lootPath));
	    	} else {	    		
		    	return new Loot(item, lootRarity, max, min);	    		
	    	}
   		} catch (Exception e) {
   			System.out.println("Exception getting item from config.yml");
   			System.out.println(Main.getInstance().getConfig().getString(lootPath + ".name") + "is not a valid minecraft item");
   			throw new RuntimeException(e.getMessage());
   		}
	}
	
	public static LootEnchantments getItemEnchantmentInfoFromConfig(String lootPath) {
		String enchantmentPath = lootPath + ".enchantments";
		
		int maxQuantity = config.getInt(enchantmentPath + ".maxQuantity");
		int minQuantity = config.getInt(enchantmentPath + ".minQuantity");
		List<Enchantment> enchantments = getEnchantmentListFromStrings(config.getStringList(enchantmentPath + ".list"));
		
		return new LootEnchantments(enchantments, maxQuantity, minQuantity);
	}
	
	public static List<Enchantment> getEnchantmentListFromStrings(List<String> list) {
		List<Enchantment> enchantments = new ArrayList<Enchantment>();
		for(String enchantmentName: list) {
			Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName.toLowerCase()));
			if(enchantment == null) {
				System.out.println("Exception on getting enchants: enchantment " + enchantmentName + " not found");
			}
			enchantments.add(enchantment);
		}
		
		return enchantments;
	}
	
	public static void fixRarityChance(List<Rarity> rarityList) {
		double restOfChance = 100;
    	
    	for(Rarity r: rarityList) {
    		restOfChance -= r.getChance();
    	}
    	
    	double aditionalChance = restOfChance / rarityList.size();
    	
    	if(restOfChance != 0) {
    		for(Rarity r: rarityList) {
        		r.setChance(r.getChance() + aditionalChance);
        	}
    	} 
	}
	
	public static void fixLootRarityChance(List<LootRarity> rarityList) {
		double restOfChance = 100;
    	
    	for(LootRarity r: rarityList) {
    		restOfChance -= r.getChance();
    	}
    	
    	double aditionalChance = restOfChance / rarityList.size();
    	
    	if(restOfChance > 0) {
    		for(LootRarity r: rarityList) {
        		r.setChance(r.getChance() + aditionalChance);
        	}
    	}
	}

	public static double getRandomDouble() {
		return getRandomDouble(0);
	}
	
	public static double getRandomDouble(double minimum) {
		Random rnd = new Random();
		
		double value = rnd.nextDouble() * 100;
		
		if(value >= 100) return 100;
		else if(value < minimum) return minimum;
		
		return value;
	}		
	
	public static int getRandomInt(int limit) {
		return getRandomInt(limit, 0);
	}
	
	public static int getRandomInt(int limit, int minimum) {
		Random rnd = new Random();
		
		int value = rnd.nextInt(limit);
		
		if(value < minimum) return minimum;
		
		return value;
	}

	public static List<LootRarity> getLootRaritiesFromConfig() {
		List<LootRarity> lootRarities = new ArrayList<LootRarity>();
		
		ConfigurationSection lootRaritiesSection = config.getConfigurationSection("lootRarities");
		Set<String> lootRaritiesKeys = lootRaritiesSection.getKeys(false);
		
		if(lootRaritiesKeys.size() > 0) {
			for(String lootRarityKey: lootRaritiesKeys) {
				String lootRarityPath = "lootRarities." + lootRarityKey;
				
				LootRarity lootRarity = new LootRarity();
				
				String name = config.getString(lootRarityPath + ".name"); 
				lootRarity.setName(name);
	    	    
	    	    double chance = config.getDouble(lootRarityPath + ".chance");
	    	    lootRarity.setChance(chance);
	    	    
	    	    lootRarities.add(lootRarity);
			}
			
			Collections.sort(lootRarities, LootRarity.compareByChance);
			
			fixLootRarityChance(lootRarities);
			
			return lootRarities;
		} else {
			System.out.println("Exception while reading loot rarities: No loot rarities found.");
    		System.out.println("Check if any loot rarities have been inserted in the config.yml");
    		throw new RuntimeException();
		}
	}
	
	public static void destroyCrate(Block block) {
		
		Container container = (Container) block.getState();
		for(ItemStack item : container.getInventory().getStorageContents()) {
			if(item != null) block.getWorld().dropItemNaturally(block.getLocation(), item);
        }
		
		block.setType(Material.AIR);
	}
	
}
