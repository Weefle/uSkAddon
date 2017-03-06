package rublitio.uskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

public class FlagsOfBossBar extends SimpleExpression<BarFlag>
{
  Expression<BossBar> bar;

  protected BarFlag[] get(Event event)
  {
    List<BarFlag> flags = new ArrayList<BarFlag>();
    BossBar bar = (BossBar)this.bar.getSingle(event);
    for (BarFlag barFlag : BarFlag.values()) {
      if (bar.hasFlag(barFlag)) {
        flags.add(barFlag);
      }
    }
    return (BarFlag[])flags.toArray(new BarFlag[flags.size()]);
  }

  public boolean isSingle()
  {
    return false;
  }

  public Class<? extends BarFlag> getReturnType()
  {
    return BarFlag.class;
  }

  public String toString(Event event, boolean b)
  {
    return "flags of boss bar";
  }

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult)
  {
    this.bar = (Expression<BossBar>) exprs[0];
    return true;
  }
}