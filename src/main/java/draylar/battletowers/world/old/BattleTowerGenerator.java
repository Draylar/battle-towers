package draylar.battletowers.world.old;

import draylar.battletowers.BattleTowers;
import draylar.battletowers.registry.BattleTowerStructures;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.List;
import java.util.Random;

public class BattleTowerGenerator {

    private static final Identifier LAYER = new Identifier("battletowers:tower_normal/tower_normal_layer");
    private static final Identifier ENTRANCE = new Identifier("battletowers:tower_normal/tower_normal_entrance");
    private static final Identifier TOP = new Identifier("battletowers:tower_normal/tower_normal_top");
    private static final Identifier BLACKSMITH = new Identifier("battletowers:tower_normal/tower_normal_blacksmith");
    private static final Identifier LIBRARY = new Identifier("battletowers:tower_normal/tower_normal_library");
    private static final Identifier LOOKOUT = new Identifier("battletowers:tower_normal/tower_normal_lookout");
    private static final Identifier JUNGLE = new Identifier("battletowers:tower_normal/tower_normal_jungle");
    private static final Identifier MINE = new Identifier("battletowers:tower_normal/tower_normal_mine");

    private static final Identifier baseLoot = new Identifier(BattleTowers.CONFIG.entranceLootTable);
    private static final Identifier blacksmithLoot = new Identifier(BattleTowers.CONFIG.blacksmithLootTable);
    private static final Identifier jungleLoot = new Identifier(BattleTowers.CONFIG.jungleLootTable);
    private static final Identifier layerLoot = new Identifier(BattleTowers.CONFIG.layerLootTable);
    private static final Identifier libraryLoot = new Identifier(BattleTowers.CONFIG.libraryLootTable);
    private static final Identifier mineLoot = new Identifier(BattleTowers.CONFIG.mineLootTable);


    public static void addParts(StructureManager structureManager, BlockPos blockPos, BlockRotation rotation, List<StructurePiece> pieceList, Random random, DefaultFeatureConfig featureConfig) {
        boolean hasAddedLookout = false;

        // spawn in base of tower
        pieceList.add(new BattleTowerGenerator.Piece(structureManager, ENTRANCE, blockPos, rotation, false));

        int lastRandom = -1;

        // randomly add layers
        for (int i = 0; i < BattleTowers.CONFIG.floorAmount + random.nextInt(BattleTowers.CONFIG.floorRandomAddition); i++) {
            int randomLayerIndex = getUniqueRandom(random, lastRandom, 6);

            hasAddedLookout = placeLayer(structureManager, blockPos, rotation, pieceList, hasAddedLookout, i, randomLayerIndex, i % 2 == 0);
            lastRandom = randomLayerIndex;
        }


        // finish with the top
        pieceList.add(new BattleTowerGenerator.Piece(structureManager, TOP, blockPos, rotation, false));
    }

    private static boolean placeLayer(StructureManager structureManager, BlockPos blockPos, BlockRotation rotation, List<StructurePiece> pieceList, boolean hasAddedLookout, int floorLevel, int layerIndex, boolean ladderSide) {
        // standard layer
        if (layerIndex == 0) {
            pieceList.add(new Piece(structureManager, LAYER, blockPos, rotation, ladderSide));
        }

        // blacksmith
        else if (layerIndex == 1) {
            pieceList.add(new Piece(structureManager, BLACKSMITH, blockPos, rotation, ladderSide));
        }

        // jungle
        else if (layerIndex == 2) {
            pieceList.add(new Piece(structureManager, JUNGLE, blockPos, rotation, ladderSide));
        }

        // mine
        else if (layerIndex == 3) {
            pieceList.add(new Piece(structureManager, MINE, blockPos, rotation, ladderSide));
        }

        // library
        else if (layerIndex == 4) {
            pieceList.add(new Piece(structureManager, LIBRARY, blockPos, rotation, ladderSide));
        }

        // lookout
        else if (layerIndex == 5 && !hasAddedLookout && floorLevel > 5) {
            pieceList.add(new Piece(structureManager, LOOKOUT, blockPos, rotation, ladderSide));
            hasAddedLookout = true;
        }

        // default to layer
        else pieceList.add(new Piece(structureManager, LAYER, blockPos, rotation, ladderSide));

        return hasAddedLookout;
    }

    private static int getUniqueRandom(Random random, int lastRandom, int randomMax) {
        int randomChance;

        do {
            randomChance = random.nextInt(randomMax);
        } while (lastRandom == randomChance);

        return randomChance;
    }

    public static class Piece extends SimpleStructurePiece {
        private final Identifier template;
        private final BlockRotation rotation;
        private boolean ladderSide;

        public Piece(StructureManager structureManager, Identifier identifier, BlockPos blockPos, BlockRotation rotation, boolean ladderSide) {
            super(BattleTowerStructures.structurePieceType, 0);

            this.template = identifier;
            this.rotation = rotation;
            this.pos = blockPos;
            this.ladderSide = ladderSide;

            this.initializePlacementData(structureManager);
        }

        public Piece(StructureManager structureManager, CompoundTag compoundTag) {
            super(BattleTowerStructures.structurePieceType, compoundTag);

            this.template = new Identifier(compoundTag.getString("Template"));
            this.rotation = BlockRotation.valueOf(compoundTag.getString("Rot"));

            this.initializePlacementData(structureManager);
        }


        private void initializePlacementData(StructureManager structureManager_1) {
            Structure structure_1 = structureManager_1.getStructureOrBlank(this.template);
            StructurePlacementData structurePlacementData_1 = (new StructurePlacementData()).setRotation(this.rotation).setMirrored(BlockMirror.NONE).setPosition(pos).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure_1, this.pos, structurePlacementData_1);
        }

        @Override
        protected void toNbt(CompoundTag compoundTag_1) {
            super.toNbt(compoundTag_1);
            compoundTag_1.putString("Template", this.template.toString());
            compoundTag_1.putString("Rot", this.rotation.name());
        }

        @Override
        protected void handleMetadata(String s, BlockPos pos, IWorld world, Random random, BlockBox boundingBox) {
            if (s.contains("normal_blacksmith_furnace")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());
            } else if (s.contains("normal_blacksmith_barrel")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof BarrelBlockEntity) {
                    ((BarrelBlockEntity) blockEntity).setLootTable(blacksmithLoot, random.nextLong());
                }
            }

            if (s.contains("normal_chest")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(baseLoot, random.nextLong());
                }
            }

            if (s.contains("normal_chest2")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(baseLoot, random.nextLong());
                }
            }


            if (s.contains("normal_library_chest")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(libraryLoot, random.nextLong());
                }
            }

            if (s.contains("normal_layer_chest")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(layerLoot, random.nextLong());
                }
            }


            if (s.contains("jungle_chest1")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(jungleLoot, random.nextLong());
                }
            }

            if (s.contains("jungle_chest2")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof ChestBlockEntity) {
                    ((ChestBlockEntity) blockEntity).setLootTable(jungleLoot, random.nextLong());
                }
            }

            if (s.contains("mine_barrel1")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof BarrelBlockEntity) {
                    ((BarrelBlockEntity) blockEntity).setLootTable(mineLoot, random.nextLong());
                }
            }

            if (s.contains("mine_barrel2")) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
                BlockEntity blockEntity = world.getBlockEntity(pos.down());

                if (blockEntity instanceof BarrelBlockEntity) {
                    ((BarrelBlockEntity) blockEntity).setLootTable(mineLoot, random.nextLong());
                }
            }
        }

        @Override
        public boolean generate(IWorld world, ChunkGenerator<?> generator, Random random, BlockBox box, ChunkPos pos) {
            this.placementData.addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);

            int yLevel = 0;

            for (int i = 255; i > 0; i--) {
                if (world.getBlockState(new BlockPos(pos.getStartX() + 7, i + this.pos.getY(), pos.getStartZ())).getBlock() != Blocks.AIR) {
                    yLevel = i;
                    break;
                }
            }

            if (template == BattleTowerGenerator.ENTRANCE) {
                yLevel = yLevel - 5;
            }

            this.pos = new BlockPos(this.pos.getX(), yLevel, this.pos.getZ());

            boolean success = super.generate(world, generator, random, box, pos);

            if (template == TOP) {
                if (ladderSide) {
                    world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel, this.pos.getZ() + 14), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH), 0);
                    world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel, this.pos.getZ() + 14), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH), 0);

                    world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel + 1, this.pos.getZ() + 1), Blocks.SPRUCE_LEAVES.getDefaultState(), 0);
                    world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel + 1, this.pos.getZ() + 1), Blocks.SPRUCE_LEAVES.getDefaultState(), 0);

                } else {
                    world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel, this.pos.getZ() + 1), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.SOUTH), 0);
                    world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel, this.pos.getZ() + 1), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.SOUTH), 0);

                    world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel + 1, this.pos.getZ() + 1), Blocks.SPRUCE_LEAVES.getDefaultState(), 0);
                    world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel + 1, this.pos.getZ() + 1), Blocks.SPRUCE_LEAVES.getDefaultState(), 0);
                }
            } else if (template != ENTRANCE) {
                if (ladderSide) {
                    for (int i = 1; i < 6; i++) {
                        world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel + i, this.pos.getZ() + 1), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.SOUTH), 0);
                        world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel + i, this.pos.getZ() + 1), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.SOUTH), 0);
                    }

                    world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel, this.pos.getZ() + 14), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH), 0);
                    world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel, this.pos.getZ() + 14), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH), 0);
                } else {
                    for (int i = 1; i < 6; i++) {
                        world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel + i, this.pos.getZ() + 14), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH), 0);
                        world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel + i, this.pos.getZ() + 14), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH), 0);
                    }

                    world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel, this.pos.getZ() + 1), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.SOUTH), 0);
                    world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel, this.pos.getZ() + 1), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.SOUTH), 0);
                }
            } else {
                if (ladderSide) {
                    for (int i = 1; i < 6; i++) {
                        world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel + i + 5, this.pos.getZ() + 1), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.SOUTH), 0);
                        world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel + i + 5, this.pos.getZ() + 1), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.SOUTH), 0);
                    }
                } else {
                    for (int i = 1; i < 6; i++) {
                        world.setBlockState(new BlockPos(this.pos.getX() + 7, yLevel + i + 5, this.pos.getZ() + 14), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH), 0);
                        world.setBlockState(new BlockPos(this.pos.getX() + 8, yLevel + i + 5, this.pos.getZ() + 14), Blocks.LADDER.getDefaultState().with(HorizontalFacingBlock.FACING, Direction.NORTH), 0);
                    }
                }
            }


            return success;
        }
    }
}
