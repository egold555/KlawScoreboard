package org.golde.bukkit.klawscoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class SimpleScoreboardHealth {

	private Scoreboard scoreboard;
	private final String title;

	/*public SimpleScoreboardHealth(String title) {
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.title = title;
	}*/
	
	public SimpleScoreboardHealth(Scoreboard scoreboard, String title) {
		this.scoreboard = scoreboard;
		this.title = title;
	}

	public void build() {
		Objective obj = scoreboard.registerNewObjective((title.length() > 16 ? title.substring(0, 15) : title), "dummy");
		obj.setDisplaySlot(DisplaySlot.BELOW_NAME);
		obj.setDisplayName(title);
	}

	public void send(Player... players) {
		for (Player p : players) {
			p.setScoreboard(scoreboard);
			p.setHealth(p.getHealth());
		}
	}
	
}
