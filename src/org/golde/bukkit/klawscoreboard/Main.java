package org.golde.bukkit.klawscoreboard;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class Main extends JavaPlugin implements Listener{

	private String host = "<none>";
	private HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
	
	public void onEnable() {
		getCommand("sethost").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, this);
		getLogger().info("KlawScoreboard plugin is starting.");
		
		new BukkitRunnable() {

			@Override
			public void run() {
				for(Player p:Bukkit.getOnlinePlayers()) {
					updateSideScoreboard(p);
				}
				
			}
			
		}.runTaskTimer(this, 0, 20*5);
	}

	public void onDisable() {
		getLogger().info("KlawScoreboard plugin is stopping.");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sethost")){
			if(!sender.hasPermission("klawscoreboard.sethost")) {
				return true;
			}
			
			if(args.length != 1) {
				sender.sendMessage("/sethost <username>");
				return true;
			}
			host = args[0];
			return true;
		}  

		// If this hasn't happened the a value of false will be returned.
		return true; 
	}
	
	void updateSideScoreboard(Player p) {
		SimpleScoreboard ss = new SimpleScoreboard("§6§lKlaw§0§lUHC");
		//updatePlayerHealth(ss.getScoreboard(), p);
		ss.blankLine();
		ss.add("§6§lSTATS:");
		ss.add("§eKills: §f" + kills.getOrDefault(p, 0));
		ss.add("§ePlayers: §f" + Bukkit.getOnlinePlayers().size());
		ss.blankLine();
		ss.add("§6§lGAME INFO:");
		ss.add("§eHost: §f" + host);
		ss.blankLine();
		ss.add("@§6§lKlaw§0§lUHC");
		
		ss.build();
		ss.send(p);
	}
	
	void updatePlayerHealth(Scoreboard scoreboard, Player p) {
		double calculated = (p.getHealth() / 20) * 100;
		
		SimpleScoreboardHealth ss = new SimpleScoreboardHealth(scoreboard, calculated + " / 100 §4❤");
		ss.build();
		ss.send(p);
	}
	
	@EventHandler
	public void playerKillPlayerEvent(EntityDeathEvent e){
		if(e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player){
			Player killer = e.getEntity().getKiller(); 
			int amount = kills.getOrDefault(killer, 0);
			amount++;
			kills.put(killer, amount);
		}
	}
	
	/*@EventHandler
	public void playerHealthChange(EntityRegainHealthEvent e) {
		if(!(e.getEntity() instanceof Player)) {return;}
		Player player = (Player) e.getEntity();
		Damageable damag = player;
		double health = damag.getHealth();
		updatePlayerHealth(player, health);
	}
	
	@EventHandler
	public void onDamage (EntityDamageEvent e) {
		if(!(e.getEntity() instanceof Player)) {return;}
		Player player = (Player) e.getEntity();
		Damageable damag = player;
		double health = damag.getHealth();
		updatePlayerHealth(player, health);
	}*/

}