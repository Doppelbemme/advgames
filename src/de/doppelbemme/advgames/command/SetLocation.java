package de.doppelbemme.advgames.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.advgames.main.ADVGames;
import net.md_5.bungee.api.ChatColor;

public class SetLocation implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
	
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("ConsoleNotAllowed")));
		}else{
			Player player = (Player) sender;
			
			if(!player.hasPermission("advgames.setup")){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("NoPermission")));
				return false;
			}
			
			if(args.length == 1){
				String LocationName = args[0];
				
				if(LocationName.equalsIgnoreCase("Lobby") || LocationName.equalsIgnoreCase("Spectator")){
					ADVGames.main.locationManager.setLocation(player.getLocation(), LocationName.toLowerCase());
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Die Location §b" + LocationName.toLowerCase() + " §7wurde §agespeichert§7.");
				}else{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§cNutze: §7/§csetlocation §7<§cLobby§7/§cSpectator§7/§cSpawn 1-" + ADVGames.main.MAX_PLAYER + "§7>");
				}
			}else if(args.length == 2){
				String LocationName = args[0];
				int Count;
				
				try {
				    Count = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
				    Count = ADVGames.main.MAX_PLAYER + 1;
				}
				
				if(LocationName.equalsIgnoreCase("Spawn") && Count > 0 && Count <= ADVGames.main.MAX_PLAYER){
					ADVGames.main.locationManager.setLocation(player.getLocation(), LocationName.toLowerCase()+Count);
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Die Location §b" + LocationName.toLowerCase() + " " + Count + " §7wurde §agespeichert§7.");
				}else{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§cNutze: §7/§csetlocation §7<§cLobby§7/§cSpectator§7/§cSpawn 1-" + ADVGames.main.MAX_PLAYER + "§7>");
				}
			}else{
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§cNutze: §7/§csetlocation §7<§cLobby§7/§cSpectator§7/§cSpawn 1-" + ADVGames.main.MAX_PLAYER + "§7>");
			}			
		}
		return false;
	}
	
}