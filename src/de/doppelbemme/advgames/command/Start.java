package de.doppelbemme.advgames.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.advgames.main.ADVGames;
import net.md_5.bungee.api.ChatColor;

public class Start implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', "ConsoleNotAllowed"));
		}else{
			Player player = (Player) sender;
			
			if(!player.hasPermission("advgames.start")){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("NoPermission")));
				return false;
			}
			
			if(Bukkit.getOnlinePlayers().size() < ADVGames.main.MIN_PLAYER){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("NotEnoughPlayer")));
				return false;
			}
			
			if(ADVGames.main.LobbyTime <= 10){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("GameAlreadyStarting")));
				return false;
			}
			
			/*
			 * Alle Bedingungen wurden erfüllt,
			 * Command wird ausgeführt.
			 */
			
			ADVGames.main.LobbyTime = 10;
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("CountdownSkipped")));
			
			for(Player OnlinePlayer : Bukkit.getOnlinePlayers()){
				OnlinePlayer.setExp(0.99F);
				OnlinePlayer.setLevel(10);
			}
			
		}
		
		return false;
	}
	
}
