package com.frrfdev.crates.Commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.frrfdev.crates.Crate;
import com.frrfdev.crates.Rarity;

public class SummonCrate implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			World w = player.getWorld();
			Location loc = player.getLocation();
			
			Rarity rarity = Rarity.getRarityByName(args[0]);
			
			String crateType;
			
			if(args.length < 1) crateType = "chest";
			else crateType = args[1];
			
			Crate.spawnCrate(w, loc, crateType, rarity);
		}
		return false;
	}	
	
}