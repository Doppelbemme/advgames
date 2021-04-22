package de.doppelbemme.advgames.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;

import de.doppelbemme.advgames.main.ADVGames;
import net.md_5.bungee.api.ChatColor;

public class AddItem implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String arg2, String[] arg3) {
		
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("ConsoleNotAllowed")));
		}else{
			Player player = (Player) sender;
			
			if(!player.hasPermission("advgames.setup")){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("NoPermission")));
				return false;
			}
			
			if(player.getItemInHand().getType().toString().equalsIgnoreCase(Material.AIR.toString())){
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Bitte halte ein §bItem §7in der Hand.");
				ADVGames.main.itemManager.getRandomItem(player.getLocation());
				return false;
			}
			
			
			Material ItemInHand = player.getInventory().getItemInHand().getType();
			String ItemName = ItemInHand.toString().toLowerCase();
			int ID = player.getItemInHand().getTypeId();
			short SubID = player.getItemInHand().getDurability();
			int Amount = player.getItemInHand().getAmount();
			boolean Splash = false;
			
			if(ItemName.toLowerCase().equalsIgnoreCase("potion")){
				
				ItemStack PotionStack = player.getItemInHand();
				Potion potion = Potion.fromItemStack(PotionStack);
				if(potion.isSplash()){
					Splash = true;
				}
				
			}
			
			ADVGames.main.itemManager.saveItem(ID, SubID, Amount, ItemName, Splash);
			
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Das Item §b" + ItemName.toLowerCase() + " §7wurde §agespeichert§7.");
			
		}
		return false;

	}
}
