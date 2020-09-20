package draylar.battletowers.entity.block;

import draylar.battletowers.api.Towers;
import draylar.battletowers.api.spawner.MobSpawnerAccessor;
import draylar.battletowers.api.spawner.MobSpawnerEntryBuilder;
import draylar.battletowers.api.tower.Floor;
import draylar.battletowers.registry.BattleTowerBlocks;
import draylar.battletowers.registry.BattleTowerEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ContentDeployerBlockEntity extends BlockEntity implements Tickable {

    private final static int RADIUS = 8;
    private final static int MAX_CHESTS = 2;
    private final static int MAX_SPAWNERS = 2;
    private final static List<BlockPos> CIRCULAR_POSITIONS = new ArrayList<>();

    static {
        // add all positions in a 7 block circle to our circular positions list for chest placement

        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                double distance = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));

                // check that block is within radius
                if(distance <= RADIUS) {

                    // don't allow chests at 0,0 (results in chest floating above deployer)
                    if (x != 0 || z != 0) {
                        CIRCULAR_POSITIONS.add(new BlockPos(x, 0, z));
                    }
                }
            }
        }
    }

    private Identifier floorID = new Identifier("minecraft", "empty");
    private boolean placeLadders = false;
    private boolean placeChests = false;
    private boolean placeSpawners = false;
    private boolean placeBossLock = false;

    private int delay = 0;

    public ContentDeployerBlockEntity() {
        super(BattleTowerEntities.CONTENT_DEPLOYER);
    }

    public void apply(Floor floor) {
        this.floorID = floor.getId();
        this.placeLadders = floor.placeLadders();
        this.placeChests = floor.placeChests();
        this.placeSpawners = floor.placeSpawners();
        this.placeBossLock = floor.placeBossLock();
    }

    @Override
    public void tick() {
         if(world != null && !world.isClient) {
             // wait 1 second before placing to try to fix issue where content doesn't appear
             if(delay <= 20) {
                 delay++;
             } else {
                 if (placeChests) {
                     placeChests();
                 }

                 if (placeSpawners) {
                     placeSpawners();
                 }

                 if (placeLadders) {
                     placeLadders();
                 }

                 // replace this content deployer with a boss lock or air
                 if (placeBossLock) {
                     placeBossLock();
                 } else {
                     world.setBlockState(pos, Blocks.AIR.getDefaultState());
                     this.markRemoved();
                 }
             }
        }
    }

    private void placeBossLock() {
        world.setBlockState(pos, BattleTowerBlocks.BOSS_LOCK.getDefaultState(), 3);
    }

    private void placeLadders() {
        // place ladder
        List<Direction> randomDirections = Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() != Direction.Axis.Y).collect(Collectors.toList());
        Collections.shuffle(randomDirections);
        BlockPos originPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

        // check y0 and y1 in case we're in a pool or something
        for(int y = 0; y <= 1; y++) {

            // check from 8 out (wall) to 2 in (to avoid content deployer)
            for (int i = 8; i >= 2; i--) {
                for (Direction direction : randomDirections) {
                    BlockPos wallPos = originPos.offset(direction, i).up(y);
                    BlockState wallState = world.getBlockState(wallPos);
                    BlockPos innerPos = originPos.offset(direction, i - 1).up(y);
                    BlockState innerState = world.getBlockState(innerPos);

                    if (wallState.isSideSolidFullSquare(world, wallPos, direction.getOpposite()) && innerState.isAir()) {
                        world.setBlockState(innerPos, BattleTowerBlocks.LADDER_DEPLOYER.getDefaultState(), 3);
                        return;
                    }
                }
            }
        }
    }

    private void placeChests() {
        ArrayList<BlockPos> clonedPositions = new ArrayList<>(CIRCULAR_POSITIONS);
        Collections.shuffle(clonedPositions);

        int placedChests = 0;

        // go over each position to find chest spot
        for(BlockPos clonedPos : clonedPositions) {

            if(placedChests >= MAX_CHESTS) {
                return;
            }

            // vertical
            for (int y = 0; y < 7; y++) {
                BlockPos checkPos = pos.add(clonedPos).up(y);
                BlockState checkState = world.getBlockState(checkPos);
                BlockState underState = world.getBlockState(checkPos.down());

                if(checkState.isAir() && underState.isSolidBlock(world, checkPos.down())) {
                    world.setBlockState(checkPos, Blocks.CHEST.getDefaultState());

                    // get chest and set loot table
                    ChestBlockEntity chestBlockEntity = (ChestBlockEntity) world.getBlockEntity(checkPos);
                    chestBlockEntity.setLootTable(Towers.getLootTableFor(floorID), world.getRandom().nextInt(1000));

                    placedChests++;
                    break;
                }
            }
        }

        // couldn't place all chests, remove
        System.out.println("[Battle Towers] Couldn't place all chests in a layer. Was the tower overriden?");
    }

    private void placeSpawners() {
        ArrayList<BlockPos> clonedPositions = new ArrayList<>(CIRCULAR_POSITIONS);
        Collections.shuffle(clonedPositions);

        int placedSpawners = 0;

        // go over each position to find spawner spot
        for(BlockPos clonedPos : clonedPositions) {

            if(placedSpawners >= MAX_SPAWNERS) {
                return;
            }

            // vertical
            for (int y = 0; y < 7; y++) {
                BlockPos checkPos = pos.add(clonedPos).up(y);
                BlockState checkState = world.getBlockState(checkPos);
                BlockState underState = world.getBlockState(checkPos.down());

                if(checkState.isAir() && underState.isSolidBlock(world, checkPos.down())) {
                    world.setBlockState(checkPos, Blocks.SPAWNER.getDefaultState());

                    // get spawner and set spawn entry
                    MobSpawnerBlockEntity mobSpawner = (MobSpawnerBlockEntity) world.getBlockEntity(checkPos);
                    ((MobSpawnerAccessor) mobSpawner).setTowerSpawner(true);
                    mobSpawner.getLogic().setSpawnEntry(new MobSpawnerEntryBuilder(Towers.getSpawnerEntryFor(floorID)).build());

                    placedSpawners++;
                    break;
                }
            }
        }

        // couldn't place all spawner, remove
        System.out.println("[Battle Towers] Couldn't place all spawners in a layer. Was the tower overriden?");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("FloorID", floorID.toString());
        tag.putBoolean("PlaceChests", placeChests);
        tag.putBoolean("PlaceSpawners", placeSpawners);
        tag.putBoolean("PlaceLadders", placeLadders);
        tag.putInt("Delay", delay);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        this.floorID = new Identifier(tag.getString("FloorID"));
        this.placeChests = tag.getBoolean("PlaceChests");
        this.placeSpawners = tag.getBoolean("PlaceSpawners");
        this.placeLadders = tag.getBoolean("PlaceLadders");
        this.delay = tag.getInt("Delay");
        super.fromTag(state, tag);
    }
}
