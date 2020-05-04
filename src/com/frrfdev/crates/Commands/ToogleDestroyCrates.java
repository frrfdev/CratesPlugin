package com.frrfdev.crates.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.frrfdev.crates.BreakBlockListener;
import com.frrfdev.crates.Main;

public class ToogleDestroyCrates implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			BreakBlockListener.toogleDestroyCrates();
			Main.getInstance().getConfig().set("destroyCrates", BreakBlockListener.getDestroyCrates());
		}
		return false;
	}	
	
}
