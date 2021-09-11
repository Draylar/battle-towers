package draylar.battletowers.item;

import draylar.battletowers.material.KeyToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class KeySwordItem extends SwordItem {

    public KeySwordItem(Settings settings) {
        super(KeyToolMaterial.INSTANCE, 8, -2.4f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
    }
}
