package froggo.corporateminecraft;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import froggo.corporateminecraft.ServerBoundReputationEffectPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CorporateMinecraft implements ModInitializer {
	public static final String MOD_ID = "corporate-minecraft";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModBlocks.initialize();

		PayloadTypeRegistry.serverboundPlay().register(
				ServerBoundReputationEffectPayload.TYPE,
				ServerBoundReputationEffectPayload.CODEC
		);

		ServerPlayNetworking.registerGlobalReceiver(
				ServerBoundReputationEffectPayload.TYPE,
				(payload, context) -> {

					context.server().execute(() -> {

						var player = context.player();

						System.out.println(
								"Received reputation effects from "
										+ player.getName().getString()
						);

						for (
								ServerBoundReputationEffectPayload.EffectData effect
								: payload.effects()
						) {

							System.out.println(
									"Received effect: "
											+ effect.id()
											+ " duration: "
											+ effect.duration()
											+ " amplifier: "
											+ effect.amplifier()
							);


							Identifier effectId =
									Identifier.parse(effect.id());

							BuiltInRegistries.MOB_EFFECT
									.get(effectId)
									.ifPresent(effectHolder -> {

										player.addEffect(
												new MobEffectInstance(
														effectHolder,
														effect.duration(),
														effect.amplifier()
												)
										);

									});
						}
					});
				}
		);

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
