package nl.gjorgdy.bars_and_ladders.modules;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import nl.gjorgdy.bars_and_ladders.utils.PlayerUtils;

public class Chains {

	public static void tick(Player player) {
		if (!player.isSpectator() && !player.onGround() && player.isShiftKeyDown() && isChain(player) && player instanceof ServerPlayer serverPlayer) {
			var v = player.getDeltaMovement();
			if (v.y < 0) {
				PlayerUtils.setVelocity(serverPlayer, v.multiply(1, 0, 1));
				player.causeFallDamage(player.fallDistance, 0.5f, player.damageSources().fall());
				player.fallDistance = 0;
			}
		}
	}

	public static boolean isChain(Player player) {
		BlockState block = player.level().getBlockState(player.blockPosition());
		return (block.is(Blocks.IRON_CHAIN)
				|| Blocks.COPPER_CHAIN.asList().contains(block.getBlock()))
				&& block.getValue(RotatedPillarBlock.AXIS).equals(Direction.Axis.Y); // is vertical chain
	}

}
