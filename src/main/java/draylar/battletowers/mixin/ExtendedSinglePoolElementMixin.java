package draylar.battletowers.mixin;

import draylar.battletowers.api.Towers;
import draylar.battletowers.api.tower.Floor;
import draylar.battletowers.entity.block.ContentDeployerBlockEntity;
import draylar.battletowers.registry.BattleTowerBlocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import robosky.structurehelpers.structure.pool.ExtendedSinglePoolElement;

import java.util.Random;

@Mixin(ExtendedSinglePoolElement.class)
public abstract class ExtendedSinglePoolElementMixin {

    @Shadow public abstract Identifier location();

    @Inject(
            method = "generate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/StructureManager;getStructureOrBlank(Lnet/minecraft/util/Identifier;)Lnet/minecraft/structure/Structure;")
    )
    private void placeContentDeployer(StructureManager manager, StructureWorldAccess world, StructureAccessor accessor, ChunkGenerator generator, BlockPos pos, BlockPos pos2, BlockRotation rotation, BlockBox box, Random rand, boolean b, CallbackInfoReturnable<Boolean> cir) {
        // pos2 is the base position, so it accounts for the middle of the structure, but the y value stays at the bottom.
        // pos is the corner of each structure with the proper y value, but it is rotated.
        // we combine them to get the middle position of each floor

        if (this.location().toString().contains("battletowers")) {
            Floor floor = Towers.getFloor(this.location());

            if (floor != null) {
                BlockPos deployerPos = new BlockPos(pos2.getX(), pos.getY() + 1, pos2.getZ());

                // some rooms call generate twice, so check for stacking content deployers
                if (!world.getBlockState(deployerPos.down()).getBlock().equals(BattleTowerBlocks.CONTENT_DEPLOYER)) {
                    if (floor.placeContentDeployer()) {
                        world.setBlockState(deployerPos, BattleTowerBlocks.CONTENT_DEPLOYER.getDefaultState(), 3);
                        ContentDeployerBlockEntity contentDeployer = (ContentDeployerBlockEntity) world.getBlockEntity(deployerPos);

                        // apply floor data to content deployer
                        if (contentDeployer != null) {
                            contentDeployer.apply(floor);
                        }
                    }
                }
            }
        }
    }
}
