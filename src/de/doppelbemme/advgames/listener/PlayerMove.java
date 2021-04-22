package de.doppelbemme.advgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.main.GameState;

public class PlayerMove implements Listener{

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
	
		if(ADVGames.main.gameState == GameState.INGAME && ADVGames.main.InGameTime <= 60*15+5 && ADVGames.main.InGameTime >= 60*15+1) {
			event.setTo(event.getFrom());
		}
	}
	
}
	
