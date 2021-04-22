package de.doppelbemme.advgames.region;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.doppelbemme.advgames.main.ADVGames;

public class RegionManager {

	public ArrayList<Cuboid> regions = new ArrayList<Cuboid>();
	
	public HashMap<Player, Location> Position1 = new HashMap<Player, Location>();
	public HashMap<Player, Location> Position2 = new HashMap<Player, Location>();

	
	public boolean isInsideRegion(Location location){
		
		for(Cuboid cuboid : regions){
			
			if(cuboid.contains(location)){
				
				return true;
			
			}
			
		}
		return false;
	}
	
	public void saveRegion(Location location1, Location location2){
		
		ADVGames.main.fileManager.locationConfig.set("BlockSpawnRegion.pos1.world", location1.getWorld().getName());
		ADVGames.main.fileManager.locationConfig.set("BlockSpawnRegion.pos1.x", location1.getBlockX());
		ADVGames.main.fileManager.locationConfig.set("BlockSpawnRegion.pos1.y", location1.getBlockY());
		ADVGames.main.fileManager.locationConfig.set("BlockSpawnRegion.pos1.z", location1.getBlockZ());
		
		ADVGames.main.fileManager.locationConfig.set("BlockSpawnRegion.pos2.world", location2.getWorld().getName());
		ADVGames.main.fileManager.locationConfig.set("BlockSpawnRegion.pos2.x", location2.getBlockX());
		ADVGames.main.fileManager.locationConfig.set("BlockSpawnRegion.pos2.y", location2.getBlockY());
		ADVGames.main.fileManager.locationConfig.set("BlockSpawnRegion.pos2.z", location2.getBlockZ());
		
		ADVGames.main.fileManager.saveLocationFile();
		
	}
	
	public void registerRegion(){
		
		if(ADVGames.main.fileManager.locationConfig.contains("BlockSpawnRegion.pos1.world")){
			ADVGames.main.regionManager.regions.add(new Cuboid(getLocation("pos1"), getLocation("pos2")));
		}
		
	}
	
	
	public Location getLocation(String LocationName){
		
		World world = Bukkit.getWorld(ADVGames.main.fileManager.locationConfig.getString("BlockSpawnRegion."+LocationName+".world"));
		int x = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion."+LocationName+".x");
		int y = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion."+LocationName+".y");
		int z = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion."+LocationName+".z");
		
		return new Location(world, x, y, z);
	}
	
}
