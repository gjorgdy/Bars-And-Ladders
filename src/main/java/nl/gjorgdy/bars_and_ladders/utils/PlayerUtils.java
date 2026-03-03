package nl.gjorgdy.bars_and_ladders.utils;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class PlayerUtils {

    public static void playDirectSound(ServerPlayer player, SoundEvent soundEvent, SoundSource soundCategory) {
        playDirectSound(player, soundEvent, soundCategory, 1.0f, 1.0f);
    }

    public static void playDirectSound(ServerPlayer player, SoundEvent soundEvent, SoundSource soundCategory, float volume, float pitch) {
        ClientboundSoundEntityPacket packet = new ClientboundSoundEntityPacket(
                BuiltInRegistries.SOUND_EVENT.wrapAsHolder(soundEvent),
                soundCategory,
                player, // The entity "emitting" the sound (can be the player themselves)
                volume,
                pitch,
                player.getRandom().nextLong() // Seed
        );
        player.connection.send(packet);
    }

    public static void setVelocity(ServerPlayer player, Vec3 velocity) {
        player.setDeltaMovement(velocity);
        player.connection.send(new ClientboundSetEntityMotionPacket(player.getId(), velocity), null);
    }

    /**
     * Fakes a block interaction on the client by swinging the hand and sending a packet to the server.
     * @param player the client player
     * @param hand the hand used
     * @param hitResult the block hit result
     * @return ActionResult.FAIL to prevent further processing
     */
    public static InteractionResult clientSwingHand(Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player instanceof LocalPlayer clientPlayer) {
            var packet = new ServerboundUseItemOnPacket(hand, hitResult, 0);
            clientPlayer.connection.send(packet);
            return InteractionResult.FAIL;
        }
        return InteractionResult.PASS;
    }

}
