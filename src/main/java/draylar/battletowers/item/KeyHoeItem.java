package draylar.battletowers.item;

import draylar.battletowers.material.KeyToolMaterial;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class KeyHoeItem extends HoeItem {

    public KeyHoeItem(Settings settings) {
        super(KeyToolMaterial.INSTANCE, 3, -2.4f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
    }
}
