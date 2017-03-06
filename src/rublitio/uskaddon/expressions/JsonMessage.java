package rublitio.uskaddon.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import rublitio.uskaddon.utils.JSONMessage;

public class JsonMessage extends SimplePropertyExpression<String, JSONMessage>
{
  protected String getPropertyName()
  {
    return "json equivalent";//JSON equivalent
  }

  public JSONMessage convert(String s)
  {
    return new JSONMessage(s);
  }

  public Class<? extends JSONMessage> getReturnType() {
    return JSONMessage.class;
  }
}