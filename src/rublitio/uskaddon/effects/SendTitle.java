package rublitio.uskaddon.effects;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PlayerConnection;

public class SendTitle extends Effect {
	private Expression<String> Title;
	private Expression<String> subTitle;
	private Expression<Player> Player;
	private Expression<Timespan> time;
	private Expression<Timespan> fadeIn;
	private Expression<Timespan> fadeOut;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parse) {
		this.Title = (Expression<String>) exprs[0];
		this.subTitle = (Expression<String>) exprs[1];
		this.time = (Expression<Timespan>) exprs[2];
	    this.fadeIn = (Expression<Timespan>) exprs[3];
	    this.fadeOut = (Expression<Timespan>) exprs[4];
		this.Player = (Expression<Player>) exprs[5];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		
		return null;
	}

	@Override
	protected void execute(Event e) {
	    int timeTick = 60;
	    if (this.time != null) {
	      timeTick = (int) ((Timespan)this.time.getSingle(e)).getTicks_i();
	    }

	    for (Player p : (Player[])this.Player.getAll(e)) {
	      PlayerConnection connection = ((CraftPlayer)p.getPlayer()).getHandle().playerConnection;

	      if ((this.fadeIn != null) && (this.fadeOut != null)) {
	        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 
	          (int) ((Timespan)this.fadeIn.getSingle(e)).getTicks_i(), timeTick, (int) ((Timespan)this.fadeOut.getSingle(e)).getTicks_i());
	        connection.sendPacket(packetPlayOutTimes);
	      } else if ((this.fadeIn == null) && (this.fadeOut != null)) {
	        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 
	          5, timeTick, (int) ((Timespan)this.fadeOut.getSingle(e)).getTicks_i());
	        connection.sendPacket(packetPlayOutTimes);
	      } else if ((this.fadeIn != null) && (this.fadeOut == null)) {
	        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 
	          (int) ((Timespan)this.fadeIn.getSingle(e)).getTicks_i(), timeTick, 5);
	        connection.sendPacket(packetPlayOutTimes);
	      } else {
	        PacketPlayOutTitle packetPlayOutTimes = 
	          new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, 5, timeTick, 5);
	        connection.sendPacket(packetPlayOutTimes);
	      }

	      if (this.subTitle != null) {
	        IChatBaseComponent finalSub = 
	          IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ((String)this.subTitle.getSingle(e)).toString() + "\"}");
	        PacketPlayOutTitle packetPlayOutSubTitle = 
	          new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, finalSub);
	        connection.sendPacket(packetPlayOutSubTitle);
	      }

	      if (this.Title != null) {
	        IChatBaseComponent finalTitle = 
	          IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ((String)this.Title.getSingle(e)).toString() + "\"}");
	        PacketPlayOutTitle packetPlayOutTitle = 
	          new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, finalTitle);
	        connection.sendPacket(packetPlayOutTitle);
	      }
	    }
	}

}
