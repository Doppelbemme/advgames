package de.doppelbemme.advgames.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.doppelbemme.advgames.command.AddItem;
import de.doppelbemme.advgames.command.SetLocation;
import de.doppelbemme.advgames.command.SetRegion;
import de.doppelbemme.advgames.command.Start;
import de.doppelbemme.advgames.file.FileManager;
import de.doppelbemme.advgames.listener.PlayerBuildBreak;
import de.doppelbemme.advgames.listener.PlayerChat;
import de.doppelbemme.advgames.listener.PlayerDamage;
import de.doppelbemme.advgames.listener.PlayerFoodlevel;
import de.doppelbemme.advgames.listener.PlayerInteract;
import de.doppelbemme.advgames.listener.PlayerJoin;
import de.doppelbemme.advgames.listener.PlayerMove;
import de.doppelbemme.advgames.listener.PlayerLogin;
import de.doppelbemme.advgames.listener.PlayerQuit;
import de.doppelbemme.advgames.listener.WorldEntitySpawning;
import de.doppelbemme.advgames.listener.WorldWeather;
import de.doppelbemme.advgames.region.RegionManager;
import de.doppelbemme.advgames.util.ItemManager;
import de.doppelbemme.advgames.util.LocationManager;
import de.doppelbemme.advgames.util.PlayerManager;
import net.md_5.bungee.api.ChatColor;


public class ADVGames extends JavaPlugin{

	public static ADVGames main;
	public FileManager fileManager;
	public Countdown countdown;
	public PlayerManager playerManager;
	public LocationManager locationManager;
	public ItemManager itemManager;
	public RegionManager regionManager;
	public GameState gameState;
	
	public int LobbyTime = 60;
	public int InGameTime = 60*15+6;
	public int RestartTime = 16;
	
	public int MIN_PLAYER = 2;
	public int MAX_PLAYER = 16;

	public int DiamondBlocksToSpawn = 150;
	
	public ArrayList<Player> PlayerAlive = new ArrayList<Player>();
	
	@Override
	public void onEnable() {
		
		main = this;
		fileManager = new FileManager();
		countdown = new Countdown();
		playerManager = new PlayerManager();
		locationManager = new LocationManager();
		itemManager = new ItemManager();
		regionManager = new RegionManager();
		
		fileManager.setupItemFile();
		fileManager.saveItemFile();
		fileManager.setupMessageFile();
		fileManager.saveMessageFile();
		
		regionManager.registerRegion();
		
		PluginManager PluginManager = Bukkit.getServer().getPluginManager();
		PluginManager.registerEvents(new PlayerJoin(), this);
		PluginManager.registerEvents(new PlayerDamage(), this);
		PluginManager.registerEvents(new PlayerQuit(), this);
		PluginManager.registerEvents(new PlayerLogin(), this);
		PluginManager.registerEvents(new PlayerChat(), this);
		PluginManager.registerEvents(new PlayerBuildBreak(), this);
		PluginManager.registerEvents(new PlayerFoodlevel(), this);
		PluginManager.registerEvents(new PlayerMove(), this);
		PluginManager.registerEvents(new PlayerInteract(), this);
		PluginManager.registerEvents(new WorldWeather(), this);
		PluginManager.registerEvents(new WorldEntitySpawning(), this);
		
		getCommand("start").setExecutor(new Start());
		getCommand("setLocation").setExecutor(new SetLocation());
		getCommand("additem").setExecutor(new AddItem());
		getCommand("setregion").setExecutor(new SetRegion());
		
		if(regionManager.regions.size() != 0){
			itemManager.spawnDiamondBlocks();
		}else{
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', fileManager.MessageConfig.getString("Prefix")) + "§bDiamantblöcke §ckonnten nicht gespawnt werden. Keine §bRegion §cgefunden!");
		}
		
		gameState = GameState.LOBBY;
		
	}
	
	
	@Override
	public void onDisable() {
		itemManager.removeDiamondBlocks();
	}
	
}
