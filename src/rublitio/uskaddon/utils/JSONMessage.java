package rublitio.uskaddon.utils;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.gson.stream.JsonWriter;

public class JSONMessage
{
  private final List<MessagePart> messageParts;
  private String jsonString;
  private boolean dirty;
  private Class<?> nmsTagCompound = Reflection.nmsClass("NBTTagCompound");
  //private Class<?> nmsPacketPlayOutChat = Reflection.nmsClass("PacketPlayOutChat");
  private Class<?> nmsAchievement = Reflection.nmsClass("Achievement");
  //private Class<?> nmsStatistic = Reflection.nmsClass("Statistic");
  private Class<?> nmsItemStack = Reflection.nmsClass("ItemStack");
  private Class<?> obcStatistic = Reflection.obcClass("CraftStatistic");
  private Class<?> obcItemStack = Reflection.obcClass("inventory.CraftItemStack");

public JSONMessage(String firstPartText)
  {
    this.messageParts = new ArrayList<MessagePart>();
    this.messageParts.add(new MessagePart(firstPartText));
    this.jsonString = null;
    this.dirty = false;
  }

  public JSONMessage color(ChatColor color)
  {
    if (!color.isColor()) {
      throw new IllegalArgumentException(new StringBuilder().append(color.name()).append(" is not a color").toString());
    }
    latest().color = color;
    this.dirty = true;
    return this;
  }

  public JSONMessage style(ChatColor[] styles)
  {
    for (ChatColor style : styles) {
      if (!style.isFormat()) {
        throw new IllegalArgumentException(new StringBuilder().append(style.name()).append(" is not a style").toString());
      }
    }
    latest().styles = styles;
    this.dirty = true;
    return this;
  }

  public JSONMessage file(String path)
  {
    onClick("open_file", path);
    return this;
  }

  public JSONMessage link(String url)
  {
    onClick("open_url", url);
    return this;
  }

  public JSONMessage suggest(String command)
  {
    onClick("suggest_command", command);
    return this;
  }

  public JSONMessage command(String command)
  {
    onClick("run_command", command);
    return this;
  }

  public JSONMessage achievementTooltip(String name)
  {
    onHover("show_achievement", new StringBuilder().append("achievement.").append(name).toString());
    return this;
  }

  public JSONMessage achievementTooltip(Achievement which)
  {
    try
    {
      Object achievement = Reflection.getMethod(this.obcStatistic, "getNMSAchievement", new Class[0]).invoke(null, new Object[] { which });
      return achievementTooltip((String)Reflection.getField(this.nmsAchievement, "name").get(achievement));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return this;
  }

  public JSONMessage itemTooltip(String itemJSON)
  {
    onHover("show_item", itemJSON);
    return this;
  }

  public JSONMessage itemTooltip(ItemStack itemStack)
  {
    try
    {
      Object nmsItem = Reflection.getMethod(this.obcItemStack, "asNMSCopy", new Class[] { ItemStack.class }).invoke(null, new Object[] { itemStack });
      return itemTooltip(Reflection.getMethod(this.nmsItemStack, "save", new Class[0]).invoke(nmsItem, new Object[] { this.nmsTagCompound.newInstance() }).toString());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return this;
  }

  public JSONMessage tooltip(String[] lines)
  {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < lines.length; i++)
    {
      builder.append(lines[i]);
      if (i != lines.length - 1) {
        builder.append('\n');
      }
    }
    onHover("show_text", builder.toString());
    return this;
  }

  public JSONMessage then(Object obj)
  {
    this.messageParts.add(new MessagePart(obj.toString()));
    this.dirty = true;
    return this;
  }

  public String toJSONString()
  {
    if ((!this.dirty) && (this.jsonString != null)) {
      return this.jsonString;
    }
    StringWriter string = new StringWriter();
    JsonWriter json = new JsonWriter(string);
    try
    {
      if (this.messageParts.size() == 1)
      {
        latest().writeJson(json);
      }
      else
      {
        json.beginObject().name("text").value("").name("extra").beginArray();
        for (MessagePart part : this.messageParts) {
          part.writeJson(json);
        }
        json.endArray().endObject();
        json.close();
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException("invalid message");
    }
    this.jsonString = string.toString();
    this.dirty = false;
    return this.jsonString;
  }

  public void send(Player[] players)
  {
	final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	final String nmsClass = ((!version.startsWith("v1_7_R")) ? "IChatBaseComponent$" : "") + "ChatSerializer";
	for (Player p : players) {
		try {
			final Object packet = TitleAPI.getNMSClass("PacketPlayOutChat").getConstructor(TitleAPI.getNMSClass("IChatBaseComponent")).newInstance(TitleAPI.getNMSClass(nmsClass).getMethod("a", String.class).invoke(null, toJSONString()));
			final Object handle = p.getClass().getMethod("getHandle").invoke(p);
			final Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", TitleAPI.getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | InstantiationException | NoSuchFieldException ex) {
			ex.printStackTrace();
		}
	}
 }

  public String toOldMessageFormat()
  {
    StringBuilder result = new StringBuilder();
    for (MessagePart part : this.messageParts) {
      result.append(part.color).append(part.text);
    }
    return result.toString();
  }

  private MessagePart latest()
  {
    return (MessagePart)this.messageParts.get(this.messageParts.size() - 1);
  }

  private void onClick(String name, String data)
  {
    MessagePart latest = latest();
    latest.clickActionName = name;
    latest.clickActionData = data;
    this.dirty = true;
  }

  private void onHover(String name, String data)
  {
    MessagePart latest = latest();
    latest.hoverActionName = name;
    latest.hoverActionData = data;
    this.dirty = true;
  }
}