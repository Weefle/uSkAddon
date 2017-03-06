package rublitio.uskaddon.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class DeleteWorld extends Effect
{
  private Expression<String> worldname;

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3)
  {
    this.worldname = (Expression<String>) args[0];

    return true;
  }

  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "delete a world";
  }

  protected void execute(Event e)
  {
    if (worldname == null)
      return;
    if(Bukkit.getWorlds().contains(Bukkit.getWorld(this.worldname.getSingle(e))))
    	Bukkit.getServer().unloadWorld(this.worldname.getSingle(e), false);
    final File f = new File(Bukkit.getWorldContainer() + File.separator + this.worldname.getSingle(e));
    if(f.exists())
    	f.delete();
  }
}