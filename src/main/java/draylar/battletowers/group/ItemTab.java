package draylar.battletowers.group;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;

// This code was taken from https://github.com/Lemonszz/gubbins/blob/master/src/main/java/party/lemons/gubbins/itemgroup/ItemTab.java,
// which is licensed under MIT.
public record ItemTab(ItemStack icon, String name, Tag<Item> itemTag) {



    public boolean matches(Item item) {
        return itemTag == null || itemTag.contains(item);
    }


    public String getTranslationKey() {
        return "subtab.battletowers." + name;
    }
}