package draylar.battletowers.block;

import draylar.battletowers.entity.block.LadderDeployerBlockEntity;
import draylar.battletowers.registry.BattleTowerEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Responsible for carrying a {@link LadderDeployerBlockEntity}, which deploys ladders vertically after a tower has been generated.
 */
public class LadderDeployerBlock extends BlockWithEntity {

    public LadderDeployerBlock() {
        super(FabricBlockSettings.of(Material.METAL));
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LadderDeployerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BattleTowerEntities.LADDER_DEPLOYER, LadderDeployerBlockEntity::tick);
    }
}
