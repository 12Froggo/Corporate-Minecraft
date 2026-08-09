package froggo.corporateminecraft.client;

import froggo.corporateminecraft.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class CorporateMinecraftClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		Mailbox.initialize();

		ClientTickEvents.END_CLIENT_TICK.register(
				client -> Mailbox.tick()
		);

		UseBlockCallback.EVENT.register(
				(player, level, hand, hitResult) -> {

					if (level.getBlockState(
							hitResult.getBlockPos()
					).is(ModBlocks.COMPUTER)) {

						Minecraft.getInstance().execute(() -> {
							Minecraft.getInstance().gui.setScreen(
									new ComputerScreen(
											Component.literal(
													"Corporate Computer"
											)
									)
							);
						});

						return InteractionResult.SUCCESS;
					}

					return InteractionResult.PASS;
				}
		);
	}
}