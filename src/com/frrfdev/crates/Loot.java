package com.frrfdev.crates;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class Loot {
	private Material item;
	private int max;
	private int min;
	private boolean enchanted;
	private LootEnchantments enchantments;
	private LootRarity rarity;
	
	public Loot(Material item, LootRarity rarity) {
		this.item = item;
		this.max = 1;
		this.min = 1;
		this.rarity = rarity;
	}
	
	public Loot(Material item, LootRarity rarity, int max, int min, boolean enchanted) {
		this.item = item;
		this.max = max;
		this.min = min;
		this.enchanted = enchanted;
		this.rarity = rarity;
	}
	
	public Loot(Material item, LootRarity rarity, boolean enchanted, LootEnchantments enchantments) {
		this.enchanted = enchanted;
		this.max = 1;
		this.min = 1;
		this.item = item;
		this.enchantments = enchantments;
		this.rarity = rarity;
	}
	
	public Loot(Material item, LootRarity rarity, int max, int min) {
		this.item = item;
		this.max = max;
		this.min = min;
		this.rarity = rarity;
	}
	
	public Loot(Material item, LootRarity rarity, int max, int min, boolean enchanted, LootEnchantments enchantments) {
		this.item = item;
		this.max = max;
		this.min = min;
		this.enchanted = enchanted;
		this.enchantments = enchantments;
		this.rarity = rarity;
	}
	
	public Material getItem() {
		return item;
	}
	public void setItem(Material item) {
		this.item = item;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public boolean isEnchanted() {
		return this.enchanted;
	}	
	
	public LootEnchantments getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(LootEnchantments enchantments) {
		this.enchantments = enchantments;
	}

	public void setEnchanted(boolean enchanted) {
		this.enchanted = enchanted;
	}

	public LootRarity getRarity() {
		return rarity;
	}

	public void setRarity(LootRarity rarity) {
		this.rarity = rarity;
	}

	public void enchantItem(ItemStack item, LootEnchantments enchantments) {
		
		int numberOfEnchants = Utils.getRandomInt(enchantments.getMaxEnchantments() + 1, enchantments.getMinEnchantments());
		Map<Enchantment, Integer> selectedEnchantments = new HashMap<Enchantment, Integer>();
		
		for(int i = 0; i < numberOfEnchants; i++) {
			Enchantment enchantment = enchantments.getEnchantments().get(Utils.getRandomInt(enchantments.getEnchantments().size())); 
			
			int level = Utils.getRandomInt(enchantment.getMaxLevel(), enchantment.getStartLevel());
			
			selectedEnchantments.put(enchantment, level);	
		}
		
		if(item.getType() == Material.ENCHANTED_BOOK) {
			EnchantmentStorageMeta enchant = (EnchantmentStorageMeta) item.getItemMeta();
			for(Map.Entry<Enchantment, Integer> entry : selectedEnchantments.entrySet()) {
				enchant.addStoredEnchant(entry.getKey(), entry.getValue(), false);  
			} 					
			item.setItemMeta(enchant);
		} else {
			item.addUnsafeEnchantments(selectedEnchantments);
		}
	}
	
}
