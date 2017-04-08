package rublitio.uskaddon.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class InventoryName extends SimpleExpression<String> {
	private Expression<Inventory> inventory = null;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int i, Kleenean arg2, ParseResult arg3) {
		if(i == 1)
			this.inventory = (Expression<Inventory>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "inventory name";
	}

	@Override
	@Nullable
	protected String[] get(Event e) {
		if(this.inventory != null)
			return new String[]{this.inventory.getSingle(e).getName()};
		if(e instanceof InventoryClickEvent)
			return new String[]{((InventoryClickEvent) e).getClickedInventory().getName()};
		return null;
	}



}
