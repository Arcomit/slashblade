//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.network;

import net.minecraftforge.fml.network.simple.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.network.*;
import java.util.function.*;

public class NetworkManager
{
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE;
    
    public static void register() {
        int id = 0;
        NetworkManager.INSTANCE.registerMessage(id++, (Class)MoveCommandMessage.class, (BiConsumer)MoveCommandMessage::encode, (Function)MoveCommandMessage::decode, (BiConsumer)MoveCommandMessage::handle);
        NetworkManager.INSTANCE.registerMessage(id++, (Class)ActiveStateSyncMessage.class, (BiConsumer)ActiveStateSyncMessage::encode, (Function)ActiveStateSyncMessage::decode, (BiConsumer)ActiveStateSyncMessage::handle);
        NetworkManager.INSTANCE.registerMessage(id++, (Class)RankSyncMessage.class, (BiConsumer)RankSyncMessage::encode, (Function)RankSyncMessage::decode, (BiConsumer)RankSyncMessage::handle);
    }
    
    static {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("slashblade", "main"), () -> "1", (Predicate)"1"::equals, (Predicate)"1"::equals);
    }
}
