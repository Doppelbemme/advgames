package de.doppelbemme.advgames.file;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	/** 
	 * Message.yml 
	 * Beinhaltet alle Plugin Nachrichten
	 */
	
	
	public File MessageFile = new File("plugins/ADVGames", "messages.properties");
	public FileConfiguration MessageConfig = YamlConfiguration.loadConfiguration(MessageFile);

	public void setupMessageFile(){
		
		MessageConfig.addDefault("Prefix", "&3&lAdvGames &8〣 &7");
		MessageConfig.addDefault("LobbyCountdown", "&7Die Runde beginnt in &b%time% &7Sekunden.");
		MessageConfig.addDefault("LobbyCountdownLastSecond", "&7Die Runde beginnt in &beiner &7Sekunde.");
		
		MessageConfig.addDefault("GracePeriod", "&7Die Schutzzeit endet in &b%seconds% &7Sekunden.");
		MessageConfig.addDefault("GracePeriodLastSecond", "&7Die Schutzzeit endet in &beiner &7Sekunde.");
		
		MessageConfig.addDefault("GameEndingMinutes", "&7Das Spiel endet in &b%minutes% &7Minuten.");
		MessageConfig.addDefault("GameEndingLastMinute", "&7Das Spiel endet in &beiner &7Minute.");
		MessageConfig.addDefault("GameEndingSeconds", "&7Das Spiel endet in &b%seconds% &7Sekunden.");
		MessageConfig.addDefault("GameEndingLastSecond", "&7Das Spiel endet in &beiner &7Sekunde.");

		MessageConfig.addDefault("TimeUp", "&7Die Zeit ist &cabgelaufen&7. &bNiemand &7hat das Spiel &agewonnen&7.");
		
		MessageConfig.addDefault("NoPermission", "&7Dazu hast du &ckeine Rechte&7.");
		MessageConfig.addDefault("ConsoleNotAllowed", "&cNur ein Spieler kann diesen Befehl benutzen!");
		MessageConfig.addDefault("NotEnoughPlayer", "&7Es sind &cnicht genug Spieler &7online.");
		MessageConfig.addDefault("GameAlreadyStarting", "&7Das Spiel hat &cbereits begonnen&7.");
		MessageConfig.addDefault("CountdownSkipped", "&7Das Spiel &awurde gestartet&7.");
		
		MessageConfig.addDefault("MinPlayerNeeded", "&cEs werden %min_player% Spieler benötigt damit die Runde startet.");
		
		MessageConfig.addDefault("JoinMessage", "&7» %player% hat das Spiel betreten");
		MessageConfig.addDefault("QuitMessage", "&7« %player% hat das Spiel verlassen");
		
		MessageConfig.addDefault("PlayerDeathMessage", "&7Der Spieler &b%player% &7ist gestorben!");
		MessageConfig.addDefault("PlayerKilledByPlayer", "&7Der Spieler &b%player% &7wurde von &b%killer% &7getötet!");
		
		MessageConfig.addDefault("ServerFull", "&7Dieser Server ist leider &cvoll&7.");
		MessageConfig.addDefault("ServerIsRestarting", "&7Dieser Server &crestartet &7gerade.");
		
		MessageConfig.addDefault("BackToLobbyItem", "&cZurück zur Lobby");
		MessageConfig.addDefault("SpectatorItem", "&8〣 &c&lKompass");
		MessageConfig.addDefault("SpectatorInventory", "&cKompass");
		
		MessageConfig.addDefault("GameWonMessage", "&b%winner% &7hat das Spiel &agewonnen&7.");
		
		MessageConfig.addDefault("PlayerRemaining", "&7Es verbleiben &b%remaining_players% &7Spieler.");

		MessageConfig.addDefault("ServerRestarting", "&cDer Server startet in &b%seconds% &cSekunden neu.");
		MessageConfig.addDefault("ServerRestartingLastSecond", "&cDer Server startet in &beiner &cSekunde neu.");
		
		MessageConfig.options().copyDefaults(true);

	}
	
	public void setupItemFile(){
	
		itemConfig.addDefault("TotalItems", 0);
		
		itemConfig.options().copyDefaults(true);
		
	}
	
	public void saveMessageFile() {
		try {
			MessageConfig.save(MessageFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/** 
	 * Items.yml 
	 * Beinhaltet alle aus den Kisten erhätlichen Items
	 */
	
	public File itemFile = new File("plugins/ADVGames", "items.yml");
	public FileConfiguration itemConfig = YamlConfiguration.loadConfiguration(itemFile);
	
	public void saveItemFile() {
		try {
			itemConfig.save(itemFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/** 
	 * locations.yml 
	 * Beinhaltet alle mit /setlocation gesetzten Locations
	 */
	
	public File locationFile = new File("plugins/ADVGames", "locations.yml");
	public FileConfiguration locationConfig = YamlConfiguration.loadConfiguration(locationFile);
	
	public void saveLocationFile() {
		try {
			locationConfig.save(locationFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
