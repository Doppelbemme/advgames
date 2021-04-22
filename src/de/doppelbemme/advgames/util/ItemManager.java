package de.doppelbemme.advgames.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;

import de.doppelbemme.advgames.main.ADVGames;
import de.doppelbemme.advgames.skull.Skull;
import net.md_5.bungee.api.ChatColor;

public class ItemManager {
	
	public static HashSet<Material> IllegalBlocks = new HashSet<>();
	public static ArrayList<Location> blocks = new ArrayList<Location>();
	
	 static{
		 IllegalBlocks.add(Material.LEAVES);
		 IllegalBlocks.add(Material.LEAVES_2);
		 IllegalBlocks.add(Material.LOG);
		 IllegalBlocks.add(Material.WATER);
		 IllegalBlocks.add(Material.STATIONARY_WATER);
		 IllegalBlocks.add(Material.LAVA);
		 IllegalBlocks.add(Material.STATIONARY_LAVA);
	 }
	
	public void clearInventory(Player player){
		
		player.getInventory().clear();
		player.getInventory().setHelmet(new ItemStack(Material.AIR));
		player.getInventory().setChestplate(new ItemStack(Material.AIR));
		player.getInventory().setLeggings(new ItemStack(Material.AIR));
		player.getInventory().setBoots(new ItemStack(Material.AIR));
		
	}

	public void giveLobbyItems(Player player){
		
		clearInventory(player);
		
		ItemStack MagmaCream = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta MagmaCreamItemMeta = MagmaCream.getItemMeta();
		MagmaCreamItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("BackToLobbyItem")));
		MagmaCream.setItemMeta(MagmaCreamItemMeta);
		
		player.getInventory().setItem(8, MagmaCream);
		
	}
	
	public void giveSpectatorItems(Player player){
		
		Bukkit.getScheduler().runTaskLater(ADVGames.main, new Runnable() {
			
			@Override
			public void run() {
				
				clearInventory(player);
				
				ItemStack Compass = new ItemStack(Material.COMPASS);
				ItemMeta CompassItemMeta = Compass.getItemMeta();
				CompassItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("SpectatorItem")));
				Compass.setItemMeta(CompassItemMeta);
				
				ItemStack MagmaCream = new ItemStack(Material.MAGMA_CREAM);
				ItemMeta MagmaCreamItemMeta = MagmaCream.getItemMeta();
				MagmaCreamItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("BackToLobbyItem")));
				MagmaCream.setItemMeta(MagmaCreamItemMeta);
				
				player.getInventory().setItem(4, Compass);
				player.getInventory().setItem(8, MagmaCream);
				
			}
		}, 2);
		
		clearInventory(player);
		
	}
	
	public void giveRestartItems(Player player){
		 
		clearInventory(player);
		
		ItemStack MagmaCream = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta MagmaCreamItemMeta = MagmaCream.getItemMeta();
		MagmaCreamItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("BackToLobbyItem")));
		MagmaCream.setItemMeta(MagmaCreamItemMeta);
		
		player.getInventory().setItem(8, MagmaCream);
		
	}
	
	public void openPlayerTeleporter(Player player){
		
		Inventory SpectatorInventory = Bukkit.createInventory(null, 9*3, ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.MessageConfig.getString("SpectatorInventory")));
		SpectatorInventory.clear();
		
		for(Player PlayerAlive : ADVGames.main.PlayerAlive){
				
			ItemStack PlayerHead = Skull.getPlayerSkull(PlayerAlive.getName());
			ItemMeta PlayerHeadMeta = PlayerHead.getItemMeta();
			PlayerHeadMeta.setDisplayName("§6"+PlayerAlive.getName());
			PlayerHead.setItemMeta(PlayerHeadMeta);
				
			SpectatorInventory.addItem(PlayerHead);
			player.openInventory(SpectatorInventory);
			
		}
	}

	public void saveItem(int id, short SubID, int amount, String ItemName, boolean Splash){
		
		int TotalItems = ADVGames.main.fileManager.itemConfig.getInt("TotalItems");
		int newTotalItems = TotalItems+1;
				
		ADVGames.main.fileManager.itemConfig.set("TotalItems", newTotalItems);
		ADVGames.main.fileManager.itemConfig.set(String.valueOf(newTotalItems)+".name", ItemName);
		ADVGames.main.fileManager.itemConfig.set(String.valueOf(newTotalItems)+".id", id);
		ADVGames.main.fileManager.itemConfig.set(String.valueOf(newTotalItems)+".subid", SubID);
		ADVGames.main.fileManager.itemConfig.set(String.valueOf(newTotalItems)+".amount", amount);
		ADVGames.main.fileManager.itemConfig.set(String.valueOf(newTotalItems)+".splash", Splash);
		
		ADVGames.main.fileManager.saveItemFile();
		
	}
	
	@SuppressWarnings("deprecation")
	public void getRandomItem(Location BlockLocation){
		
		int TotalItems = ADVGames.main.fileManager.itemConfig.getInt("TotalItems");
		
		if(TotalItems == 0){
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', ADVGames.main.fileManager.itemConfig.getString("Prefix")) + "§7Der Server ist fertig §cnicht konfiguriert§7. §7Wurden §cItems §7hinzugefügt ?");
			return;
		}
		
		Random random = new Random();
		int RandomInteger = random.nextInt(TotalItems+1);
		
		int ID = ADVGames.main.fileManager.itemConfig.getInt(String.valueOf(RandomInteger)+".id");
		byte SubID = (byte) ADVGames.main.fileManager.itemConfig.getInt(String.valueOf(RandomInteger)+".subid");
		int Amount = ADVGames.main.fileManager.itemConfig.getInt(String.valueOf(RandomInteger)+".amount");
		ItemStack ItemToDrop;
		boolean Splash = ADVGames.main.fileManager.itemConfig.getBoolean(String.valueOf(RandomInteger)+".splash");
		ItemToDrop = new ItemBuilder(Material.getMaterial(ID), Amount, SubID).toItemStack();
		
		
		while(ItemToDrop.getTypeId() == 0){
			random = new Random();
			RandomInteger = random.nextInt(TotalItems+1);
			
			ID = ADVGames.main.fileManager.itemConfig.getInt(String.valueOf(RandomInteger)+".id");
			SubID = (byte) ADVGames.main.fileManager.itemConfig.getInt(String.valueOf(RandomInteger)+".subid");
			Amount = ADVGames.main.fileManager.itemConfig.getInt(String.valueOf(RandomInteger)+".amount");
			
			ItemToDrop = new ItemBuilder(Material.getMaterial(ID), Amount, SubID).toItemStack();
		}
		
		
		if(ItemToDrop.getType().toString().toLowerCase().equalsIgnoreCase("potion")){
			
			Potion PotionToDrop = Potion.fromItemStack(ItemToDrop);
			if(Splash){
				PotionToDrop.setSplash(true);
			}
			
			BlockLocation.getWorld().dropItem(BlockLocation, PotionToDrop.toItemStack(Amount));
			return;
		}
		
		BlockLocation.getWorld().dropItem(BlockLocation, ItemToDrop);
	}
	
	
	public void spawnDiamondBlocks(){
		
		World world = Bukkit.getWorld(ADVGames.main.fileManager.locationConfig.getString("BlockSpawnRegion.pos1.world"));
		
		int x1 = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion.pos1.x");
		int x2 = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion.pos2.x");
		
		int y1 = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion.pos1.y");
		int y2 = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion.pos2.y");
		
		int z1 = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion.pos1.z");
		int z2 = ADVGames.main.fileManager.locationConfig.getInt("BlockSpawnRegion.pos2.z");
		
		for(int i = 0; i<ADVGames.main.DiamondBlocksToSpawn;){
		
			int min;
			int max;
		
			if(x1 > x2){
				min = x2;
				max = x1;
			}else{
				min = x1;
				max = x2;
			}
		
			int x = getRandomNumberInRange(min, max);

			if(y1 > y2){
				min = y2;
				max = y1;
			}else{
				min = y1;
				max = y2;
			}
		
			int y = getRandomNumberInRange(min, max);
		
			if(z1 > z2){
				min = z2;
				max = z1;
			}else{
				min = z1;
				max = z2;
			}
		
			int z = getRandomNumberInRange(min, max);
	
			Location RandomLocation = new Location(world, x, y, z);
			y = RandomLocation.getWorld().getHighestBlockYAt(RandomLocation);
			RandomLocation.setY(y);
		
			if(isLocationSafe(RandomLocation)){
				blocks.add(RandomLocation);
				RandomLocation.getBlock().setType(Material.DIAMOND_BLOCK);
				i++;
			}
		}
	}
	
	public void removeDiamondBlocks(){
		
		for(Location BlocksToReset : blocks){
			BlocksToReset.getBlock().setType(Material.AIR);
		}
		
	}
	
	private static boolean isLocationSafe(Location location){
		
		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		
		Block block = location.getWorld().getBlockAt(x, y, z);
		Block below = location.getWorld().getBlockAt(x, y - 1, z);
		Block above = location.getWorld().getBlockAt(x, y + 1, z);
		
		return !(IllegalBlocks.contains(block.getType()) || (IllegalBlocks.contains(below.getType()) || IllegalBlocks.contains(above.getType())));
	}
	
	/**
	 * 
	 * getRandomNumberInRange Funktion von https://mkyong.com/java/java-generate-random-integers-in-a-range/
	 * 
	 */
	
    private int getRandomNumberInRange(int min, int max) {

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    
    }
    
}
	

