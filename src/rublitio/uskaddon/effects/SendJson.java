package rublitio.uskaddon.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import rublitio.uskaddon.utils.JSONMessage;

public class SendJson extends Effect
{
  private Expression<JSONMessage> json;
  private Expression<Player> players;

  protected void execute(Event event)
  {
    JSONMessage j = (JSONMessage)this.json.getSingle(event);
    if (j == null) {
      return;
    }
    j.send((Player[])this.players.getAll(event));
  }

  public String toString(Event event, boolean b)
  {
    return "json";
  }
  @SuppressWarnings("unchecked")
@Override
  public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parseResult)
  {
    this.json = (Expression<JSONMessage>) exprs[0];
    this.players = (Expression<Player>) exprs[1];
    return true;
  }

}