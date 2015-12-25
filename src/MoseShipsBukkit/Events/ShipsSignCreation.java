package MoseShipsBukkit.Events;

import javax.annotation.Nullable;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ShipsSignCreation extends Event implements Cancellable{

	boolean CANCELLED;
	JavaPlugin PLUGIN;
	Player PLAYER;
	String SIGNTEXT;
	String TEXT;
	Sign SIGN;
	static HandlerList LIST = new HandlerList();
	
	public ShipsSignCreation(JavaPlugin plugin, String signText, Sign sign){
		PLUGIN = plugin;
		SIGNTEXT = signText;
		SIGN = sign;
	}
	
	public ShipsSignCreation(JavaPlugin plugin, String signText, Sign sign, Player player, String playerText){
		PLUGIN = plugin;
		SIGNTEXT = signText;
		SIGN = sign;
		
		PLAYER = player;
		SIGNTEXT = signText;
	}
	
	public JavaPlugin getCause(){
		return PLUGIN;
	}
	
	public @Nullable Player getPlayer(){
		return PLAYER;
	}
	
	public String getSignTypeResult(){
		return SIGNTEXT;
	}
	
	public @Nullable String getTypedText(){
		return TEXT;
	}
	
	public Sign getSign(){
		return SIGN;
	}
	
	@Override
	public boolean isCancelled() {
		return CANCELLED;
	}

	@Override
	public void setCancelled(boolean arg0) {
		CANCELLED = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		return LIST;
	}
	
	public static HandlerList getHandlerList(){
		return LIST;
	}
	
	

}
