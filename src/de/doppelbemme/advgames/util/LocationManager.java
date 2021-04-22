package de.doppelbemme.advgames.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.doppelbemme.advgames.main.ADVGames;

public class LocationManager {

	public void setLocation(Location Location, String LocationName) {
		
		ADVGames.main.fileManager.locationConfig.set(LocationName+".world", Location.getWorld().getName());
		ADVGames.main.fileManager.locationConfig.set(LocationName+".x", Location.getX());
		ADVGames.main.fileManager.locationConfig.set(LocationName+".y", Location.getY());
		ADVGames.main.fileManager.locationConfig.set(LocationName+".z", Location.getZ());
		
		ADVGames.main.fileManager.locationConfig.set(LocationName+".yaw", Location.getYaw());
		ADVGames.main.fileManager.locationConfig.set(LocationName+".pitch", Location.getPitch());
		ADVGames.main.fileManager.saveLocationFile();
		
	}
	
	public Location getLocation(String LocationName) {
		World World = Bukkit.getWorld(ADVGames.main.fileManager.locationConfig.getString(LocationName+".world"));
		double x = ADVGames.main.fileManager.locationConfig.getDouble(LocationName+".x");
		double y = ADVGames.main.fileManager.locationConfig.getDouble(LocationName+".y");
		double z = ADVGames.main.fileManager.locationConfig.getDouble(LocationName+".z");
		Location Location = new Location(World, x, y, z);
		Location.setYaw(ADVGames.main.fileManager.locationConfig.getInt(LocationName+".yaw"));
		Location.setPitch(ADVGames.main.fileManager.locationConfig.getInt(LocationName+".pitch"));
		return Location;
		
	}
	
}
