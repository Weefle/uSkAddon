/*
 * EffCreateFile.class - Made by nfell2009
 * http://umbaska.co.uk (C) nfell2009 | 2014 - 2015
 * Submitted to: Umbaska
 * 
*/

package rublitio.uskaddon.effects;

import java.io.File;
import java.io.IOException;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

public class CreateFile extends Effect {

    private Expression<String> file;

    @Override
    protected void execute(Event event){
    	final File f = new File(file.getSingle(event));
        if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }


    @Override
    public String toString(Event event, boolean b){
        return "create a file";
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult){
        this.file = (Expression<String>) expressions[0];
        return true;
    }
}