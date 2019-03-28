package com.github.draylar.battleTowers.common.world;

import com.github.draylar.battleTowers.common.Structures;
import com.github.draylar.battleTowers.config.ConfigHolder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.ChunkPos;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.List;
import java.util.Random;

public class BattleTowerGenerator
{
    public static final Identifier towerLayer = new Identifier("battle-towers:tower_normal/tower_normal_layer");
    public static final Identifier towerBase = new Identifier("battle-towers:tower_normal/tower_normal_entrance");
    public static final Identifier towerTop = new Identifier("battle-towers:tower_normal/tower_normal_top");
    public static final Identifier towerBlacksmith = new Identifier("battle-towers:tower_normal/tower_normal_blacksmith");
    public static final Identifier towerLibrary = new Identifier("battle-towers:tower_normal/tower_normal_library");
    public static final Identifier towerLookout = new Identifier("battle-towers:tower_normal/tower_normal_lookout");
    public static final Identifier towerJungle = new Identifier("battle-towers:tower_normal/tower_normal_jungle");
    public static final Identifier towerMine = new Identifier("battle-towers:tower_normal/tower_normal_mine");

    public static final Identifier baseLoot = ConfigHolder.configInstance.entranceLootTable;
    public static final Identifier blacksmithLoot = ConfigHolder.configInstance.blacksmithLootTable;
    public static final Identifier jungleLoot = ConfigHolder.configInstance.jungleLootTable;
    public static final Identifier layerLoot = ConfigHolder.configInstance.layerLootTable;
    public static final Identifier libraryLoot = ConfigHolder.configInstance.libraryLootTable;
    public static final Identifier mineLoot = ConfigHolder.configInstance.mineLootTable;


    public static void addParts(StructureManager structureManager_1, BlockPos blockPos_1, Rotation rotation_1, List<StructurePiece> list_1, Random random_1, DefaultFeatureConfig featureConfig)
    {
        boolean hasAddedLookout = false;


        // base of tower
        list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerBase, blockPos_1, rotation_1, 0));

        int lastRandomChance = -1;

        // randomly add layers
        for(int i = 0; i < ConfigHolder.configInstance.floorAmount + random_1.nextInt(ConfigHolder.configInstance.floorRandomAddition); i++)
        {
            int randomChance;

            do randomChance = random_1.nextInt(6);
            while (lastRandomChance == randomChance);

            if(randomChance == 0) list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerLayer, blockPos_1, rotation_1, 0));
            else if (randomChance == 1) list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerBlacksmith, blockPos_1, rotation_1, 0));
            else if (randomChance == 2) list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerJungle, blockPos_1, rotation_1, 0));
            else if (randomChance == 3) list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerMine, blockPos_1, rotation_1, 0));
            else if (randomChance == 4) list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerLibrary, blockPos_1, rotation_1, 0));

            else if (randomChance == 5 && !hasAddedLookout && i > 5)
            {
                list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerLookout, blockPos_1, rotation_1, 0));
                hasAddedLookout = true;
            }

            else list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerLayer, blockPos_1, rotation_1, 0));

            lastRandomChance = randomChance;
        }


        // finish with the top
        list_1.add(new BattleTowerGenerator.Piece(structureManager_1, towerTop, blockPos_1, rotation_1, 0));
    }

    public static class Piece extends SimpleStructurePiece
    {
        private final Identifier template;
        private final Rotation rotation;

        public Piece(StructureManager structureManager, Identifier identifier, BlockPos blockPos, Rotation rotation, int offset)
        {
            super(Structures.structurePieceType, 0);

            this.template = identifier;
            this.rotation = rotation;
            this.pos = blockPos;

            this.initializePlacementData(structureManager);
        }

        public Piece(StructureManager structureManager_1, CompoundTag compoundTag_1) {
            super(Structures.structurePieceType, compoundTag_1);

            this.template = new Identifier(compoundTag_1.getString("Template"));
            this.rotation = Rotation.valueOf(compoundTag_1.getString("Rot"));

            this.initializePlacementData(structureManager_1);
        }

        private void initializePlacementData(StructureManager structureManager_1)
        {
            Structure structure_1 = structureManager_1.getStructureOrBlank(this.template);
            StructurePlacementData structurePlacementData_1 = (new StructurePlacementData()).setRotation(this.rotation).setMirrored(Mirror.NONE).setPosition(pos).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure_1, this.pos, structurePlacementData_1);
        }

        @Override
        protected void toNbt(CompoundTag compoundTag_1)
        {
            super.toNbt(compoundTag_1);
            compoundTag_1.putString("Template", this.template.toString());
            compoundTag_1.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleMetadata(String s, BlockPos blockPos, IWorld iWorld, Random random, MutableIntBoundingBox mutableIntBoundingBox)
        {
            if (s.contains("normal_blacksmith_furnace"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());
            }

            else if (s.contains("normal_blacksmith_barrel"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof BarrelBlockEntity)
                {
                    ((BarrelBlockEntity) blockEntity).setLootTable(blacksmithLoot, random.nextLong());
                }
            }

            if (s.contains("normal_chest"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof ChestBlockEntity)
                {
                    ((ChestBlockEntity) blockEntity).setLootTable(baseLoot, random.nextLong());
                }
            }

            if (s.contains("normal_chest2"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof ChestBlockEntity)
                {
                    ((ChestBlockEntity) blockEntity).setLootTable(baseLoot, random.nextLong());
                }
            }



            if (s.contains("normal_library_chest"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof ChestBlockEntity)
                {
                    ((ChestBlockEntity) blockEntity).setLootTable(libraryLoot, random.nextLong());
                }
            }

            if (s.contains("normal_layer_chest"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof ChestBlockEntity)
                {
                    ((ChestBlockEntity) blockEntity).setLootTable(layerLoot, random.nextLong());
                }
            }


            if (s.contains("jungle_chest1"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof ChestBlockEntity)
                {
                    ((ChestBlockEntity) blockEntity).setLootTable(jungleLoot, random.nextLong());
                }
            }

            if (s.contains("jungle_chest2"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof ChestBlockEntity)
                {
                    ((ChestBlockEntity) blockEntity).setLootTable(jungleLoot, random.nextLong());
                }
            }

            if (s.contains("mine_barrel1"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof BarrelBlockEntity)
                {
                    ((BarrelBlockEntity) blockEntity).setLootTable(mineLoot, random.nextLong());
                }
            }

            if (s.contains("mine_barrel2"))
            {
                iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = iWorld.getBlockEntity(blockPos.down());

                if (blockEntity instanceof BarrelBlockEntity)
                {
                    ((BarrelBlockEntity) blockEntity).setLootTable(mineLoot, random.nextLong());
                }
            }
        }

        @Override
        public boolean generate(IWorld world, Random random_1, MutableIntBoundingBox mutableIntBoundingBox_1, ChunkPos chunkPos_1)
        {
            int yLevel = 0;

            for(int i = 255; i > 0; i--)
            {
                if(world.getBlockState(new BlockPos(chunkPos_1.getStartX() + 7, i + pos.getY(), chunkPos_1.getStartZ())).getBlock() != Blocks.AIR)
                {
                    yLevel = i;
                    break;
                }
            }

            if(template == BattleTowerGenerator.towerBase)
            {
                yLevel = yLevel - 5;
            }

            this.pos = new BlockPos(this.pos.getX(), yLevel, this.pos.getZ());

           return super.generate(world, random_1, mutableIntBoundingBox_1, chunkPos_1);
        }
    }
}
