package rublitio.uskaddon.effects;

import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class RemoveFlagFromBossBar extends Effect
  implements Listener
{
  private Expression<BossBar> bar;
  private Expression<BarFlag> flag;

  protected void execute(Event event)
  {
    ((BossBar)this.bar.getSingle(event)).removeFlag((BarFlag)this.flag.getSingle(event));
  }

  public String toString(Event event, boolean b)
  {
    return "Add Flag to Boss Bar";
  }

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult)
  {
    this.bar = (Expression<BossBar>) exprs[1];
    this.flag = (Expression<BarFlag>) exprs[0];
    return true;
  }
}