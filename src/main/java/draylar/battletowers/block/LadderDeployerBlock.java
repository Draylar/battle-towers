package draylar.battletowers.block;

import draylar.battletowers.entity.LadderDeployerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

/**
 * Responsible for carrying a {@link LadderDeployerBlockEntity}, which deploys ladders vertically after a tower has been generated.
 */
public class LadderDeployerBlock extends Block implements BlockEntityProvider {

    public LadderDeployerBlock() {
        super(FabricBlockSettings.of(Material.METAL));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new LadderDeployerBlockEntity();
    }
}
