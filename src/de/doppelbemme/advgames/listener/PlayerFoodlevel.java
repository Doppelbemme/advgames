package de.doppelbemme.advgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.main.GameState;

public class PlayerFoodlevel implements Listener{

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event){
		
		if(ADVGames.main.gameState != GameState.INGAME){
			event.setCancelled(true);
		}
		
	}
	
}
