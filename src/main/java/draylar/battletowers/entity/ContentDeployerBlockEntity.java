package draylar.battletowers.entity;

import draylar.battletowers.api.MobSpawnerAccessor;
import draylar.battletowers.api.Towers;
import draylar.battletowers.api.spawner.MobSpawnerEntryBuilder;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public ContentDeployerBlockEntity() {
        super(BattleTowerEntities.CONTENT_DEPLOYER);
    }

    public void setFloorID(Identifier floorID) {
        this.floorID = floorID;
    }

    @Override
    public void tick() {
        if(world != null && !world.isClient) {
            placeChests();
            placeSpawners();
        }
    }

    private void placeChests() {
        ArrayList<BlockPos> clonedPositions = new ArrayList<>(CIRCULAR_POSITIONS);
        Collections.shuffle(clonedPositions);

        int placedChests = 0;

        // go over each position to find chest spot
        for(BlockPos clonedPos : clonedPositions) {

            if(placedChests >= MAX_CHESTS) {
                // remove this chest placer after we hit max chests
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                this.markRemoved();
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
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        System.out.println("[Battle Towers] Couldn't place all chests in a layer. Was the tower overriden?");
    }

    private void placeSpawners() {
        ArrayList<BlockPos> clonedPositions = new ArrayList<>(CIRCULAR_POSITIONS);
        Collections.shuffle(clonedPositions);

        int placedSpawners = 0;

        // go over each position to find spawner spot
        for(BlockPos clonedPos : clonedPositions) {

            if(placedSpawners >= MAX_SPAWNERS) {
                // remove this chest placer after we hit max chests
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                this.markRemoved();
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
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
        System.out.println("[Battle Towers] Couldn't place all spawners in a layer. Was the tower overriden?");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putString("FloorID", floorID.toString());
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        this.floorID = new Identifier(tag.getString("FloorID"));
        super.fromTag(state, tag);
    }
}
