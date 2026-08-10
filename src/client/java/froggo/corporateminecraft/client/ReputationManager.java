package froggo.corporateminecraft.client;

import froggo.corporateminecraft.ServerBoundReputationEffectPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;

public class ReputationManager {

    private static int reputation = 90;

    /*
     * These are the effects that should currently be active
     * because of the player's reputation.
     */
    private static final List<ReputationEffect> effects =
            new ArrayList<>();


    public static int getReputation() {
        return reputation;
    }


    public static List<ReputationEffect> getEffects() {
        return effects;
    }


    public static void changeReputation(int amount) {

        reputation += amount;

        if (reputation > 100) {
            reputation = 100;
        } else if (reputation < 0) {
            reputation = 0;
        }

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        checkConsequences();
    }


    public static void checkConsequences() {

        /*
         * Remove the previous reputation consequences.
         * We are going to rebuild the list based on
         * the current reputation.
         */
        effects.clear();

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }


        /*
         * Reputation > 90
         */
        if (reputation > 90) {

            effects.add(
                    new ReputationEffect(
                            "minecraft:hero_of_the_village",
                            5 * 60 * 20,
                            1
                    )
            );
        }
        if (reputation > 80) {

            effects.add(
                    new ReputationEffect(
                            "minecraft:absorption",
                            5 * 60 * 20,
                            1
                    )
            );
        }

        if (reputation > 60) {

            effects.add(
                    new ReputationEffect(
                            "minecraft:saturation",
                            5 * 60 * 20,
                            1
                    )
            );
        }

        if (reputation < 40) {

            effects.add(
                    new ReputationEffect(
                            "minecraft:slowness",
                            2 * 60 * 20,
                            0
                    )
            );
        }

        if (reputation < 10) {

            effects.add(
                    new ReputationEffect(
                            "minecraft:hunger",
                            1 * 60 * 20,
                            0
                    )
            );
        }

        sendEffectsToServer();
    }

    private static void sendEffectsToServer() {

        List<ServerBoundReputationEffectPayload.EffectData>
                serverEffects = new ArrayList<>();


        for (ReputationEffect effect : effects) {

            serverEffects.add(
                    new ServerBoundReputationEffectPayload.EffectData(
                            effect.id(),
                            effect.duration(),
                            effect.amplifier()
                    )
            );
        }

        ServerBoundReputationEffectPayload payload =
                new ServerBoundReputationEffectPayload(
                        serverEffects
                );
        ClientPlayNetworking.send(payload);
    }

    public record ReputationEffect(
            String id,
            int duration,
            int amplifier
    ) {
    }
}