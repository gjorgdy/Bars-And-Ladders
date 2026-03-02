package nl.gjorgdy.bars_and_ladders.listeners;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nl.gjorgdy.bars_and_ladders.modules.Ladders;
import org.jetbrains.annotations.Nullable;

public class PlayerBlockBreakListener implements PlayerBlockBreakEvents.After {

    @Override
    public void afterBlockBreak(Level world, Player playerEntity, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {
        Ladders.updateLadder(world, blockPos.below());
    }
}
