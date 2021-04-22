package de.doppelbemme.advgames.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.region.Cuboid;
import net.md_5.bungee.api.ChatColor;

public class SetRegion implements CommandExecutor{

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
			
			if(args.length != 1){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§cNutze: §7/setregion <§c1§7/§c2§7/§cfinish§7>");
			}else{
				if(args[0].equalsIgnoreCase("finish")){
					if(ADVGames.main.regionManager.Position1.containsKey(player) && ADVGames.main.regionManager.Position2.containsKey(player)){
						ADVGames.main.regionManager.saveRegion(ADVGames.main.regionManager.Position1.get(player), ADVGames.main.regionManager.Position2.get(player));
						ADVGames.main.regionManager.regions.add(new Cuboid(ADVGames.main.regionManager.Position1.get(player), ADVGames.main.regionManager.Position2.get(player)));
						ADVGames.main.regionManager.Position1.remove(player);
						ADVGames.main.regionManager.Position2.remove(player);
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Die §bRegion §7wurde erfolgreich §agespeichert§7.");
					}else{
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Es wurden noch §cnicht alle Positionen §7gesetzt.");
					}
				}else if(args[0].equalsIgnoreCase("1")){
					ADVGames.main.regionManager.Position1.put(player, player.getLocation());
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Die §bPosition 1 §7wurde erfolgreich §agespeichert§7.");
				}else if(args[0].equalsIgnoreCase("2")){
					ADVGames.main.regionManager.Position2.put(player, player.getLocation());
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Die §bPosition 2 §7wurde erfolgreich §agespeichert§7.");
				}else{
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§cNutze: §7/setregion <§c1§7/§c2§7/§cfinish§7>");
				}
			}
		
		}
		return false;
	}
}
