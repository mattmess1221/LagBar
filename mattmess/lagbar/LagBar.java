package mattmess.lagbar;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LagBar	extends JavaPlugin implements Listener {
	
	public Plugin plugin = this;
	private static HashMap<Player, Boolean> isEnabled = new HashMap<Player, Boolean>();
	private static Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
	private int lagInterval = plugin.getConfig().getInt("lag-refresh");
	private Boolean isLagEnabled = plugin.getConfig().getBoolean("lag-enable");
	private Boolean isPingEnabled = plugin.getConfig().getBoolean("features.ping");
		
	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		getCommand("lagbar").setExecutor(new LagBarCommand(this));
		getServer().getPluginManager().registerEvents(this, this);
		Methods.enableAllOnline();
	}
	
	@Override
	public void onDisable()
	{
		Methods.disableAllOnline();
		getServer().getScheduler().cancelTasks(this);
	}
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent event)
	{
		Methods.disable(event.getPlayer());
		if(event.getPlayer().hasPermission("tpsbar.autoenable"))
		{
			Methods.enable(event.getPlayer());
		}
	}

	//Returns true if is enabled for player, otherwise false.
	public boolean isEnabled(Player player){
		try{
			return isEnabled.get(player);
		}catch(NullPointerException e){
			return false;
		}
	}
	public void setEnabled(Player player, Boolean bol){
		isEnabled.put(player, bol);
	}
	public boolean isLagEnabled(){
		return isLagEnabled;
	}
	public Player[] getOnlinePlayers(){
		return onlinePlayers;
	}
	public int getLagInterval(){
		return lagInterval;
	}
	public boolean isPingEnabled(){
		return isPingEnabled;
	}
}
