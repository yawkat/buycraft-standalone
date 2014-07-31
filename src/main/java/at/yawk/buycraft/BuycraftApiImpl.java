package at.yawk.buycraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.xml.bind.DatatypeConverter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author yawkat
 */
class BuycraftApiImpl implements BuycraftApi {
    private final BuycraftClientImpl client;

    private final HttpClient httpClient;

    private String onlinePlayers = "0";

    public BuycraftApiImpl(BuycraftClientImpl client) {
        this.client = client;

        httpClient = HttpClientBuilder.create()
                .disableCookieManagement()
                .build();
    }

    @Override
    public void setOnlinePlayers(String onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    @Override
    public InfoResponse info(String port, String onlineMode, String playerLimit, String version) throws IOException {
        Objects.requireNonNull(port);
        Objects.requireNonNull(onlineMode);
        Objects.requireNonNull(playerLimit);
        Objects.requireNonNull(version);
        JsonObject object = get("info", new URIBuilder()
                .setParameter("serverPort", port)
                .setParameter("onlineMode", onlineMode)
                .setParameter("playersMax", playerLimit)
                .setParameter("version", version));
        JsonObject payload = object.getAsJsonObject("payload");
        return new InfoResponse(
                payload.get("latestVersion").getAsDouble(),
                payload.get("latestDownload").getAsString(),
                payload.get("serverId").getAsInt(),
                payload.get("serverCurrency").getAsString(),
                payload.get("serverName").getAsString(),
                payload.get("serverStore").getAsString(),
                payload.get("updateUsernameInterval").getAsInt(),
                payload.get("onlineMode").getAsBoolean()
        );
    }

    @Override
    public Set<Category> categories() throws IOException {
        JsonObject object = get("categories");
        Set<Category> categories = new HashSet<>();
        object.getAsJsonArray("payload").forEach(ele -> {
            JsonObject entry = ele.getAsJsonObject();
            Category category = new Category(
                    entry.get("id").getAsInt(),
                    entry.get("name").getAsString(),
                    entry.get("shortDescription").getAsString(),
                    entry.get("guiItemId").getAsInt(),
                    entry.get("itemId").getAsString()
            );
            categories.add(category);
        });
        return categories;
    }

    @Override
    public String url(String url) throws IOException {
        Objects.requireNonNull(url);
        return get("url", new URIBuilder().setParameter("url", url))
                .getAsJsonObject("payload")
                .get("url")
                .getAsString();
    }

    @Override
    public Set<Package> packages() throws IOException {
        JsonObject object = get("packages");
        Set<Package> packages = new HashSet<>();
        object.getAsJsonArray("payload").forEach(ele -> {
            JsonObject entry = ele.getAsJsonObject();
            Package pack = new Package(
                    entry.get("id").getAsInt(),
                    entry.get("order").getAsInt(),
                    entry.get("name").getAsString(),
                    entry.get("description").getAsString(),
                    entry.get("shortDescription").getAsString(),
                    entry.get("price").getAsString(),
                    entry.get("category").getAsInt(),
                    entry.get("guiItemId").getAsInt(),
                    entry.get("itemId").getAsString()
            );
            packages.add(pack);
        });
        return packages;
    }

    @Override
    public Set<Purchase> payments(String limit, Optional<String> user) throws IOException {
        URIBuilder builder = new URIBuilder();
        builder.setParameter("limit", limit);
        user.ifPresent(u -> builder.setParameter("ign", u));
        JsonObject object = get("payments", builder);
        Set<Purchase> purchases = new HashSet<>();
        object.getAsJsonArray("payload").forEach(ele -> {
            JsonObject entry = ele.getAsJsonObject();
            List<Integer> packages = new ArrayList<>();
            entry.getAsJsonArray("packages").forEach(pack -> packages.add(pack.getAsInt()));
            Purchase purchase = new Purchase(
                    entry.get("time").getAsLong(),
                    entry.get("humanTime").getAsString(),
                    entry.get("ign").getAsString(),
                    entry.get("uuid").getAsString(),
                    entry.get("price").getAsString(),
                    entry.get("currency").getAsString(),
                    Collections.unmodifiableCollection(packages)
            );
            purchases.add(purchase);
        });
        return purchases;
    }

    @Override
    public PendingResponse pending() throws IOException {
        JsonObject object = get("pendingUsers");
        List<String> pending = new ArrayList<>();
        JsonObject payload = object.getAsJsonObject("payload");
        payload.getAsJsonArray("pendingPlayers").forEach(ele -> pending.add(ele.getAsString()));
        return new PendingResponse(
                Collections.unmodifiableCollection(pending),
                payload.get("offlineCommands").getAsBoolean()
        );
    }

    @Override
    public List<Command> commands(String players, String offlineCommands, String offlineCommandLimit)
            throws IOException {
        JsonObject object = get("commands",
                                new URIBuilder()
                                        .setParameter("do", "lookup")
                                        .setParameter("users", players)
                                        .setParameter("offlineCommands", offlineCommands)
                                        .setParameter("offlineCommandLimit", offlineCommandLimit)
        );
        List<Command> commands = new ArrayList<>();
        object.getAsJsonObject("payload").getAsJsonArray("commands").forEach(ele -> {
            JsonObject entry = ele.getAsJsonObject();
            Command command = new Command(
                    entry.get("id").getAsInt(),
                    entry.get("ign").getAsString(),
                    entry.get("uuid").getAsString(),
                    entry.get("command").getAsString(),
                    entry.get("requireOnline").getAsBoolean(),
                    entry.get("requireInventorySlot").getAsInt(),
                    entry.get("delay").getAsInt()
            );
            commands.add(command);
        });
        return commands;
    }

    @Override
    public void removeCommands(String commands) throws IOException {
        get("commands", new URIBuilder().setParameter("do", "removeId").setParameter("commands", commands));
    }

    private JsonObject get(String action) throws IOException {
        return get(action, new URIBuilder());
    }

    private JsonObject get(String action, URIBuilder builder) throws IOException {
        builder.setScheme("http") // buycraft, fix your https cert
                .setHost("api.buycraft.net")
                .setPath("/v4")
                .setParameter("action", action)
                .setParameter("secret", DatatypeConverter.printHexBinary(client.getApiKey()))
                .setParameter("playersOnline", onlinePlayers);
        HttpGet get;
        try {
            get = new HttpGet(builder.build());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        HttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        try (InputStream stream = entity.getContent()) {
            // can't be bothered to parse HTTP encoding, UTF-8 should do the job
            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            return new JsonParser().parse(reader).getAsJsonObject();
        }
    }
}
