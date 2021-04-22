package de.doppelbemme.advgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.doppelbemme.advgames.main.ADVGames;

public class PlayerChat implements Listener{

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event){
		
		Player player = event.getPlayer();
		
		if(!ADVGames.main.PlayerAlive.contains(player)){
			for(Player OnlinePlayer : Bukkit.getOnlinePlayers()){
				if(!ADVGames.main.PlayerAlive.contains(OnlinePlayer)){
					OnlinePlayer.sendMessage("§c✘ §7" + player.getName() + " §8» §7" + event.getMessage());
				}
			}
		}else{
			Bukkit.broadcastMessage("§7" + player.getName() + " §8» §7" + event.getMessage());
		}
		event.setCancelled(true);
	}
	
}
