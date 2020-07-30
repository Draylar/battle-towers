package draylar.battletowers.entity.themed;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;

public class ThemedSpiderEntity extends SpiderEntity {

    public ThemedSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
    }
}
