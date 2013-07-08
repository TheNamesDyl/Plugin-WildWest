package net.daboross.bukkitdev.wildwest;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class TeleportingBandits extends JavaPlugin implements Listener{
    @Override
    public void onEnable() {
    	Bukkit.getPluginManager().registerEvents(this,this);
    	getConfig().options().copyDefaults(true);
        saveConfig();

    }
    		  
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
    	Player p = (Player) sender;
    	if (commandLabel.equalsIgnoreCase("setbandit")){
            getConfig().set("bandit.world", p.getLocation().getWorld().getName());
            getConfig().set("bandit.x", p.getLocation().getX());
            getConfig().set("bandit.y", p.getLocation().getY());
            getConfig().set("bandit.z", p.getLocation().getZ());
            saveConfig();
            p.sendMessage(ChatColor.GREEN + "bandit set!");
            return true;
            
            
            
       
           
		}
		return false;
    
    }
    @EventHandler
    public void onSignClick(PlayerInteractEvent p) {
        Material t = p.getClickedBlock().getType();
            if ((t == Material.WALL_SIGN || t == Material.SIGN
                || t == Material.SIGN_POST)
                && p.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Sign sign = (Sign) p.getClickedBlock().getState();
            if (sign.getLine(0).contains("[Bandit]")) {
            	Player p2 = (Player) p.getPlayer();
                World w = Bukkit.getServer().getWorld(getConfig().getString("bandit.world"));
                double x = getConfig().getDouble("bandit.x");
                double y = getConfig().getDouble("bandit.y");
                double z = getConfig().getDouble("bandit.z");
                p2.teleport(new Location(w, x, y, z));
                p2.sendMessage(ChatColor.GREEN + "Teleport bandit");
            }
        }
            	
      }

	@Override
    public void onDisable() {
		saveConfig();
    }
}