package froggo.corporateminecraft;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;


public class ModBlockItemIds {

    private static BlockItemId create(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(CorporateMinecraft.MOD_ID, name);
        return BlockItemId.create(id, id);
    }
    public static final BlockItemId COMPUTER = create(
            "computer"
    );
}