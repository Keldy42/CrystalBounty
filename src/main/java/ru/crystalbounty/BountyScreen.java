package ru.crystalbounty;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class BountyScreen extends Screen {
    private final BountyManager manager;
    private int spin;
    public BountyScreen(BountyManager manager) { super(Text.literal("Crystal Bounty")); this.manager = manager; }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        int cx = width / 2, top = height / 2 - 75;
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("✧  CRYSTAL BOUNTY  ✧"), cx, top, 0xB8F5FF);
        spin++;
        String[] crystals = {"◇", "◈", "◆", "◈"};
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal(crystals[(spin / 8) % crystals.length]), cx, top + 24, 0x8FEAFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("Цель: " + (manager.hasTarget() ? manager.getTarget() : "не выбрана")), cx, top + 54, 0xFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("Очки: " + manager.getPoints()), cx, top + 70, 0xFFE58A);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("B — меню | V — рывок | Dash стоит 5 очков"), cx, top + 96, 0xBBBBBB);
        super.render(ctx, mouseX, mouseY, delta);
    }
    @Override public boolean shouldPause() { return false; }
    @Override public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { close(); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
