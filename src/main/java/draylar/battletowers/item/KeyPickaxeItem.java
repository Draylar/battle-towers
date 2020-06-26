package draylar.battletowers.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class KeyPickaxeItem extends PickaxeItem {

    public KeyPickaxeItem(Settings settings) {
        super(ToolMaterials.GOLD, 3, -2.4f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
    }
}
