package rublitio.uskaddon.utils;

import java.util.HashMap;
import java.util.Map.Entry;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class EnumClassInfo<E extends Enum<E>>
{
  private final Class<E> enumType;
  private final String codeName;
  @SuppressWarnings("rawtypes")
private final EventValueExpression defaultExpression;
  private final ClassInfo<E> classInfo;
  private final HashMap<String, String> synonyms = new HashMap<String, String>();

  @SuppressWarnings({ "unchecked", "rawtypes" })
private EnumClassInfo(Class<E> enumType, String codeName)
  {
    this.enumType = enumType;
    this.codeName = codeName;
    this.defaultExpression = new EventValueExpression(enumType);
    this.classInfo = new ClassInfo(enumType, codeName);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
private EnumClassInfo(Class<E> enumType, String codeName, EventValueExpression<E> defaultExpression)
  {
    this.enumType = enumType;
    this.codeName = codeName;
    this.defaultExpression = defaultExpression;
    this.classInfo = new ClassInfo(enumType, codeName);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
public static <E extends Enum<E>> EnumClassInfo<E> create(Class<E> enumType, String codeName)
  {
    return new EnumClassInfo(enumType, codeName);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
public static <E extends Enum<E>> EnumClassInfo<E> create(Class<E> enumType, String codeName, EventValueExpression<E> defaultExpression)
  {
    return new EnumClassInfo(enumType, codeName, defaultExpression);
  }

  public EnumClassInfo<E> addSynonym(String regex, String actualValue)
  {
    this.synonyms.put(regex, actualValue);
    return this;
  }

  public EnumClassInfo<E> after(String[] after)
  {
    this.classInfo.after(after);
    return this;
  }

  public EnumClassInfo<E> before(String[] before)
  {
    this.classInfo.before(before);
    return this;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
public void register()
  {
    Classes.registerClass(this.classInfo.user(new String[] { this.codeName + "s?" }).parser(new Parser()
    {
      public E parse(String s, ParseContext parseContext)
      {
        if (s.startsWith(EnumClassInfo.this.codeName + ":")) {
          s = s.substring(EnumClassInfo.this.codeName.length() + 1, s.length());
        }
        try
        {
          for (Entry p : EnumClassInfo.this.synonyms.entrySet()) {
            if (s.matches((String)p.getKey())) {
              return Enum.valueOf(EnumClassInfo.this.enumType, (String)p.getValue());
            }
          }
          return Enum.valueOf(EnumClassInfo.this.enumType, s.replace(" ", "_").toUpperCase().trim());
        } catch (IllegalArgumentException e) {
        }
        return null;
      }
      
      @SuppressWarnings("unused")
	public String toString(E e, int i)
      {
        return e.toString();
      }

      @SuppressWarnings("unused")
	public String toVariableNameString(E e)
      {
        return EnumClassInfo.this.codeName + ':' + e.toString();
      }

      public String getVariableNamePattern()
      {
        return EnumClassInfo.this.codeName + ":.+";
      }

	@Override
	public String toString(Object arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toVariableNameString(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

    }).serializer(new EnumSerializer(this.enumType)).defaultExpression(this.defaultExpression));
  }
}