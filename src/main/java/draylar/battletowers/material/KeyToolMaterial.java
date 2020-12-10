package draylar.battletowers.material;

import draylar.battletowers.registry.BattleTowerItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class KeyToolMaterial implements ToolMaterial {

    public static final KeyToolMaterial INSTANCE = new KeyToolMaterial();

    @Override
    public int getDurability() {
        return 1750;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 8.0F;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(BattleTowerItems.BOSS_KEY);
    }
}
