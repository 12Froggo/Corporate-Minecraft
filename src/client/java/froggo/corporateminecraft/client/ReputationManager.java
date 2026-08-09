package froggo.corporateminecraft.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class ReputationManager {

    private static int reputation = 50;

    public static int getReputation() {
        return reputation;
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

        minecraft.player.removeAllEffects();

        checkConsequences();
    }

    public static void checkConsequences() {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.player == null) {
            return;
        }

        if (reputation > 90) {

            minecraft.player.addEffect(
                    new MobEffectInstance(
                            MobEffects.HERO_OF_THE_VILLAGE,
                            100000,
                            255
                    )
            );
        }
    }
}