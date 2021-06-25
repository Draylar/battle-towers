package draylar.battletowers.item;

import com.google.common.collect.Lists;
import draylar.battletowers.api.Towers;
import draylar.battletowers.api.tower.Tower;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;
import java.util.Random;

public class ForeignKeyItem extends Item {

    public ForeignKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user.hasPermissionLevel(2)) {
            HitResult raycast = user.raycast(100, 0, true);
            if (raycast instanceof BlockHitResult bhr) {

                // Ensure we found a non-air block
                if (!world.getBlockState(bhr.getBlockPos()).isAir()) {
                    BlockPos up = bhr.getBlockPos().up();

                    generate(up, (ServerWorld) world, 12, false);
                }
            }
        }

        return super.use(world, user, hand);
    }

    public void generate(BlockPos pos, ServerWorld world, int maxDepth, boolean keepJigsaws) {
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        StructureManager structureManager = world.getStructureManager();
        StructureAccessor structureAccessor = world.getStructureAccessor();
        Random random = world.getRandom();
        List<PoolStructurePiece> list = Lists.newArrayList();

        Tower tower = Towers.getTowerFor(world.getBiome(pos));
        StructurePoolElement randomElement = tower.getStartPool().getRandomElement(random);
        PoolStructurePiece poolStructurePiece = new PoolStructurePiece(structureManager, randomElement, pos.add(-8, -1, -8), 0, BlockRotation.NONE, BlockBox.create(pos, pos));

        list.add(poolStructurePiece);
        StructurePoolBasedGenerator.method_27230(world.getRegistryManager(), poolStructurePiece, maxDepth, PoolStructurePiece::new, chunkGenerator, structureManager, list, random, world);


        for (PoolStructurePiece poolStructurePiece2 : list) {
            poolStructurePiece2.generate(world, structureAccessor, chunkGenerator, random, BlockBox.empty(), pos, keepJigsaws);
        }
    }

    @Override
    public Text getName(ItemStack stack) {
        return ((TranslatableText) super.getName(stack)).formatted(Formatting.DARK_RED);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(LiteralText.EMPTY);
        tooltip.add(new LiteralText("A key from a foreign dimension.").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("Use to spawn a Battle Tower at the position.").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("you are looking at.").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("  Operator Only").formatted(Formatting.DARK_GRAY));
    }
}
