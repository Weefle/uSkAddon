package rublitio.uskaddon;

import java.util.HashMap;

import org.bukkit.Chunk;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.registrations.Classes;
import rublitio.uskaddon.conditions.FileExists;
import rublitio.uskaddon.conditions.HasPotionEffect;
import rublitio.uskaddon.conditions.StringEndsWith;
import rublitio.uskaddon.conditions.StringStartsWith;
import rublitio.uskaddon.effects.AddFlagToBar;
import rublitio.uskaddon.effects.AddPlayerToBossBar;
import rublitio.uskaddon.effects.CreateFile;
import rublitio.uskaddon.effects.CreateWorld;
import rublitio.uskaddon.effects.DeleteFile;
import rublitio.uskaddon.effects.DeleteObjectInRam;
import rublitio.uskaddon.effects.DeleteWorld;
import rublitio.uskaddon.effects.HideBossBar;
import rublitio.uskaddon.effects.LoadWorld;
import rublitio.uskaddon.effects.RemoveAllPlayersFromBossBar;
import rublitio.uskaddon.effects.RemoveFlagFromBossBar;
import rublitio.uskaddon.effects.RemovePlayerFromBossBar;
import rublitio.uskaddon.effects.RequestGarbageCollector;
import rublitio.uskaddon.effects.SaveObjectInRam;
import rublitio.uskaddon.effects.SendActionBar;
import rublitio.uskaddon.effects.SendJson;
import rublitio.uskaddon.effects.SendTitle;
import rublitio.uskaddon.effects.SetClickedItem;
import rublitio.uskaddon.effects.SetCursorItem;
import rublitio.uskaddon.effects.SetKeepInventoryOnDeath;
import rublitio.uskaddon.effects.SetKeepXPOnDeath;
import rublitio.uskaddon.effects.ShowBossBar;
import rublitio.uskaddon.effects.UnloadChunk;
import rublitio.uskaddon.effects.UnloadWorld;
import rublitio.uskaddon.events.PlayerArmorStandManipulate;
import rublitio.uskaddon.events.PlayerChangeMainHand;
import rublitio.uskaddon.events.PlayerChangeWorld;
import rublitio.uskaddon.events.PlayerChatTabComplete;
import rublitio.uskaddon.events.PlayerEditBook;
import rublitio.uskaddon.events.PlayerShearEntity;
import rublitio.uskaddon.events.PlayerSwapHandItem;
import rublitio.uskaddon.events.PlayerVelocity;
import rublitio.uskaddon.expressions.BossBarColor;
import rublitio.uskaddon.expressions.BossBarFromSerialised;
import rublitio.uskaddon.expressions.BossBarProgress;
import rublitio.uskaddon.expressions.BossBarStyle;
import rublitio.uskaddon.expressions.BossBarTitle;
import rublitio.uskaddon.expressions.DropsOfBlock;
import rublitio.uskaddon.expressions.FlagsOfBossBar;
import rublitio.uskaddon.expressions.FreeMemory;
import rublitio.uskaddon.expressions.GetObjectInRam;
import rublitio.uskaddon.expressions.InventoryName;
import rublitio.uskaddon.expressions.JavaVersion;
import rublitio.uskaddon.expressions.JsonAppend;
import rublitio.uskaddon.expressions.JsonMessage;
import rublitio.uskaddon.expressions.JsonMessageCommand;
import rublitio.uskaddon.expressions.JsonMessageTooltip;
import rublitio.uskaddon.expressions.JsonMessageURL;
import rublitio.uskaddon.expressions.LoadedChunks;
import rublitio.uskaddon.expressions.MaxMemory;
import rublitio.uskaddon.expressions.MaxPlayers;
import rublitio.uskaddon.expressions.NewBossBar;
import rublitio.uskaddon.expressions.NewLine;
import rublitio.uskaddon.expressions.ParseIntFromString;
import rublitio.uskaddon.expressions.PingOfPlayer;
import rublitio.uskaddon.expressions.PlayersOfBossBar;
import rublitio.uskaddon.expressions.SerialiseBossBar;
import rublitio.uskaddon.expressions.SkullOwner;
import rublitio.uskaddon.expressions.TotalMemory;
import rublitio.uskaddon.utils.EnumRegister;
import rublitio.uskaddon.utils.JSONMessage;

public final class Main extends JavaPlugin {
	public static final HashMap<String, Object> objects = new HashMap<String, Object>();
	@Override
	public final void onEnable()
	{
		Skript.registerAddon(this);
		
		                           ////Events
		Skript.registerEvent("player changed world", PlayerChangeWorld.class, PlayerChangedWorldEvent.class, "player change[d] world");
		Skript.registerEvent("player swap hand items", PlayerSwapHandItem.class, PlayerSwapHandItemsEvent.class, "player swap hand [items]");
		Skript.registerEvent("player change main hand", PlayerChangeMainHand.class, PlayerChangedMainHandEvent.class, "player change[d] main hand");
		Skript.registerEvent("player chat tab complete", PlayerChatTabComplete.class, PlayerChatTabCompleteEvent.class, "player [chat] tab complete");
		Skript.registerEvent("player edit book", PlayerEditBook.class, PlayerEditBookEvent.class, "[player] (edit book|book edit)");
		Skript.registerEvent("player shear entity", PlayerShearEntity.class, PlayerShearEntityEvent.class, "player shear entity");
		Skript.registerEvent("player velocity", PlayerVelocity.class, PlayerVelocityEvent.class, "player [change] velocity");
		Skript.registerEvent("player armor stand manipulate", PlayerArmorStandManipulate.class, PlayerArmorStandManipulateEvent.class, "player armor stand manipulate");
		
		                           /////Effects
		Skript.registerEffect(SendTitle.class, "send title %string% [with subtitle %-string%] [for %-timespan%] [with %-timespan% fade in and %-timespan% fade out] to %player%");
		Skript.registerEffect(SendActionBar.class, "set action bar of %player% to %string%");
		Skript.registerEffect(LoadWorld.class, "load world %string%");
		Skript.registerEffect(UnloadWorld.class, "unload world %string%");
		Skript.registerEffect(SendJson.class, "send %jsonmessage% to %player%");
		Skript.registerEffect(SetKeepInventoryOnDeath.class, "set keep inventory to %boolean%");
		Skript.registerEffect(SetKeepXPOnDeath.class, "set keep (xp|level) to %boolean%");
		
		Skript.registerEffect(ShowBossBar.class, "show[ boss[ ]bar] %bossbar%");
		Skript.registerEffect(HideBossBar.class, "hide[ boss[ ]bar] %bossbar%");
		Skript.registerEffect(AddFlagToBar.class, "add flag %flag% to[ boss[ ]bar] %bossbar%");
		Skript.registerEffect(AddPlayerToBossBar.class, "add player %player% to [boss][ ][bar] %bossbar%");
		Skript.registerEffect(RemoveAllPlayersFromBossBar.class, "remove all players from [boss][ ][bar]%bossbar%");
		Skript.registerEffect(RemoveFlagFromBossBar.class, "remove flag %flag% from [boss][ ][bar] %bossbar%");
		Skript.registerEffect(RemovePlayerFromBossBar.class, "remove player %player% from [boss][ ][bar]%bossbar%");
		
		Skript.registerEffect(SaveObjectInRam.class, "save object %object% in ram[ with name] %string%");
		Skript.registerEffect(DeleteObjectInRam.class, new String[]{"delete object[ with name] %string%[ saved] in ram", "delete all object[s][ saved] in ram"});
		Skript.registerEffect(CreateFile.class, "create[ (a|the)] file %string%");
		Skript.registerEffect(DeleteFile.class, "delete[ the] file %string%");
		Skript.registerEffect(RequestGarbageCollector.class, "[request[ a] ]garbage collect[or]");
		Skript.registerEffect(CreateWorld.class, "create[ [a][ new]] world[ (with name|called|named)] %string%");
		Skript.registerEffect(DeleteWorld.class, "delete[ the] world[ (with name|called|named)] %string%");
		Skript.registerEffect(SetCursorItem.class, "set cursor item to %itemstack%");
		Skript.registerEffect(SetClickedItem.class, "set clicked item to %itemstack%");
		Skript.registerEffect(UnloadChunk.class, "unload chunk %chunk%");
		
		                          ////Expressions
		Skript.registerExpression(InventoryName.class, String.class, ExpressionType.SIMPLE, "name of[ inventory] %inventory%");
		Skript.registerExpression(JsonMessage.class, JSONMessage.class, ExpressionType.SIMPLE, "json[ of] %string%");
		Skript.registerExpression(JsonMessageCommand.class, JSONMessage.class, ExpressionType.SIMPLE, new String[]{"%jsonmessage% suggest %string%", "%jsonmessage% run %string%"});
		Skript.registerExpression(JsonMessageURL.class, JSONMessage.class, ExpressionType.SIMPLE, "%jsonmessage% open [url] %string%");
		Skript.registerExpression(JsonMessageTooltip.class, JSONMessage.class, ExpressionType.SIMPLE, "%jsonmessage% tooltip %string%");
		Skript.registerExpression(JsonAppend.class, JSONMessage.class, ExpressionType.SIMPLE, "%jsonmessage% (then|append) %string%");
		
		Skript.registerExpression(NewBossBar.class, BossBar.class, ExpressionType.SIMPLE, "new boss[ ]bar");
		Skript.registerExpression(BossBarColor.class, BarColor.class, ExpressionType.SIMPLE, "[boss][ ][bar] bar colo[u]r of %bossbar%");
		Skript.registerExpression(FlagsOfBossBar.class, BarFlag.class, ExpressionType.SIMPLE, "list of flags of [boss][ ][bar] %bossbar%");
		Skript.registerExpression(PlayersOfBossBar.class, Player.class, ExpressionType.SIMPLE, "list of players of [boss][ ][bar] %bossbar%");
		Skript.registerExpression(BossBarProgress.class, Number.class, ExpressionType.SIMPLE, "[boss][ ][bar] progress of %bossbar%");
		Skript.registerExpression(BossBarStyle.class, BarStyle.class, ExpressionType.SIMPLE, "[boss][ ][bar] bar style of %bossbar%");
		Skript.registerExpression(BossBarTitle.class, String.class, ExpressionType.SIMPLE, "[boss][ ][bar] title of %bossbar%");
		Skript.registerExpression(BossBarFromSerialised.class, BossBar.class, ExpressionType.SIMPLE, "boss[ ]bar from data %string%");
		Skript.registerExpression(SerialiseBossBar.class, String.class, ExpressionType.SIMPLE, "seriali(z|s)ed [(contents|data)][ of][ boss][ ][bar] %bossbar%");
		
		Skript.registerExpression(DropsOfBlock.class, ItemStack.class, ExpressionType.SIMPLE, new String[]{"drops of %block%", "drops of %block% with %itemstack%"});
		Skript.registerExpression(SkullOwner.class, String.class, ExpressionType.SIMPLE, "[the ]skull owner of %itemstack%");
		Skript.registerExpression(GetObjectInRam.class, Object.class, ExpressionType.SIMPLE, "[get] object[ with name] %string%[ saved] in ram");
		Skript.registerExpression(NewLine.class, String.class, ExpressionType.SIMPLE, "(new[ ]line|nl)");
		Skript.registerExpression(ParseIntFromString.class, Integer.class, ExpressionType.SIMPLE, "[parse[d] ]int[eger] from[ string] %string%");
		Skript.registerExpression(FreeMemory.class, Integer.class, ExpressionType.SIMPLE, "free memory");
		Skript.registerExpression(MaxMemory.class, Integer.class, ExpressionType.SIMPLE, "max[imum] memory");
		Skript.registerExpression(TotalMemory.class, Integer.class, ExpressionType.SIMPLE, "total memory");
		Skript.registerExpression(JavaVersion.class, String.class, ExpressionType.SIMPLE, "java version");
		Skript.registerExpression(PingOfPlayer.class, Integer.class, ExpressionType.SIMPLE, "ping of[ player] %player%");
		Skript.registerExpression(MaxPlayers.class, Integer.class, ExpressionType.SIMPLE, "max players[ count]");
		Skript.registerExpression(LoadedChunks.class, Chunk.class, ExpressionType.SIMPLE, new String[]{"([all ]loaded chunks|loaded chunks of all worlds)", "loaded chunks of[ world] %world%"});
		
		                          ////Conditions
		Skript.registerCondition(StringStartsWith.class, new String[]{"%string% start[s][ with] %string%", "%string% (doesn't|don't|not) start[s][ with] %string%"});
		Skript.registerCondition(StringEndsWith.class, new String[]{"%string% end[s][ with] %string%", "%string% (doesn't|don't|not) start[s][ with] %string%"});
		Skript.registerCondition(HasPotionEffect.class, new String[]{"[player ]%player% has[ potion] effect[ type][ of] %potioneffecttype%", "[player ]%player% has(n't| not)[ potion] effect[ type][ of] %potioneffecttype%"});
		Skript.registerCondition(FileExists.class, new String[]{"file %string% exist[s]", "file %string% (not|doesn't|don't) exist[s]"});
		
		                          ////Classes
		Classes.registerClass(new ClassInfo<JSONMessage>(JSONMessage.class, "jsonmessage"));
		Classes.registerClass(new ClassInfo<BossBar>(BossBar.class, "bossbar"));
		Classes.registerClass(new ClassInfo<ItemStack>(ItemStack.class, "item"));
		
		                         /////Enums
		EnumRegister.registerEnum(BarColor.class, "barcolor");
		EnumRegister.registerEnum(BarFlag.class, "flag");
		EnumRegister.registerEnum(BarStyle.class, "barstyle");
	}

}
