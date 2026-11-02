package ru.crystalbounty;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import java.util.*;

public final class BountyManager {
    private String target = "";
    private int points = 0;
    private final Random random = new Random();
    public String getTarget() { return target; }
    public int getPoints() { return points; }
    public boolean hasTarget() { return !target.isBlank(); }
    public String chooseTarget() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getNetworkHandler() == null || client.player == null) return target = "";
        String self = client.player.getGameProfile().getName();
        List<String> candidates = new ArrayList<>();
        for (PlayerListEntry entry : client.getNetworkHandler().getPlayerList()) {
            String name = entry.getProfile().getName();
            if (!name.equalsIgnoreCase(self)) candidates.add(name);
        }
        target = candidates.isEmpty() ? "" : candidates.get(random.nextInt(candidates.size()));
        return target;
    }
    public boolean buyDash() { if (points < ClientConfig.dashCost) return false; points -= ClientConfig.dashCost; return true; }
    public void onGameMessage(String raw) {
        if (target.isBlank()) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        String msg = raw.toLowerCase(Locale.ROOT);
        if (msg.contains(target.toLowerCase(Locale.ROOT)) && msg.contains(client.player.getGameProfile().getName().toLowerCase(Locale.ROOT)) && (msg.contains("убил") || msg.contains("killed") || msg.contains("slain"))) {
            points++; target = "";
        }
    }
}
