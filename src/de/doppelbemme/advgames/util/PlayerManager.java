package de.doppelbemme.advgames.util;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.main.GameState;
import net.md_5.bungee.api.ChatColor;

public class PlayerManager {

	public void setPlayerLevel(Player player){
		
		player.setLevel(ADVGames.main.LobbyTime);
		
		if(ADVGames.main.LobbyTime == 60){
			player.setExp(0.99f);
			return;
		}
		
		float CurrentEXPAmount = player.getExp();
		float LobbyTime = ADVGames.main.LobbyTime;
		float EXPAmountToRemove = (CurrentEXPAmount/LobbyTime);
		float newEXPAmount = CurrentEXPAmount - EXPAmountToRemove;
		player.setExp(newEXPAmount);
		
	}
	
	public void teleportPlayer(Player player, String LocationName){
		
		if(LocationName.equalsIgnoreCase("Lobby") || LocationName.equalsIgnoreCase("Spectator")){
		
			player.teleport(ADVGames.main.locationManager.getLocation(LocationName));
		
		}else{
			
			Collections.shuffle(ADVGames.main.PlayerAlive);
			
			for(int i = 0; i < ADVGames.main.PlayerAlive.size(); i++){
				Player TeleportPlayer = ADVGames.main.PlayerAlive.get(i);
				TeleportPlayer.teleport(ADVGames.main.locationManager.getLocation("Spawn".toLowerCase()+(i+1)));
			}
			
		}
		
	}
	
	public void setPlayerInSpectator(Player player){
		
		player.spigot().respawn();
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);
		player.setFlying(true);
		ADVGames.main.playerManager.teleportPlayer(player, "Spectator".toLowerCase());
		ADVGames.main.itemManager.giveSpectatorItems(player);
		player.getInventory().setHeldItemSlot(4);
		
		if(ADVGames.main.PlayerAlive.contains(player)){
			ADVGames.main.PlayerAlive.remove(player);
		}
		
		for(Player OnlinePlayer : Bukkit.getOnlinePlayers()){
			if(!ADVGames.main.PlayerAlive.contains(OnlinePlayer)){
				OnlinePlayer.showPlayer(player);
				player.showPlayer(OnlinePlayer);
			}
		}
		
		for(Player AlivePlayer : ADVGames.main.PlayerAlive){
			AlivePlayer.hidePlayer(player);
		}
		
	}
	
	public void checkForGameEnding(int PlayerAliveCount){
		
		if(PlayerAliveCount > 1){
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("PlayerRemaining").replaceAll("%remaining_players%", String.valueOf(PlayerAliveCount))));
			return;
		}
		
		Player Winner = ADVGames.main.PlayerAlive.get(0);
		ADVGames.main.PlayerAlive.clear();
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("GameWonMessage").replaceAll("%winner%", Winner.getName())));
		ADVGames.main.gameState = GameState.RESTART;
		
		for(Player OnlinePlayer : Bukkit.getOnlinePlayers()){
			ADVGames.main.playerManager.teleportPlayer(OnlinePlayer, "Lobby".toLowerCase());
			ADVGames.main.itemManager.giveRestartItems(OnlinePlayer);
		}
		
		Bukkit.getScheduler().cancelTask(ADVGames.main.countdown.IngameScheduler);
		ADVGames.main.countdown.startRestartCountdown();
		
	}
	
}
