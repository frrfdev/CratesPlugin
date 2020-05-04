package com.frrfdev.crates.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.frrfdev.crates.Main;
import com.frrfdev.crates.Placer;

public class ToogleCrateType implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Placer.toogleCrateType();
			Main.getInstance().getConfig().set("crateType", Placer.getCrateType());
		}
		return false;
	}	
	
}
