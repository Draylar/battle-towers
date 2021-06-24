package draylar.battletowers.group;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.List;

// This code was taken from https://github.com/Lemonszz/gubbins/blob/master/src/main/java/party/lemons/gubbins/itemgroup/TabbedItemGroup.java,
// which is licensed under MIT.
public abstract class TabbedItemGroup extends ItemGroup {

    private int selectedTab = 0;
    private final List<ItemTab> tabs = Lists.newArrayList();
    private boolean hasInitialized = false;

    protected TabbedItemGroup(Identifier id) {
        super(createTabIndex(), String.format("%s.%s", id.getNamespace(), id.getPath()));
    }

    private static int createTabIndex() {
        ((ItemGroupExtensions) ItemGroup.BUILDING_BLOCKS).fabric_expandArray();
        return ItemGroup.GROUPS.length - 1;
    }

    public void initialize() {
        hasInitialized = true;
        tabs.add(createAllTab());
        initTabs(tabs);
    }

    protected abstract void initTabs(List<ItemTab> tabs);

    @Override
    public void appendStacks(DefaultedList<ItemStack> stacks) {
        for (Item item : Registry.ITEM) {
            if (getSelectedTab().matches(item))
                item.appendStacks(this, stacks);
        }
    }

    protected ItemTab createAllTab() {
        return new ItemTab(getIcon(), "all", null);
    }

    public ItemTab getSelectedTab() {
        return tabs.get(selectedTab);
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    public List<ItemTab> getTabs() {
        return tabs;
    }

    public int getSelectedTabIndex() {
        return selectedTab;
    }

    public boolean hasInitialized() {
        return hasInitialized;
    }
}
