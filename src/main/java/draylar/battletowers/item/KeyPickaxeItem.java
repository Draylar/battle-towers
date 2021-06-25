package draylar.battletowers.item;

import draylar.battletowers.material.KeyToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class KeyPickaxeItem extends PickaxeItem {

    public KeyPickaxeItem(Settings settings) {
        super(KeyToolMaterial.INSTANCE, 5, -2.8f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
    }
}
