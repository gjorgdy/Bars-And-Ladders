package nl.gjorgdy.bars_and_ladders.modules;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import nl.gjorgdy.bars_and_ladders.utils.PlayerUtils;

public class Jumping {

	public static void jump(Player player) {
		if (!player.isSpectator() && !player.onGround() &&
				(closeToTargetVelocity(player.getDeltaMovement()) && Bars.isSlidingDownPole(player))
				|| (Chains.isChain(player) && player.isShiftKeyDown())
		) {
			if (player instanceof ServerPlayer serverPlayer) {
				var newVelocity = Vec3.directionFromRotation(0, serverPlayer.getYRot()).scale(0.3);
				PlayerUtils.setVelocity(serverPlayer, new Vec3(
					newVelocity.x(),
					0.05,
					newVelocity.z()
				));
			}
		}
	}

	private static boolean closeToTargetVelocity(Vec3 velocity) {
		return Math.abs(velocity.y() - Bars.getTargetVelocity()) < 0.25;
	}

}
