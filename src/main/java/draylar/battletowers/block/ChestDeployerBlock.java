package draylar.battletowers.block;

import draylar.battletowers.entity.ChestDeployerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ChestDeployerBlock extends Block implements BlockEntityProvider {

    public ChestDeployerBlock() {
        super(FabricBlockSettings.of(Material.METAL));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ChestDeployerBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
