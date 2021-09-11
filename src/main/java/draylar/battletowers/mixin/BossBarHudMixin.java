package draylar.battletowers.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import draylar.battletowers.BattleTowers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public abstract class BossBarHudMixin extends DrawableHelper {

    private static final Identifier CUSTOM_BAR_TEX = BattleTowers.id("textures/gui/bars.png");
    @Shadow
    @Final
    private static Identifier BARS_TEXTURE;
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(
            method = "renderBossBar",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderCustomBossBar(MatrixStack matrixStack, int i, int j, BossBar bossBar, CallbackInfo ci) {
        // Prevent issues with non-translatable text boss bar titles
        if (!(bossBar.getName() instanceof TranslatableText)) {
            return;
        }

        if (bossBar instanceof ClientBossBar && ((TranslatableText) bossBar.getName()).getKey().contains("tower_guard")) {
            RenderSystem.setShaderTexture(0, CUSTOM_BAR_TEX);

            // draw empty background bar
            this.drawTexture(matrixStack, i, j, 0, 2, 185, 9);

            // percentage -> texture width
            int overlayBarWidth = (int) (bossBar.getPercent() * 185.0F);

            // draw overlay
            this.drawTexture(matrixStack, i, j, 0, 24, overlayBarWidth, 9);

            ci.cancel();
        }

        this.client.getTextureManager().bindTexture(BARS_TEXTURE);
    }
}
