package rublitio.uskaddon.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class SetClickedItem extends Effect
{
  Expression<ItemStack> item;

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] exprs, int i, Kleenean kleenean, ParseResult parse)
  {
    this.item = (Expression<ItemStack>) exprs[0];
    return true;
  }

  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "set clicked item";
  }

  protected void execute(Event event) {
	if(!(event instanceof InventoryClickEvent))
		return;
    final ItemStack itemTobe = (ItemStack)this.item.getSingle(event);
    ((InventoryClickEvent)event).getCurrentItem().setType(itemTobe.getType());
    ((InventoryClickEvent)event).getCurrentItem().setItemMeta(itemTobe.getItemMeta());
    ((InventoryClickEvent)event).getCurrentItem().addEnchantments(itemTobe.getEnchantments());
    ((InventoryClickEvent)event).getCurrentItem().setAmount(itemTobe.getAmount());
    ((InventoryClickEvent)event).getCurrentItem().setData(itemTobe.getData());
    ((InventoryClickEvent)event).getCurrentItem().setDurability(itemTobe.getDurability());
  }
}