package io.github.icarus3;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.registries.ForgeRegistries;

public class RemoteControlItem extends Item {
    public RemoteControlItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            boolean current = serverLevel.getGameRules().getRule(GameRules.RULE_DOMOBSPAWNING).get();
            serverLevel.getGameRules().getRule(GameRules.RULE_DOMOBSPAWNING).set(!current, serverLevel.getServer());
            player.sendSystemMessage(Component.literal("生物生成" + (!current ? "启用!" : "禁用!")));

            // 物品使用时播放音效
            level.playSound(
                    null,
                    player.blockPosition(),
                    SoundEvents.ANVIL_LAND,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );

            // 冷却设置
            player.getCooldowns().addCooldown(this, 20);
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}
