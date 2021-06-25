package draylar.battletowers.world;

import draylar.battletowers.registry.BattleTowerWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class BattleTowerPiece extends PoolStructurePiece {

    public BattleTowerPiece(StructureManager manager, StructurePoolElement poolElement, BlockPos pos, int groundLevelDelta, BlockRotation rotation, BlockBox boundingBox) {
        super(manager, poolElement, pos, groundLevelDelta, rotation, boundingBox);
    }

    public BattleTowerPiece(ServerWorld world, NbtCompound tag) {
        super(world, tag);
    }

    @Override
    public StructurePieceType getType() {
        return BattleTowerWorld.PIECE;
    }
}
