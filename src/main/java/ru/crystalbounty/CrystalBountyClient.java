package ru.crystalbounty;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class CrystalBountyClient implements ClientModInitializer {
    public static final String MOD_ID = "crystalbounty";
    private static KeyBinding menuKey;
    private static KeyBinding dashKey;
    private static KeyBinding abilityKey;
    private static final BountyManager BOUNTIES = new BountyManager();
    private static long nextDashAt;
    private static boolean dashEnabled = true;

    @Override public void onInitializeClient() {
        ClientConfig.load();
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.crystalbounty.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "category.crystalbounty"));
        dashKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.crystalbounty.dash", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "category.crystalbounty"));
        abilityKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.crystalbounty.toggle_dash", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "category.crystalbounty"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) client.setScreen(new BountyScreen(BOUNTIES));
            while (abilityKey.wasPressed()) { dashEnabled = !dashEnabled; if (client.player != null) client.player.sendMessage(Text.literal("Crystal Bounty: Dash " + (dashEnabled ? "включён" : "выключен")), true); }
            while (dashKey.wasPressed()) performDash(client);
        });
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> BOUNTIES.onGameMessage(message.getString()));
    }

    private static void performDash(net.minecraft.client.MinecraftClient client) {
        if (!dashEnabled || client.player == null || client.world == null) return;
        long now = System.currentTimeMillis();
        if (now < nextDashAt) return;
        var player = client.player;
        var look = player.getRotationVec(1.0F);
        player.setVelocity(look.x * 1.35, Math.max(player.getVelocity().y, 0.12), look.z * 1.35);
        player.velocityModified = true;
        nextDashAt = now + 3000;
    }
}
