package draylar.battletowers.registry;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.status.CustomStatusEffect;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.registry.Registry;

public class BattleTowerStatusEffects {

    public static final StatusEffect GUARDIAN_CONQUEROR = register("guardian_conqueror", new CustomStatusEffect(StatusEffectType.BENEFICIAL, 0xfca503))
            .addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    "91AEAA56-376B-4498-935B-2F7F68070635",
                    0.1D,
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL
            )
            .addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_SPEED,
                    "91AEAA56-376B-4498-935B-2F7F68070635",
                    0.1D,
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL
            )
            .addAttributeModifier(
                    EntityAttributes.GENERIC_ARMOR,
                    "91AEAA56-376B-4498-935B-2F7F68070635",
                    2D,
                    EntityAttributeModifier.Operation.ADDITION
            );

    private static StatusEffect register(String id, StatusEffect entry) {
        return Registry.register(Registry.STATUS_EFFECT, BattleTowers.id(id), entry);
    }

    public static void init() {

    }

    private BattleTowerStatusEffects() {

    }
}
