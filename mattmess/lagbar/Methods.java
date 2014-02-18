package mattmess.lagbar;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class Methods{
	private static final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("LagBar");
	static LagBar lagbar = (LagBar) plugin;
	Player[] onlinePlayers = lagbar.getOnlinePlayers();
	int lagInterval = lagbar.getLagInterval();
	// Enables the plugin for player
	public static void enable(final Player player){
		lagbar.setEnabled(player, true);
		final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		// Continually Repeat
		if(lagbar.isLagEnabled()){
			scheduler.scheduleSyncRepeatingTask(plugin, new BukkitRunnable()
			{
				@Override
				public void run()
				{
					if(lagbar.isEnabled(player)||player.getGameMode() != GameMode.CREATIVE){
						int tps = (int) Math.round(Profiler.getTPS());
						player.setLevel(tps);
						float mem = (float) Profiler.getMemoryPercent();
						player.setExp(mem);
					}
				}
			}, lagbar.getLagInterval(), lagbar.getLagInterval());
		}
	}
	
	// Disables for specified player
	public static void disable( final Player player){
		player.setLevel(0);
		player.setExp(0);
		lagbar.setEnabled(player, false);
	}
	
	// Enables for everyone with the permission
	public static void enableAllOnline(){
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			if(player.hasPermission("tpsbar.autoenable")){
				enable(player);
			}
		}
	}
	
	// Disables for everyone
	public static void disableAllOnline() {
		for(Player player : lagbar.getOnlinePlayers()){
			disable(player);
		}
	}
	
}
