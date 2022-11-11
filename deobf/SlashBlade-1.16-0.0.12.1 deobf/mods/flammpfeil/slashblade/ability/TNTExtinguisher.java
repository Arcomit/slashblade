//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.ability;

import net.minecraft.world.server.*;
import net.minecraft.particles.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;

public class TNTExtinguisher
{
    public static void doExtinguishing(final Entity target, final LivingEntity attacker) {
        if (!(target instanceof TNTEntity)) {
            return;
        }
        if (attacker.level.isClientSide) {
            return;
        }
        target.remove();
        final ServerWorld world = (ServerWorld)attacker.level;
        world.sendParticles((IParticleData)ParticleTypes.SMOKE, target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), 5, target.getBbWidth() * 1.5, (double)target.getBbHeight(), target.getBbWidth() * 1.5, 0.02);
        if (target.getType() == EntityType.TNT && world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            final ItemEntity itementity = new ItemEntity((World)world, target.getX(), target.getY() + target.getBbHeight(), target.getZ(), new ItemStack((IItemProvider)Items.TNT));
            itementity.setDefaultPickUpDelay();
            world.addFreshEntity((Entity)itementity);
        }
    }
}
