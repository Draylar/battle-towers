package draylar.battletowers.item;

import draylar.battletowers.material.KeyToolMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class KeyAxeItem extends AxeItem {

    public KeyAxeItem(Settings settings) {
        super(KeyToolMaterial.INSTANCE, 9, -3.0f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
    }
}
