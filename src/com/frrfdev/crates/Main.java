package com.frrfdev.crates;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.frrfdev.crates.Commands.SummonCrate;
import com.frrfdev.crates.Commands.ToogleCrateType;
import com.frrfdev.crates.Commands.ToogleDestroyCrates;

public class Main extends JavaPlugin {  
	private static Main instance;
    @Override
    public void onEnable() {
        instance = this;          
        this.saveDefaultConfig();
        
        Crate.setSpawnChance();
        BreakBlockListener.setDestroyCrates();
        Placer.setCrateType();
        LootRarity.setLootRarities(LootRarity.generateLootRarities());
        Rarity.setRarities(Rarity.createRarities());
    	
    	// Commands
    	this.getCommand("toggleDestroyCrates").setExecutor(new ToogleDestroyCrates());
    	this.getCommand("toggleCrateType").setExecutor(new ToogleCrateType());
    	this.getCommand("summonCrate").setExecutor(new SummonCrate());    	

        getServer().getPluginManager().registerEvents(new BreakBlockListener(), this);
    }
    
    @Override
    public void onDisable() {
    }
    
    public static Plugin getInstance() {
    	return Main.instance;
    }
    
}

