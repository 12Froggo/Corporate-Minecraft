package froggo.corporateminecraft;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public record ServerBoundReputationEffectPayload(
        List<EffectData> effects
) implements CustomPacketPayload {

    public static final Identifier ID =
            Identifier.fromNamespaceAndPath(
                    "corporate-minecraft",
                    "reputation_effects"
            );

    public static final CustomPacketPayload.Type<ServerBoundReputationEffectPayload> TYPE =
            new CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerBoundReputationEffectPayload> CODEC =
            StreamCodec.composite(
                    EffectData.CODEC.apply(
                            ByteBufCodecs.list()
                    ),
                    ServerBoundReputationEffectPayload::effects,
                    ServerBoundReputationEffectPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public record EffectData(
            String id,
            int duration,
            int amplifier
    ) {

        public static final StreamCodec<RegistryFriendlyByteBuf, EffectData> CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.STRING_UTF8,
                        EffectData::id,

                        ByteBufCodecs.INT,
                        EffectData::duration,

                        ByteBufCodecs.INT,
                        EffectData::amplifier,

                        EffectData::new
                );
    }
}