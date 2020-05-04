package com.frrfdev.crates;

import java.util.Comparator;
import java.util.List;

public class LootRarity implements Comparable<LootRarity> {	
	private static List<LootRarity> lootRarities;
		
	private String name;
	private double chance;	
	
	public LootRarity() {}
	
	public LootRarity(String name, double chance) {
		super();
		this.name = name;
		this.chance = chance;
	}
	
	public static List<LootRarity> getLootRarities() {
		return lootRarities;
	}
	public static void setLootRarities(List<LootRarity> r) {
		lootRarities = r;
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
	
	public static LootRarity getLootRarityByName(String name) {
		for(LootRarity lootRarity : lootRarities) {
			if(lootRarity.name.toLowerCase().equals(name.toLowerCase())) {
				return lootRarity;
			}
		}
		
		System.out.println("Exception getting loot rarity by name: ");
		System.out.println(name + " rarity not found");
		throw new RuntimeException();
	} 
	
	public static List<LootRarity> generateLootRarities() {
		return Utils.getLootRaritiesFromConfig();
	}
	
	public static Comparator<LootRarity> compareByChance = (LootRarity lr1, LootRarity lr2) -> Double.compare(lr1.getChance(), lr2.getChance());

	@Override
	public int compareTo(LootRarity arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
