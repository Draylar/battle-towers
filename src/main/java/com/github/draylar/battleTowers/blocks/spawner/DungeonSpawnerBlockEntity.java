package com.github.draylar.battleTowers.blocks.spawner;

import com.github.draylar.battleTowers.BattleTowers;
import com.github.draylar.battleTowers.common.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sortme.MobSpawnerEntry;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DungeonSpawnerBlockEntity extends BlockEntity implements Tickable
{
    private DungeonSpawnerLogic logic  = new DungeonSpawnerLogic()
    {
        @Override
        public void method_8273(int int_1) {
            DungeonSpawnerBlockEntity.this.world.addBlockAction(DungeonSpawnerBlockEntity.this.pos, Blocks.dungeonSpawnerBlock, int_1, 0);
        }

        @Override
        public World getWorld() {
            return DungeonSpawnerBlockEntity.this.world;
        }


        @Override
        public BlockPos getPos() {
            return DungeonSpawnerBlockEntity.this.pos;
        }


        @Override
        public void setSpawnEntry(MobSpawnerEntry mobSpawnerEntry_1) {
            super.setSpawnEntry(mobSpawnerEntry_1);
            if (this.getWorld() != null) {
                BlockState blockState_1 = this.getWorld().getBlockState(this.getPos());
                this.getWorld().updateListeners(DungeonSpawnerBlockEntity.this.pos, blockState_1, blockState_1, 4);
            }
        }
    };

    public DungeonSpawnerBlockEntity()
    {
        super(BattleTowers.DUNGEON_SPAWNER);
        logic.setEntityType(EntityType.ZOMBIE);
    }



    public void setLogic(EntityType entityType, int initialSpawnDelay, int minSpawnDelay, int maxSpawnDelay, int spawnCount, int maxNearbyEntities, int requiredPlayerRange, int spawnrange, DefaultParticleType particleBackground, DefaultParticleType particleImportant)
    {
        logic.setEntityType(entityType);
        logic.spawnDelay = initialSpawnDelay;
        logic.minSpawnDelay = minSpawnDelay;
        logic.maxSpawnDelay = maxSpawnDelay;
        logic.spawnCount = spawnCount;
        logic.maxNearbyEntities = maxNearbyEntities;
        logic.requiredPlayerRange = requiredPlayerRange;
        logic.spawnRange = spawnrange;

        logic.particleBackground = particleBackground;
        logic.particleImportant = particleImportant;
    }


    @Override
    public void fromTag(CompoundTag compoundTag_1) {
        super.fromTag(compoundTag_1);
        this.logic.deserialize(compoundTag_1);
    }


    @Override
    public CompoundTag toTag(CompoundTag compoundTag_1) {
        super.toTag(compoundTag_1);
        this.logic.serialize(compoundTag_1);

        return compoundTag_1;
    }


    @Override
    public void tick() {
        this.logic.update();
    }





    @Override
    public CompoundTag toInitialChunkDataTag() {
        CompoundTag compoundTag_1 = this.toTag(new CompoundTag());
        compoundTag_1.remove("SpawnPotentials");
        return compoundTag_1;
    }


    @Override
    public boolean onBlockAction(int int_1, int int_2) {
        return this.logic.method_8275(int_1) || super.onBlockAction(int_1, int_2);
    }

    @Override
    public boolean shouldNotCopyTagFromItem() {
        return true;
    }


    public DungeonSpawnerLogic getLogic() {
        return this.logic;
    }
}
