package ru.crystalbounty;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class BountyScreen extends Screen {
    private final BountyManager manager;
    private int spin;

    public BountyScreen(BountyManager manager) {
        super(Text.literal("Crystal Bounty"));
        this.manager = manager;
    }

    @Override
    protected void init() {
        int cx = width / 2;
        int y = height / 2 + 12;
        addDrawableChild(ButtonWidget.builder(Text.literal("Выбрать новую цель"), button -> {
            String target = manager.chooseTarget();
            if (target.isBlank()) {
                if (client != null && client.player != null) {
                    client.player.sendMessage(Text.literal("Целей для охоты сейчас нет"), false);
                }
            }
        }).dimensions(cx - 100, y, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal(manager.isDashUnlocked() ? "Dash уже куплен" : "Купить Dash — " + ClientConfig.dashCost + " очков"),
                button -> {
                    if (manager.buyDash()) {
                        button.setMessage(Text.literal("Dash куплен!"));
                    } else if (client != null && client.player != null) {
                        client.player.sendMessage(Text.literal("Недостаточно очков для покупки Dash"), false);
                    }
                }).dimensions(cx - 100, y + 25, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Закрыть"), button -> close())
                .dimensions(cx - 100, y + 50, 200, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        renderBackground(ctx, mouseX, mouseY, delta);
        int cx = width / 2;
        int top = height / 2 - 90;
        ctx.fill(cx - 125, top - 10, cx + 125, top + 135, 0xD0202638);
        ctx.drawBorder(cx - 125, top - 10, 250, 145, 0xFF66D9F0);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("✧  CRYSTAL BOUNTY  ✧"), cx, top, 0xB8F5FF);
        spin++;
        String[] crystals = {"◇", "◈", "◆", "◈"};
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal(crystals[(spin / 8) % crystals.length]), cx, top + 22, 0x8FEAFF);
        ctx.drawCenteredTextWithShadow(textRenderer,
                Text.literal("Цель: " + (manager.hasTarget() ? manager.getTarget() : "не выбрана")),
                cx, top + 48, 0xFFFFFFFF);
        ctx.drawCenteredTextWithShadow(textRenderer, Text.literal("Очки: " + manager.getPoints()),
                cx, top + 64, 0xFFFFE58A);
        ctx.drawCenteredTextWithShadow(textRenderer,
                Text.literal("B — меню | V — рывок"), cx, top + 82, 0xFFBBBBBB);
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
