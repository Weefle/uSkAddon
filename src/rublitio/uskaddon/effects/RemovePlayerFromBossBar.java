package rublitio.uskaddon.effects;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class RemovePlayerFromBossBar extends Effect
  implements Listener
{
  private Expression<BossBar> bar;
  private Expression<Player> player;

  protected void execute(Event event)
  {
    ((BossBar)this.bar.getSingle(event)).removePlayer((Player)this.player.getSingle(event));
  }

  public String toString(Event event, boolean b)
  {
   return "Remove Player from Boss Bar"; // return "Add Player to Boss Bar";
  }

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult)
  {
    this.bar = (Expression<BossBar>) exprs[1];
    this.player = (Expression<Player>) exprs[0];
    return true;
  }
}