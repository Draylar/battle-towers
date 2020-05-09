package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class BattleTowerTags {

    public static final Tag<Item> DEVELOPER_ITEMS = TagRegistry.item(BattleTowers.id("developer_items"));

    public static void init() {
        // NO-OP
    }

    private BattleTowerTags() {
        // NO-OP
    }
}
