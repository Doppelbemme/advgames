package de.doppelbemme.advgames.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Countdown{

	public int MinPlayerScheduler;
	public int LobbyScheduler;
	public int IngameScheduler;
	public int RestartScheduler;
	
	@SuppressWarnings("deprecation")
	public void MinPlayerBroadcast(){
		
		MinPlayerScheduler = Bukkit.getScheduler().scheduleAsyncRepeatingTask(ADVGames.main, new Runnable() {
					
			@Override
			public void run() {
				if(Bukkit.getOnlinePlayers().size() < ADVGames.main.MIN_PLAYER){
					
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("MinPlayerNeeded").replaceAll("%min_player%", String.valueOf(ADVGames.main.MIN_PLAYER))));
					
				}
			}
		}, 0, 20*60L);
	}
	
	public void startLobbyCountdown() {
		
		LobbyScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(ADVGames.main, new Runnable() {
			
			@Override
			public void run() {
				
				if(ADVGames.main.LobbyTime == 60 || ADVGames.main.LobbyTime == 30 || ADVGames.main.LobbyTime == 20 || ADVGames.main.LobbyTime == 10 || ADVGames.main.LobbyTime <= 5 & ADVGames.main.LobbyTime >= 2){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("LobbyCountdown").replaceAll("%time%", String.valueOf(ADVGames.main.LobbyTime))));		
				}else if(ADVGames.main.LobbyTime == 1){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("LobbyCountdownLastSecond")));
				}else if(ADVGames.main.LobbyTime == 0){
					
					for(Player OnlinePlayer : Bukkit.getOnlinePlayers()){
						ADVGames.main.itemManager.clearInventory(OnlinePlayer);
						ADVGames.main.playerManager.teleportPlayer(OnlinePlayer, "spawn");
					}
					
					ADVGames.main.gameState = GameState.INGAME;
					Bukkit.getScheduler().cancelTask(LobbyScheduler);
					startIngameCountdown();
				}
				
				for(Player OnlinePlayer : Bukkit.getOnlinePlayers()){
					ADVGames.main.playerManager.setPlayerLevel(OnlinePlayer);
				}
				
				ADVGames.main.LobbyTime --;
			}
		}, 0, 20L);
		
	}
	
	public void startIngameCountdown(){
		
		IngameScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(ADVGames.main, new Runnable() {
			
			@Override
			public void run() {
				
				if(ADVGames.main.InGameTime <= 60*15+5 && ADVGames.main.InGameTime >= 60*15+2){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("GracePeriod").replaceAll("%seconds%", String.valueOf(ADVGames.main.InGameTime-(60*15)))));
				}else if(ADVGames.main.InGameTime == 60*15+1){					
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("GracePeriodLastSecond")));
				}else if(ADVGames.main.InGameTime == 60*15 || ADVGames.main.InGameTime == 60*10 || ADVGames.main.InGameTime == 60*5 || ADVGames.main.InGameTime == 60*4 || ADVGames.main.InGameTime == 60*3 || ADVGames.main.InGameTime == 60*2 ){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("GameEndingMinutes")).replaceAll("%minutes%", String.valueOf(ADVGames.main.InGameTime/60)));
				}else if(ADVGames.main.InGameTime == 60){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("GameEndingLastMinute")));
				}else if(ADVGames.main.InGameTime == 30 || ADVGames.main.InGameTime == 20 || ADVGames.main.InGameTime == 10 || ADVGames.main.InGameTime <= 5 && ADVGames.main.InGameTime >= 2) {
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("GameEndingSeconds")).replaceAll("%seconds%", String.valueOf(ADVGames.main.InGameTime)));
				}else if(ADVGames.main.InGameTime == 1){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("GameEndingLastSecond")));
				}else if(ADVGames.main.InGameTime == 0){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("TimeUp")));
					
					for(Player OnlinePlayer : Bukkit.getOnlinePlayers()){
						
						ADVGames.main.playerManager.teleportPlayer(OnlinePlayer, "Lobby".toLowerCase());
						ADVGames.main.itemManager.giveRestartItems(OnlinePlayer);
						
					}
					
					ADVGames.main.gameState = GameState.RESTART;
					Bukkit.getScheduler().cancelTask(IngameScheduler);
					startRestartCountdown();
				}
					
					
				ADVGames.main.InGameTime--;
			}
		}, 0, 20L);
		
	}
	
	public void startRestartCountdown(){
		
		RestartScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(ADVGames.main, new Runnable() {
			
			@Override
			public void run() {
				
				if(ADVGames.main.RestartTime == 15 || ADVGames.main.RestartTime == 10 || ADVGames.main.RestartTime <= 5 && ADVGames.main.RestartTime >= 2){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("ServerRestarting").replaceAll("%seconds%", String.valueOf(ADVGames.main.RestartTime))));
				}else if(ADVGames.main.RestartTime == 1){
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("ServerRestartingLastSecond")));
				}else if(ADVGames.main.RestartTime == 0){
					Bukkit.getServer().shutdown();
				}
				ADVGames.main.RestartTime --;
			}
			
		}, 0, 20L);
		
	}
}
	
