package com.frrfdev.crates;

import java.util.List;

import org.bukkit.enchantments.Enchantment;

public class LootEnchantments {
	private List<Enchantment> enchantments;
	private int maxEnchantments;
	private int minEnchantments;
	
	public LootEnchantments() {}
	
	public LootEnchantments(List<Enchantment> enchantments, int maxEnchantments, int minEnchantments) {
		this.enchantments = enchantments;
		this.maxEnchantments = maxEnchantments;
		this.minEnchantments = minEnchantments;
	}

	public List<Enchantment> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<Enchantment> enchantments) {
		this.enchantments = enchantments;
	}

	public int getMaxEnchantments() {
		return maxEnchantments;
	}

	public void setMaxEnchantments(int maxEnchantments) {
		this.maxEnchantments = maxEnchantments;
	}

	public int getMinEnchantments() {
		return minEnchantments;
	}

	public void setMinEnchantments(int minEnchantments) {
		this.minEnchantments = minEnchantments;
	}
}
