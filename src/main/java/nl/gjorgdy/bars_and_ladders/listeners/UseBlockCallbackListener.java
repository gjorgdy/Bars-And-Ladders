package nl.gjorgdy.bars_and_ladders.listeners;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nl.gjorgdy.bars_and_ladders.modules.Ladders;
import nl.gjorgdy.bars_and_ladders.utils.PlayerUtils;
import org.jspecify.annotations.NonNull;

public class UseBlockCallbackListener implements UseBlockCallback {

    @Override
    public @NonNull InteractionResult interact(Player player, Level world, @NonNull InteractionHand hand, BlockHitResult hitResult) {
        BlockState blockState = world.getBlockState(hitResult.getBlockPos());
        ItemStack itemStack = player.getItemInHand(hand);

        if (hand == InteractionHand.MAIN_HAND && player.getOffhandItem().getItem() instanceof BlockItem) return InteractionResult.PASS;
        if (hand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() instanceof BlockItem) return InteractionResult.PASS;

        return interactLadders(player, world, hand, hitResult, itemStack, blockState);
    }

    private @NonNull InteractionResult interactLadders(Player player, Level world, @NonNull InteractionHand hand, BlockHitResult hitResult, ItemStack itemStack, BlockState blockState) {
        if (itemStack.is(Items.LADDER) && blockState.is(Blocks.LADDER)) {
            if (world.isClientSide()) return PlayerUtils.clientSwingHand(player, hand, hitResult);
            if (Ladders.lower(player, itemStack, world, hitResult.getBlockPos())) {
                player.swing(hand, SwingAnimation.DEFAULT, true);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

}
