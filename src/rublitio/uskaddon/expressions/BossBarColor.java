package rublitio.uskaddon.expressions;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

public class BossBarColor extends SimplePropertyExpression<BossBar, BarColor>
{
  public BarColor convert(BossBar bar)
  {
    if (bar == null)
      return null;
    return bar.getColor();
  }

  public void change(Event e, Object[] delta, ChangeMode mode)
  {
    BossBar bar = (BossBar)getExpr().getSingle(e);
    if (bar == null)
      return;
    BarColor b = (BarColor)delta[0];
    if (mode == Changer.ChangeMode.SET)
      bar.setColor(b);
  }

  public Class<?>[] acceptChange(ChangeMode mode)
  {
    if (mode == Changer.ChangeMode.SET)
      return (Class[])CollectionUtils.array(new Class[] { BarColor.class });
    return null;
  }

  public Class<? extends BarColor> getReturnType() {
    return BarColor.class;
  }

  protected String getPropertyName()
  {
    return "color of boss bar";
  }
}