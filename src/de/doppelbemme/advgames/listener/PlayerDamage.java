package de.doppelbemme.advgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.main.GameState;
import net.md_5.bungee.api.ChatColor;

public class PlayerDamage implements Listener{

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
	
		event.setDeathMessage(null);
		
		if(!(event.getEntity() instanceof Player)){
			return;
		}
		
		Player player = (Player) event.getEntity();
		
		if(!(player.getKiller() instanceof Player)){
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("PlayerDeathMessage")).replaceAll("%player%", player.getName()));			
			ADVGames.main.playerManager.setPlayerInSpectator(player);
			
			ADVGames.main.playerManager.checkForGameEnding(ADVGames.main.PlayerAlive.size());
			return;
		}
		
		Player killer = player.getKiller();
		
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("PlayerKilledByPlayer")).replaceAll("%player%", player.getName()).replaceAll("%killer%", killer.getName()));
		ADVGames.main.playerManager.setPlayerInSpectator(player);
	
		ADVGames.main.playerManager.checkForGameEnding(ADVGames.main.PlayerAlive.size());
	}
	
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event){
		
		if(!(event.getEntity() instanceof Player)){
			return;
		}
		
		if(ADVGames.main.gameState != GameState.INGAME){
			
			event.setCancelled(true);
			return;
			
		}
	}
	
	
	@EventHandler
	public void onPlayerDamageByPlayer(EntityDamageByEntityEvent event){
		
		if(ADVGames.main.gameState != GameState.INGAME){
			
			event.setCancelled(true);
			
		}else{
			
			if(!(event.getEntity() instanceof Player)){
				return;
			}
			
			if(!(event.getDamager() instanceof Player)){
				event.setCancelled(true);
				return;
			}
			
			Player damager = (Player) event.getDamager();
			
			if(!ADVGames.main.PlayerAlive.contains(damager)){
				event.setCancelled(true);
			}else{
				event.setCancelled(false);
			}
		}
		
	}
	
}
