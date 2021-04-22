package de.doppelbemme.advgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.main.GameState;
import net.md_5.bungee.api.ChatColor;

public class PlayerJoin implements Listener{

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		
		Player player = event.getPlayer();
		int OnlinePlayerAmount = Bukkit.getOnlinePlayers().size();
		
		event.setJoinMessage(null);
		
		if(!ADVGames.main.fileManager.locationConfig.contains("lobby.") || !ADVGames.main.fileManager.locationConfig.contains("spectator.")){
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + "§7Der Server ist fertig §cnicht konfiguriert§7. §7Wurden alle §cLocations §7gesetzt ?");
			return;
		}
		
		if(ADVGames.main.gameState == GameState.LOBBY){
			
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("JoinMessage").replaceAll("%player%", player.getName())));
			
			Bukkit.getScheduler().runTaskLater(ADVGames.main, new Runnable() {
				
				@Override
				public void run() {
					
					ADVGames.main.PlayerAlive.add(player);
					ADVGames.main.playerManager.teleportPlayer(player, "Lobby".toLowerCase());
					player.setExp(0.99f);
					ADVGames.main.playerManager.setPlayerLevel(player);
					ADVGames.main.itemManager.giveLobbyItems(player);
					player.setGameMode(GameMode.ADVENTURE);
					player.setHealth(20);
					player.setFoodLevel(20);

				}
				
			}, 2);
		
			if(OnlinePlayerAmount == 1){
	
				ADVGames.main.countdown.MinPlayerBroadcast();
	
			}else if(OnlinePlayerAmount == ADVGames.main.MIN_PLAYER){
	
				Bukkit.getScheduler().cancelTask(ADVGames.main.countdown.LobbyScheduler);
				ADVGames.main.countdown.startLobbyCountdown();
			
			}
		}else if(ADVGames.main.gameState == GameState.INGAME){
			
			Bukkit.getScheduler().runTaskLater(ADVGames.main, new Runnable() {
				
				@Override
				public void run() {
					
					ADVGames.main.playerManager.setPlayerInSpectator(player);
	
				}
				
			}, 2);
			
		}
	}
}
