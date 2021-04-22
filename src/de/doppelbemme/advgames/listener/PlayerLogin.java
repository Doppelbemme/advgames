package de.doppelbemme.advgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.main.GameState;
import net.md_5.bungee.api.ChatColor;

public class PlayerLogin implements Listener{

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		
		int OnlinePlayerAmount = Bukkit.getServer().getOnlinePlayers().size();
		
		if(ADVGames.main.gameState == GameState.LOBBY && OnlinePlayerAmount == ADVGames.main.MAX_PLAYER){
			event.disallow(Result.KICK_FULL, ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("ServerFull")));
		}else if(ADVGames.main.gameState == GameState.RESTART){
			event.disallow(Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("ServerIsRestarting")));
		}
		
	}
	
}
