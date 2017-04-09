package rublitio.uskaddon.effects;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import rublitio.uskaddon.utils.TitleAPI;

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
			for (Player p : (Player[])this.player.getAll(e))
				TitleAPI.sendActionBar(p, this.text.getSingle(e));
	  }
}
