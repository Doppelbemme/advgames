package de.doppelbemme.advgames.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBuildBreak implements Listener{

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		
		Player player = event.getPlayer();
		
		if(!player.hasPermission("advgames.build")){
			event.setCancelled(true);
			return;
		}
		
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		
		if(!player.hasPermission("advgames.build")){
			event.setCancelled(true);
			return;
		}
		
	}
	
}
