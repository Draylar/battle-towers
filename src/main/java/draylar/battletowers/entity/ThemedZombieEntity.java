package draylar.battletowers.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class ThemedZombieEntity extends ZombieEntity {

    public ThemedZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }
}
