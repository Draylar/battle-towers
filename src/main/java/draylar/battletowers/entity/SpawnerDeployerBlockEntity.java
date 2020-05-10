package draylar.battletowers.entity;

import draylar.battletowers.block.ChestDeployerBlock;
import draylar.battletowers.registry.BattleTowerEntities;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.MobSpawnerEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnerDeployerBlockEntity extends BlockEntity implements Tickable {

    private final static int RADIUS = 8;
    private final static int MAX_SPAWNERS = 3;
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

    private final List<MobSpawnerEntry> potentialSpawnerEntries = new ArrayList<>();

    public SpawnerDeployerBlockEntity() {
        super(BattleTowerEntities.SPAWNER_DEPLOYER);
    }

    @Override
    public void tick() {
        if(world != null && !world.isClient) {
            // add a spawn entry if the random list is empty
            if(potentialSpawnerEntries.isEmpty()) {
                CompoundTag spawnerTag = new CompoundTag();
                spawnerTag.putString("id", "minecraft:zombie");

                potentialSpawnerEntries.add(new MobSpawnerEntry(spawnerTag));
            }

            ArrayList<BlockPos> clonedPositions = new ArrayList<>(CIRCULAR_POSITIONS);
            Collections.shuffle(clonedPositions);

            int placedSpawners = 0;

            // go over each position to find chest spot
            for(BlockPos clonedPos : clonedPositions) {

                if(placedSpawners >= MAX_SPAWNERS) {
                    // remove this chest placer after we hit max chests
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    this.markRemoved();
                    return;
                }

                // if the block underneath this is a chest deployer, do logic 1 block down
                int start = 0;
                if(world.getBlockState(pos.down()).getBlock() instanceof ChestDeployerBlock) {
                    start = -1;
                }

                // vertical
                for (int y = start; y < 7 + start; y++) {
                    BlockPos checkPos = pos.add(clonedPos).up(y);
                    BlockState checkState = world.getBlockState(checkPos);
                    BlockState underState = world.getBlockState(checkPos.down());

                    if(checkState.isAir() && underState.isSolidBlock(world, checkPos.down())) {
                        world.setBlockState(checkPos, Blocks.SPAWNER.getDefaultState());

                        // get chest and set loot table
                        MobSpawnerBlockEntity spawnerBlockEntity = (MobSpawnerBlockEntity) world.getBlockEntity(checkPos);
                        spawnerBlockEntity.getLogic().setSpawnEntry(potentialSpawnerEntries.get(world.random.nextInt(potentialSpawnerEntries.size())));

                        placedSpawners++;
                        break;
                    }
                }
            }

            // couldn't place all chests, remove
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            System.out.println("[Battle Towers] Couldn't place all spawners in a layer. Was the tower overriden?");
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        ListTag lootList = new ListTag();

        potentialSpawnerEntries.forEach(spawnEntry -> {
            lootList.add(spawnEntry.serialize());
        });

        tag.put("Entries", lootList);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        ListTag lootList = tag.getList("Entries", NbtType.COMPOUND);

        lootList.forEach(compound -> {
            potentialSpawnerEntries.add(new MobSpawnerEntry((CompoundTag) compound));
        });

        super.fromTag(state, tag);
    }
}
