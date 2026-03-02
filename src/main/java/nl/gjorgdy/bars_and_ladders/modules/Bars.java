package nl.gjorgdy.bars_and_ladders.modules;

import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import nl.gjorgdy.bars_and_ladders.BarsAndLadders;

public class Bars {

    public static double getTargetVelocity() {
        return BarsAndLadders.targetSpeed;
    }

    public static double getVelocityModifier() {
        return BarsAndLadders.dragModifier;
    }

    public static void tick(Player player) {
        if (!player.isSpectator() && !player.onGround() && player.getDeltaMovement().y() < 0 && !player.isShiftKeyDown() && isPole(player)) {
            Vec3 v = player.getDeltaMovement();
            double newVerticalVelocity = v.y >= getTargetVelocity() ? v.y : v.y * getVelocityModifier();
            var newVelocity = new Vec3(v.x, newVerticalVelocity, v.z);
            if (player instanceof ServerPlayer serverPlayer)
                setVelocity(serverPlayer, newVelocity);
            if (newVerticalVelocity >= getTargetVelocity()) {
                player.fallDistance = 0;
            }
        }
    }

    public static boolean jump(Player player) {
        if (!player.isSpectator() && !player.onGround() && closeToTargetVelocity(player.getDeltaMovement()) && !player.isShiftKeyDown() && isPole(player)) {
            if (player instanceof ServerPlayer serverPlayer) {
                var newVelocity = serverPlayer.getDeltaMovement()
                                          .add(Vec3.directionFromRotation(0, serverPlayer.getYRot()).scale(0.3))
                                          .add(0, 0.5, 0);
                setVelocity(serverPlayer, newVelocity);
                return false;
            }
        }
        return true;
    }

    private static boolean closeToTargetVelocity(Vec3 velocity) {
        return Math.abs(velocity.y() - getTargetVelocity()) < 0.25;
    }

    private static void setVelocity(ServerPlayer player, Vec3 velocity) {
        player.setDeltaMovement(velocity);
        player.connection.send(new ClientboundSetEntityMotionPacket(player.getId(), velocity), null);
    }

    private static boolean isPole(Player player) {
        BlockState block = player.level().getBlockState(player.blockPosition());
        return block.is(Blocks.END_ROD)
                || (block.is(Blocks.IRON_BARS)
                || Blocks.COPPER_BARS.asList().contains(block.getBlock())
                && !block.isFaceSturdy(player.level(), player.blockPosition(), Direction.NORTH, SupportType.CENTER)
                && !block.isFaceSturdy(player.level(), player.blockPosition(), Direction.EAST, SupportType.CENTER)
                && !block.isFaceSturdy(player.level(), player.blockPosition(), Direction.SOUTH, SupportType.CENTER)
                && !block.isFaceSturdy(player.level(), player.blockPosition(), Direction.WEST, SupportType.CENTER));
    }

}
