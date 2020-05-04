package com.frrfdev.crates;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.DyeColor;
import org.bukkit.Sound;

public class Rarity {
    private static List<Rarity> rarities;
    
    
	private String name;
	private String colorName;
	private double chance;
	private DyeColor color;
	private List<Loot> loots;
	private double itemChance;
	private Sound sound;
	private List<LootRarity> lootRarities;
	
	public Rarity() {
		this.lootRarities = new ArrayList<LootRarity>();
	}
	
	public Rarity(String name, double chance, DyeColor color, List<Loot> loots, double itemChance, Sound sound, String colorName) {
		this.name = name;
		this.chance = chance;
		this.color = color;	
		this.loots = loots;		
		this.itemChance = itemChance;
		this.sound = sound;
		this.colorName = colorName;
		this.lootRarities = new ArrayList<LootRarity>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getChance() {
		return chance;
	}
	public void setChance(double chance) {
		this.chance = chance;
	}
	public DyeColor getColor() {
		return color;
	}
	public void setColor(DyeColor color) {
		this.color = color;
	}
	public void setLootTable(List<Loot> loots) {
		this.loots = loots;		
	}
	public List<Loot> getLootTable() {
		return this.loots;
	}
	public void setItemChance(double chance) {
		this.itemChance = chance;
	}
	public double getItemChance() {
		return this.itemChance;
	}
    public Sound getSound() {
		return sound;
	}
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public static List<Rarity> createRarities() {
    	return Utils.getRaritiesFromConfig();
    }
    
    public static List<Rarity> getRarities() {
    	return rarities;
    }
    
    public static void setRarities(List<Rarity> r) {
    	rarities = r;
    }
    public void addToLootRarities(LootRarity rarity) {
    	if(!this.lootRarities.contains(rarity)) this.lootRarities.add(rarity);
    }
    public List<LootRarity> getLootRarities() {
    	return this.lootRarities;
    }    
    
    public static Rarity getRarityByName(String name) {
    	for(Rarity r: rarities) {
    		if(r.getName().equals(name)) return r;
    	}
    	
    	System.out.println("Exception on getting rarity by name: No rarity " + name + " found!");
    	throw new RuntimeException();
    }
}
