package draylar.battletowers.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import draylar.battletowers.BattleTowers;
import draylar.battletowers.group.ItemTab;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

// This code was taken from https://github.com/Lemonszz/gubbins/blob/master/src/main/java/party/lemons/gubbins/gui/ItemGroupTabWidget.java,
// which is licensed under MIT.
public class ItemGroupTabWidget extends ButtonWidget {

    public static final Identifier TEXTURE = BattleTowers.id("textures/gui/side_tab_test.png");

    private final ItemTab tab;
    public boolean isSelected = false;

    public ItemGroupTabWidget(int x, int y, ItemTab tab, PressAction onPress) {
        super(x, y, 39, 20, new TranslatableText(tab.getTranslationKey()), onPress);

        this.tab = tab;
    }

    @Override
    protected int getYImage(boolean isHovered) {
        return isHovered || isSelected ? 1 : 0;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        minecraftClient.getTextureManager().bindTexture(TEXTURE);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);

        this.drawTexture(matrixStack, this.x, this.y, 0, i * height, this.width, this.height);
        this.renderBackground(matrixStack, minecraftClient, mouseX, mouseY);

        minecraftClient.getItemRenderer().renderGuiItemIcon(tab.getIcon(), this.x + 15, this.y + 2);
    }
}