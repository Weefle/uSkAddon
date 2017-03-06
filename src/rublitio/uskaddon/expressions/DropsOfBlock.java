package rublitio.uskaddon.expressions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class DropsOfBlock extends SimpleExpression<ItemStack>
{
	private Expression<Block> block;
	private Expression<ItemStack> item;
	private boolean b;
  public Class<? extends ItemStack> getReturnType()
  {
    return ItemStack.class;
  }

  public boolean isSingle()
  {
    return true;
  }

  @SuppressWarnings("unchecked")
  @Override
public boolean init(Expression<?>[] exprs, int i, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
	this.block = (Expression<Block>) exprs[0];
	this.b = i == 1;
	if(i ==1)
		this.item = (Expression<ItemStack>) exprs[1];
    return true;
  }
  @Override
  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "drops of a block";
  }
  @Override
  @Nullable
  protected ItemStack[] get(Event e)
  {
		 ItemStack[] items = new ItemStack[((BlockEvent)e).getBlock().getDrops().size()];
		  int i = 0;
		  if(!b)
			  for(ItemStack is : this.block.getSingle(e).getDrops()){
			  	items[i] = is;
			  	i++;
		  	}
		  else 
			  for(ItemStack is : this.block.getSingle(e).getDrops(this.item.getSingle(e))){
				  	items[i] = is;
				  	i++;
			  	}
		  return items;
  }

  /*public void change(Event e, Object[] delta, Changer.ChangeMode mode)
  {
    ItemStack[] newItem = (ItemStack[])delta;
    if (mode == Changer.ChangeMode.SET){
      ((BlockEvent)e).getBlock().getDrops().clear();
  	for(ItemStack is : newItem)
		((BlockEvent)e).getBlock().getDrops().add(is);
    }
    else if(mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.REMOVE_ALL){
    	((BlockEvent)e).getBlock().getDrops().clear();
    }
    else if(mode == Changer.ChangeMode.ADD){
    	for(ItemStack is : newItem)
    		((BlockEvent)e).getBlock().getDrops().add(is);
    }
    else if(mode == Changer.ChangeMode.DELETE){
    	for(ItemStack is : newItem){
    		if(((BlockEvent)e).getBlock().getDrops().contains(is))
    			((BlockEvent)e).getBlock().getDrops().remove(is);
    	}
    }
    
  }

  public Class<?>[] acceptChange(Changer.ChangeMode mode)
  {
    return (Class[])CollectionUtils.array(new Class[] { ItemStack.class });
  }*/
}