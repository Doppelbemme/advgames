package de.doppelbemme.advgames.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class WorldEntitySpawning implements Listener{

	/*
	 * onCreatureSpawn Funktion von https://www.spigotmc.org/threads/disable-mob-spawning-naturally.426033/ User: RoxioCZ
	 */
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event){
		
        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)){
        	
        	event.setCancelled(true);
        
        }
    }
}
