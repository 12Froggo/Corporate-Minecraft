package froggo.corporateminecraft;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class ModBlockIds {
    private static ResourceKey<Block> create(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(CorporateMinecraft.MOD_ID, name);
        return ResourceKey.create(Registries.BLOCK, id);
    }
}