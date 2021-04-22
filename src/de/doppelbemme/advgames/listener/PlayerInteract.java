package de.doppelbemme.advgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.main.GameState;
import net.md_5.bungee.api.ChatColor;

public class PlayerInteract implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemInteract(PlayerInteractEvent event){
		
		try{
			
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
			
			if(event.getClickedBlock().getType().toString().toLowerCase().equalsIgnoreCase("diamond_block")){
				event.getClickedBlock().getLocation().getBlock().setTypeId(0);
				ADVGames.main.itemManager.getRandomItem(event.getClickedBlock().getLocation());
				
			}if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("SpectatorItem")))){
				
				ADVGames.main.itemManager.openPlayerTeleporter(event.getPlayer());
				
			}else if(event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("BackToLobbyItem")))){
				
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§cDas hier ist nur Dekoration...");
				
			}
		}
		
		}catch(NullPointerException Exception){
			
		}
		
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event){
		if(ADVGames.main.gameState != GameState.INGAME){
			event.setCancelled(true);
		}else{
			if(!ADVGames.main.PlayerAlive.contains(event.getPlayer())){
				event.setCancelled(true);
			}
		}
	}
	
	
	@EventHandler
	public void onItemDrop(PlayerPickupItemEvent event){
		if(ADVGames.main.gameState != GameState.INGAME){
			event.setCancelled(true);
		}else{
			if(!ADVGames.main.PlayerAlive.contains(event.getPlayer())){
				event.setCancelled(true);
			}
		}
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		
		if(event.getWhoClicked().hasPermission("advgames.setup")){
			event.setCancelled(false);
			return;
		}
		
		if(ADVGames.main.gameState != GameState.INGAME){
			event.setCancelled(true);
			return;
		}
		
		try{

			if(event.getInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("SpectatorInventory")))){
					
				String PlayerName = event.getCurrentItem().getItemMeta().getDisplayName().replaceFirst("§6", "");
				Player ClickedPlayer = Bukkit.getPlayer(PlayerName);
				event.getWhoClicked().closeInventory();
				event.getWhoClicked().teleport(ClickedPlayer);
				
			}
			
			event.setCancelled(true);
			
		}catch (NullPointerException Exception) {

		}
		
	}
	
}
