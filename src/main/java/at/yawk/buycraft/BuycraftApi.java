package at.yawk.buycraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author yawkat
 */
public interface BuycraftApi {
    /**
     * Set the online player count sent with every buycraft request. Numeric.
     */
    void setOnlinePlayers(String onlinePlayers);

    /**
     * @see #setOnlinePlayers(String)
     */
    default void setOnlinePlayers(int onlinePlayers) {
        setOnlinePlayers(Integer.toString(onlinePlayers));
    }

    /**
     * Send an info message to the buycraft server, with the given parameters. Also named "auth", even though it is not
     * a prerequisite for other requests.
     *
     * @param port        The minecraft server port. (numeric)
     * @param onlineMode  Whether the server is in online mode. (true/false)
     * @param playerLimit The player limit of the server. (numeric)
     * @param version     The version of this buycraft implementation. (string)
     */
    InfoResponse info(String port, String onlineMode, String playerLimit, String version) throws IOException;

    /**
     * @see #info(String, String, String, String)
     */
    default InfoResponse info(int port, boolean onlineMode, int playerLimit, String version) throws IOException {
        return info(Integer.toString(port), Boolean.toString(onlineMode), Integer.toString(playerLimit), version);
    }

    /**
     * Get the categories of the web shop.
     */
    Set<Category> categories() throws IOException;

    /**
     * Shorten the given web shop URL using buycraft's API.
     */
    String url(String url) throws IOException;

    /**
     * Get all packages in the web shop.
     */
    Set<Package> packages() throws IOException;

    /**
     * Get the latest payments in the shop.
     *
     * @param limit The maximum payment count. (numeric)
     * @param user  If set, only payments of that username will be returned. (string)
     */
    Set<Purchase> payments(String limit, Optional<String> user) throws IOException;

    /**
     * @see #payments(String, java.util.Optional)
     */
    default Set<Purchase> payments(int limit, Optional<String> user) throws IOException {
        return payments(Integer.toString(limit), user);
    }

    /**
     * Get the currently pending users.
     */
    PendingResponse pending() throws IOException;

    /**
     * Get the currently pending commands of the given players.
     *
     * @param players             The players we want to request commands for. (json array)
     * @param offlineCommands     Whether we want offline commands to be sent as well. (true/false)
     * @param offlineCommandLimit How many offline commands we want to receive at maximum. (numeric)
     */
    List<Command> commands(String players, String offlineCommands, String offlineCommandLimit) throws IOException;

    /**
     * @see #commands(String, String, String)
     */
    default List<Command> commands(Collection<String> players, boolean offlineCommands, int offlineCommandLimit)
            throws IOException {
        JsonArray array = new JsonArray();
        players.forEach(p -> array.add(new JsonPrimitive(p)));
        return commands(array.toString(), Boolean.toString(offlineCommands), Integer.toString(offlineCommandLimit));
    }

    /**
     * Remove the given commands from the pending command queue.
     *
     * @param commands The IDs of the commands we want removed. (json array of ints)
     */
    void removeCommands(String commands) throws IOException;

    /**
     * @see #removeCommands(String)
     */
    default void removeCommands(Collection<Integer> commandIds) throws IOException {
        JsonArray array = new JsonArray();
        commandIds.forEach(i -> array.add(new JsonPrimitive(i)));
        removeCommands(array.toString());
    }
}
