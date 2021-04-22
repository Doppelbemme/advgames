package de.doppelbemme.advgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.main.GameState;
import net.md_5.bungee.api.ChatColor;

public class PlayerQuit implements Listener{

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		
	Player player = event.getPlayer();
	int OnlinePlayerAmount = Bukkit.getOnlinePlayers().size()-1;
		
	event.setQuitMessage(null);
		
		if(ADVGames.main.gameState == GameState.LOBBY){
			
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("QuitMessage").replaceAll("%player%", player.getName())));
			
			if(OnlinePlayerAmount < ADVGames.main.MIN_PLAYER && OnlinePlayerAmount > 0){
				Bukkit.getScheduler().cancelTask(ADVGames.main.countdown.LobbyScheduler);
				ADVGames.main.countdown.MinPlayerBroadcast();
				ADVGames.main.LobbyTime = 60;
			
				for(Player OnlinePlayer : Bukkit.getOnlinePlayers()){
					OnlinePlayer.setExp(0.99f);
					OnlinePlayer.setLevel(60);
				}
				
			}else if(OnlinePlayerAmount == 0){
				Bukkit.getScheduler().cancelTask(ADVGames.main.countdown.MinPlayerScheduler);
			}
			
		}else if(ADVGames.main.gameState == GameState.INGAME){
			
			if(!ADVGames.main.PlayerAlive.contains(player)){
				
				return;
				
			}else{
				
				ADVGames.main.PlayerAlive.remove(player);
				Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("PlayerDeathMessage").replaceAll("%player%", player.getName())));
				
				ADVGames.main.playerManager.checkForGameEnding(ADVGames.main.PlayerAlive.size());
				 
			}
		}else if(ADVGames.main.gameState == GameState.RESTART){
			return;
		}
	}
}
