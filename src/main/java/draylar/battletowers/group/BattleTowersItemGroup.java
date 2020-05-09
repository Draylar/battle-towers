package draylar.battletowers.group;

import draylar.battletowers.registry.BattleTowerItems;
import draylar.battletowers.registry.BattleTowerTags;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import java.util.List;

// This code was taken from https://github.com/Lemonszz/gubbins/blob/master/src/main/java/party/lemons/gubbins/mixin/client/CreativeInventoryScreenMixin.java,
// which is licensed under MIT.
public class BattleTowersItemGroup extends TabbedItemGroup {

    public BattleTowersItemGroup(Identifier id) {
        super(id);
    }

    @Override
    public void initTabs(List<ItemTab> tabs) {
        tabs.add(new ItemTab(new ItemStack(Items.DARK_OAK_DOOR), "developer_items", BattleTowerTags.DEVELOPER_ITEMS));
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(BattleTowerItems.BOSS_KEY);
    }
}