package spaceinvaders.command;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.HashMap;

import spaceinvaders.command.Command;

/**
 * Used for building commands.
 */
public abstract class CommandBuilder {
  private static final Logger LOGGER = Logger.getLogger(CommandBuilder.class.getName());

  private Map<String,Command> commandMap;
  private Command command;

  public CommandBuilder(Command... commands) {
    commandMap = new HashMap<>();
    for (Command command : commands) {
      commandMap.put(command.getName(),command);
    }
    commandMap = Collections.unmodifiableMap(commandMap);
  }

  /**
   * Build a command out of a JSON.
   */
  public void buildCommand(String json) {
    try {
      JsonParser parser = new JsonParser();
      JsonObject jsonObj = parser.parse(json).getAsJsonObject();
      String key = jsonObj.get("name").getAsString();
      Command value = commandMap.get(key);
      Gson gson = new Gson();
      command = gson.fromJson(json,value.getClass());
    } catch (Exception exception) {
      LOGGER.log(Level.SEVERE,exception.getMessage(),exception);
    }
  }

  /**
   * Get the built command.
   */
  public Command getCommand() {
    return command;
  }
}
