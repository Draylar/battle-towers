package draylar.battletowers.world;

import draylar.battletowers.block.ContentDeployerBlock;
import draylar.battletowers.entity.ContentDeployerBlockEntity;
import draylar.battletowers.registry.BattleTowerBlocks;
import draylar.battletowers.registry.BattleTowerStructures;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;

import java.util.Random;

public class BattleTowerPiece extends PoolStructurePiece {

    public BattleTowerPiece(StructureManager manager, StructurePoolElement poolElement, BlockPos pos, int groundLevelDelta, BlockRotation rotation, BlockBox boundingBox) {
        super(BattleTowerStructures.PIECE, manager, poolElement, pos, groundLevelDelta, rotation, boundingBox);
    }

    public BattleTowerPiece(StructureManager manager, CompoundTag tag) {
        super(manager, tag, BattleTowerStructures.PIECE);
    }

    @Override
    public boolean generate(ServerWorldAccess serverWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        boolean generate = super.generate(serverWorldAccess, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, pos);

        StructurePoolElement poolElement = this.getPoolElement();
        if (poolElement instanceof ExtendedSinglePoolElement) {
            ExtendedSinglePoolElement element = (ExtendedSinglePoolElement) poolElement;
            String location = element.location().toString();

            // place content deployer in non-entrance/top/bottom floor
            if(!location.contains("entrance") && !location.contains("top") && !location.contains("bottom")) {
                BlockPos deployerPos = new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ());
                serverWorldAccess.setBlockState(deployerPos, BattleTowerBlocks.CONTENT_DEPLOYER.getDefaultState(), 3);
                ContentDeployerBlockEntity contentDeployer = (ContentDeployerBlockEntity) serverWorldAccess.getBlockEntity(deployerPos);
                contentDeployer.setFloorID(new Identifier(location));
            }
        }

        return generate;
    }
}
