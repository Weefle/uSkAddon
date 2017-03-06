package rublitio.uskaddon.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Collect
{
  public static String[] toStringArray(Object[] array)
  {
    String[] strings = new String[array.length];
    for (int i = 0; i < strings.length; i++) strings[i] = array[i].toString();
    return strings;
  }

  public static String[] toFriendlyStringArray(Object[] array) {
    String[] strings = new String[array.length];
    for (int i = 0; i < strings.length; i++) strings[i] = array[i].toString().toLowerCase().replace("_", " ");
    return strings;
  }

  public static <T> T[] asArray(T[] objects) {
    return objects;
  }

  @SuppressWarnings("unchecked")
public static <T> T[] newArray(Class<?> type, int size)
  {
    return (T[])Array.newInstance(type, size);
  }

  public static String[] asSkriptProperty(String property, String fromType) {
    return (String[])asArray(new String[] { new StringBuilder().append("[the] ").append(property).append(" of %").append(fromType).append("%").toString(), new StringBuilder().append("%").append(fromType).append("%'[s] ").append(property).toString() });
  }

  public static <T> String toString(T[] array) {
    return toString(array, ',');
  }

  public static <T> String toString(T[] array, char separator) {
    return toString(array, separator, true);
  }

  public static <T> String toString(T[] array, char separator, boolean spaces)
  {
    String SEPARATOR = spaces ? " " : "";
    if (array == null)
      return "null";
    int max = array.length - 1;
    if (max == -1)
      return "";
    String b = "";
    for (int i = 0; ; i++) {
      b = new StringBuilder().append(b).append(String.valueOf(array[i])).toString();
      if (i == max)
        return b;
      b = new StringBuilder().append(b).append(separator).append(SEPARATOR).toString();
    }
  }

  public static String getPaths(Collection<String> list) {
    StringBuilder builder = new StringBuilder();
    for (String s : list) {
      builder.append(s).append(".");
    }
    return builder.toString().substring(0, builder.length() - 1);
  }

  @SuppressWarnings("resource")
public static String textPart(InputStream is)
  {
    if (is == null) return "";
    Scanner s = new Scanner(is).useDelimiter("\\A"); Throwable localThrowable2 = null;
    try { return s.hasNext() ? s.next() : ""; }
    catch (Throwable localThrowable3)
    {
      localThrowable2 = localThrowable3; throw localThrowable3;
    } finally {
      if (s != null) if (localThrowable2 != null) try { s.close(); } catch (Throwable x2) { localThrowable2.addSuppressed(x2); } else s.close();  
    }
  }

  private static ArrayList<File> getListFiles(File root, FilenameFilter filter, ArrayList<File> toAdd)
  {
    for (File f : root.listFiles(filter)) {
      if (f.isDirectory()) return getListFiles(f, filter, toAdd);
      toAdd.add(f);
    }
    return toAdd;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
public static File[] getFiles(File root, FilenameFilter filter) {
    ArrayList files = getListFiles(root, filter, new ArrayList());
    return (File[])files.toArray(new File[files.size()]);
  }

  public static File[] getFiles(File root) {
    return getFiles(root, null);
  }
}