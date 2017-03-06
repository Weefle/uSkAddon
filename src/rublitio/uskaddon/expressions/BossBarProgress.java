package rublitio.uskaddon.expressions;

import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class BossBarProgress extends SimplePropertyExpression<BossBar, Number>
{
  public Number convert(BossBar bar)
  {
    if (bar == null)
      return null;
    return Double.valueOf(bar.getProgress());
  }

  public void change(Event e, Object[] delta, ChangeMode mode)
  {
    BossBar bar = (BossBar)getExpr().getSingle(e);
    if (bar == null)
      return;
    Number b = (Number)delta[0];
    if (mode == Changer.ChangeMode.SET)
      bar.setProgress(b.doubleValue());
  }

  public Class<?>[] acceptChange(ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { Number.class });
    return null;
  }

  public Class<? extends Number> getReturnType() {
    return Number.class;
  }

  protected String getPropertyName()
  {
    return "title of boss bar";
  }
}