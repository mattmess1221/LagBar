package mattmess.lagbar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class LagBarCommand implements CommandExecutor
{
	private Plugin plugin;
	LagBar lagbar = (LagBar) plugin;
	
	public LagBarCommand(LagBar plugin)
	{
		this.plugin = Bukkit.getServer().getPluginManager().getPlugin("LagBar");
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		String noPermission = "§cYou don't have permission to do that.";
		String toggleMsg = "LagBar\u2122 Togged for " + sender.getName();
		String mustBePlayer = "You must be a player to excecute this command";
		String tooManyArgs = "Too many arguments";
		String toggleMsgOther = "Your LagBar\u2122 has been toggled.";
		String playerNotFound = "Player could not be found";
		String notEnabled = "LagBar\u2122 is not enabled in the config";
		
		if (cmd.getName().equalsIgnoreCase("lagbar"))
		{
			if(args.length>0){
				switch(args[0].toLowerCase()){
				default:
					return false;
				case "toggle":
					if(args.length<=2){if(lagbar.isLagEnabled()){
						switch(args.length){
						case 1:
							if(sender instanceof Player){
								if(sender.hasPermission("lagbar.toggle")){
									Player target = (Player) sender;
									toggle(target);
									sender.sendMessage(toggleMsg);
								}else{
									sender.sendMessage(noPermission);
								}
							}else{
								sender.sendMessage(mustBePlayer);
							}
							break;
						case 2:
							if(sender.hasPermission("lagbar.toggle.others")){
								Player target = Bukkit.getServer().getPlayer(args[1].toLowerCase());
								if (target.isOnline()){
									toggle(target);
									sender.sendMessage(toggleMsg);
									target.sendMessage(toggleMsgOther);
								}else{
									target.sendMessage(playerNotFound);
								}
								
							}
							break;
						}
					}else{
						sender.sendMessage(notEnabled);
					}}
					else{
						sender.sendMessage(tooManyArgs);
					}
					break;
				case "reload":
					if(args.length == 1){
						if(sender.hasPermission("lagbar.reload")){
							reload();
						}else{
							sender.sendMessage(noPermission);
						}
					}else{
						sender.sendMessage(tooManyArgs);
					}
					break;
					
				}
				return true;
			}
		}
		return false;
	}

	private void reload() {
		// TODO Auto-generated method stub
		plugin.reloadConfig();
	}

	private void toggle(Player player) {
		if(lagbar.isEnabled(player)){
			Methods.disable(player);
		}else{
			Methods.enable(player);
		}
	}
}
