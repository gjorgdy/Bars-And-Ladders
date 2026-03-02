package nl.gjorgdy.bars_and_ladders.modules;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import nl.gjorgdy.bars_and_ladders.BarsAndLadders;
import nl.gjorgdy.bars_and_ladders.utils.ItemUtils;

public class Ladders {

    public static boolean lower(Player player, ItemStack stack, Level world, BlockPos pos) {
        for (int i = 0; i < BarsAndLadders.ladderReach; i++) {
            BlockPos _pos = pos.below(i);
            BlockState _block = world.getBlockState(_pos);
            if (_block.is(Blocks.AIR) || _block.is(Blocks.WATER)) {
                if (ItemUtils.place(stack, player, _pos, SoundEvents.LADDER_PLACE)) {
                    return true;
                } else {
                    break;
                }
            } else if (!_block.is(Blocks.LADDER)) break;
        }
        return false;
    }

    public static boolean canBeSupported(BlockState blockState, LevelReader world, BlockPos pos) {
        BlockState upperBlock = world.getBlockState(pos.above());
        if (!blockState.is(Blocks.LADDER) || !upperBlock.is(Blocks.LADDER)) return false;
        return blockState.getValue(LadderBlock.FACING) == upperBlock.getValue(LadderBlock.FACING);
    }

    public static boolean isSupported(LevelReader world, BlockPos pos) {
        BlockState block = world.getBlockState(pos);
        return canBeSupported(block, world, pos);
    }

    public static void updateLadder(LevelReader world, BlockPos pos) {
        if (world instanceof LevelAccessor worldAccess) {
            BlockState block = world.getBlockState(pos);
            if (block.is(Blocks.LADDER)) {
                Direction _facing = block.getValue(LadderBlock.FACING);
                BlockPos _facingPos = pos.relative(_facing.getOpposite());
                if (!(world.getBlockState(_facingPos).isRedstoneConductor(world, _facingPos) || isSupported(world, pos))) {
                    worldAccess.destroyBlock(pos, true);
                    updateLadder(world, pos.below());
                }
            }
        }
    }

}
