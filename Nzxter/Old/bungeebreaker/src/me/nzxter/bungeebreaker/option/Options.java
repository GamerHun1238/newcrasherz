package me.nzxter.bungeebreaker.option;

import java.util.HashMap;
import java.util.Map;

public class Options {

  private final Map<String, Object> options = new HashMap<>();

  public boolean isOption(String id) {
    return this.options.containsKey(id);
  }

  public <T> T getOption(String id, T defaultValue) {
    return (T) this.options.getOrDefault(id, defaultValue);
  }

  public Map<String, Object> getOptions() {
    return options;
  }

  public static class Builder {
    public static Options of(String... args) {
      final Options options = new Options();
      for (String arg : args) {
        final String[] part = arg.split("=");
        if (part.length > 1) {
          Object value = part[1];//.replace("=", "");


          //autodetect of int
          try {
            value = Integer.parseInt((String)value);
          }
          catch (Exception ignored) {
          }

          if (value instanceof String && value.equals("true") || value.equals("false")) {
            //autodetect of boolean
            try {
              value = Boolean.parseBoolean((String) value);
            } catch (Exception ignored) {
            }
          }

          options.options.put(part[0].replace("=", ""), value);
        }
      }
      return options;
    }
  }

}
