package ru.crystalbounty;

import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.nio.file.Path;
import java.util.Properties;

public final class ClientConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("crystalbounty.properties");
    public static int dashCost = 5;
    private ClientConfig() {}
    public static void load() {
        Properties p = new Properties();
        if (FILE.toFile().exists()) try (Reader r = new FileReader(FILE.toFile())) { p.load(r); dashCost = Integer.parseInt(p.getProperty("dashCost", "5")); } catch (Exception ignored) {}
        save();
    }
    public static void save() {
        Properties p = new Properties(); p.setProperty("dashCost", Integer.toString(dashCost));
        try (Writer w = new FileWriter(FILE.toFile())) { p.store(w, "Crystal Bounty client settings"); } catch (IOException ignored) {}
    }
}
