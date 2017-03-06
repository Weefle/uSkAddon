package rublitio.uskaddon.effects;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;

public class SendActionBar extends Effect{
	  private Expression<Player> player;
	  private Expression<String> text;
	  @SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3)
	  {
	    this.player = (Expression<Player>) exprs[0];
	    this.text = (Expression<String>) exprs[1];
	    return true;
	  }

	  public String toString(@Nullable Event e, boolean arg1)
	  {
	    return null;
	  }

	  protected void execute(Event e)
	  {
			for (Player p : (Player[])this.player.getAll(e)) {
				((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ((String)this.text.getSingle(e)).replace("\"", "") + "\"}"), (byte)2));
	    	}
	  }
}
