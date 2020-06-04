package draylar.battletowers.block;

import draylar.battletowers.entity.ContentDeployerBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class ContentDeployerBlock extends Block implements BlockEntityProvider {

    public ContentDeployerBlock() {
        super(FabricBlockSettings.of(Material.METAL));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ContentDeployerBlockEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
