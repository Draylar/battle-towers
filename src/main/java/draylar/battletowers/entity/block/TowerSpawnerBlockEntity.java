package draylar.battletowers.entity.block;

import draylar.battletowers.world.TowerSpawnerLogic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerEntry;
import net.minecraft.world.World;

public class TowerSpawnerBlockEntity extends BlockEntity implements Tickable {

    private final TowerSpawnerLogic logic = new TowerSpawnerLogic() {
        public void sendStatus(int status) {
            TowerSpawnerBlockEntity.this.world.addSyncedBlockEvent(TowerSpawnerBlockEntity.this.pos, Blocks.SPAWNER, status, 0);
        }

        public World getWorld() {
            return TowerSpawnerBlockEntity.this.world;
        }

        public BlockPos getPos() {
            return TowerSpawnerBlockEntity.this.pos;
        }

        public void setSpawnEntry(MobSpawnerEntry spawnEntry) {
            super.setSpawnEntry(spawnEntry);
            if (this.getWorld() != null) {
                BlockState blockState = this.getWorld().getBlockState(this.getPos());
                this.getWorld().updateListeners(TowerSpawnerBlockEntity.this.pos, blockState, blockState, 4);
            }

        }
    };

    public TowerSpawnerBlockEntity() {
        super(BlockEntityType.MOB_SPAWNER);
    }

    @Override
    public void tick() {
        this.logic.update();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.logic.fromTag(tag);
    }
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        this.logic.toTag(tag);
        return tag;
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 1, this.toInitialChunkDataTag());
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        CompoundTag compoundTag = this.toTag(new CompoundTag());
        compoundTag.remove("SpawnPotentials");
        return compoundTag;
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        return this.logic.method_8275(type) || super.onSyncedBlockEvent(type, data);
    }

    @Override
    public boolean copyItemDataRequiresOperator() {
        return true;
    }
}