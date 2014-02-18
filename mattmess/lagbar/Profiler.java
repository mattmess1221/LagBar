package mattmess.lagbar;

import org.bukkit.Bukkit;
import com.webkonsept.minecraft.lagmeter.LagMeter;
import com.webkonsept.minecraft.lagmeter.exceptions.NoAvailableTPSException;

public class Profiler{
	private static LagMeter lagmeter = (LagMeter) Bukkit.getServer().getPluginManager().getPlugin("LagMeter");
	
	public static double getUsedMemory(){
		return lagmeter.getMemory()[0];
	}
	public static double getMaxMemory(){
		return lagmeter.getMemory()[1];
	}
	public static double getFreeMemory(){
		return lagmeter.getMemory()[2];
	}
	public static double getMemoryPercent(){
		return lagmeter.getMemory()[3]/100;
	}
	public static double getTPS()
	{
		try {
			return lagmeter.getTPS();
		} catch (NoAvailableTPSException e) {
			return 0;
		}
	}
	/* WIP
	 * public static double getPing(CommandSender sender,String[] args){
	 * 	lagmeter.ping(sender, args);
	 * 	
	 * }
	 */
}
