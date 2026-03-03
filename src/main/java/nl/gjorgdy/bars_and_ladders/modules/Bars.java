package nl.gjorgdy.bars_and_ladders.modules;

import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import nl.gjorgdy.bars_and_ladders.BarsAndLadders;
import nl.gjorgdy.bars_and_ladders.utils.PlayerUtils;

public class Bars {

    public static double getTargetVelocity() {
        return BarsAndLadders.targetSpeed;
    }

    public static double getVelocityMultiplier() {
        return BarsAndLadders.dragModifier;
    }

    public static void tick(Player player) {
        if (!player.isSpectator() && !player.onGround() && isSlidingDownPole(player)) {
            Vec3 v = player.getDeltaMovement();
            var multiplier = getVelocityMultiplier() * (player.isCrouching() ? 0.75 : 1);
            double newVerticalVelocity = v.y >= getTargetVelocity() ? v.y : v.y * multiplier;
            var newVelocity = new Vec3(v.x, newVerticalVelocity, v.z);
            if (player instanceof ServerPlayer serverPlayer)
                PlayerUtils.setVelocity(serverPlayer, newVelocity);
            if (newVerticalVelocity >= getTargetVelocity()) {
                player.fallDistance = 0;
            }
        }
    }

    public static boolean isSlidingDownPole(Player player) {
        BlockState block = player.level().getBlockState(player.blockPosition());
        return player.getDeltaMovement().y() < 0
                && (block.is(Blocks.END_ROD)
                || (block.is(Blocks.IRON_BARS)
                || Blocks.COPPER_BARS.asList().contains(block.getBlock())
                && !block.isFaceSturdy(player.level(), player.blockPosition(), Direction.NORTH, SupportType.CENTER)
                && !block.isFaceSturdy(player.level(), player.blockPosition(), Direction.EAST, SupportType.CENTER)
                && !block.isFaceSturdy(player.level(), player.blockPosition(), Direction.SOUTH, SupportType.CENTER)
                && !block.isFaceSturdy(player.level(), player.blockPosition(), Direction.WEST, SupportType.CENTER)));
    }

}
