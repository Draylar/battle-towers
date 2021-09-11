package draylar.battletowers.item;

import draylar.battletowers.material.KeyToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class KeyShovelItem extends ShovelItem {

    public KeyShovelItem(Settings settings) {
        super(KeyToolMaterial.INSTANCE, 4, -3.0f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
    }
}
