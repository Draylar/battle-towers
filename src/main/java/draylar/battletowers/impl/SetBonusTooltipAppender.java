package draylar.battletowers.impl;

import draylar.battletowers.item.KeyArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

@Environment(EnvType.CLIENT)
public class SetBonusTooltipAppender {

    public static void appendKeyTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        if (world.isClient) {
            for (ItemStack armor : player.getInventory().armor) {
                if (!(armor.getItem() instanceof KeyArmorItem)) {
                    return;
                }
            }
        }

        tooltip.add(new LiteralText(" "));
        tooltip.add(new TranslatableText("battletowers.set_bonus").formatted(Formatting.GOLD));
        tooltip.add(new LiteralText(" ").append(new TranslatableText("battletowers.key_bonus_1").formatted(Formatting.GRAY)));
        tooltip.add(new LiteralText(" ").append(new TranslatableText("battletowers.key_bonus_2").formatted(Formatting.GRAY)));
        tooltip.add(new LiteralText(" ").append(new TranslatableText("battletowers.key_bonus_3").formatted(Formatting.GRAY)));
    }
}
