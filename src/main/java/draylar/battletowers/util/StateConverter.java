package draylar.battletowers.util;

import net.minecraft.block.BlockState;

public class StateConverter {

    public static BlockState copyFrom(BlockState propertyHolder, BlockState targetState) {
        if(propertyHolder.getBlock().equals(targetState.getBlock())) {

        }

        return targetState;
    }
}
