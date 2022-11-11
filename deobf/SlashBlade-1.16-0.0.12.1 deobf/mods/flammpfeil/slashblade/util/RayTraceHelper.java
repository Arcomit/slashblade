//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.vector.*;
import java.util.function.*;
import net.minecraft.util.math.*;
import javax.annotation.*;
import java.util.*;

public class RayTraceHelper
{
    public static Optional<RayTraceResult> rayTrace(final World worldIn, final Entity entityIn, final Vector3d start, final Vector3d dir, final double blockReach, double entityReach, final Predicate<Entity> selector) {
        Vector3d end = start.add(dir.scale(blockReach));
        RayTraceResult raytraceresult = (RayTraceResult)worldIn.clip(new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, entityIn));
        if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            end = raytraceresult.getLocation();
            entityReach = start.distanceTo(end);
        }
        else {
            end = start.add(dir.scale(entityReach));
        }
        final AxisAlignedBB area = entityIn.getBoundingBox().expandTowards(dir.scale(entityReach)).inflate(1.0);
        final EntityRayTraceResult entityraytraceresult = rayTrace(worldIn, entityIn, start, end, area, selector);
        if (entityraytraceresult != null) {
            raytraceresult = (RayTraceResult)entityraytraceresult;
        }
        return Optional.ofNullable(raytraceresult);
    }
    
    @Nullable
    public static EntityRayTraceResult rayTrace(final World worldIn, final Entity entityIn, final Vector3d start, final Vector3d end, final AxisAlignedBB boundingBox, final Predicate<Entity> selector) {
        return rayTrace(worldIn, entityIn, start, end, boundingBox, selector, Double.MAX_VALUE);
    }
    
    @Nullable
    public static EntityRayTraceResult rayTrace(final World worldIn, final Entity entityIn, final Vector3d start, final Vector3d end, final AxisAlignedBB boundingBox, final Predicate<Entity> selector, final double limitDist) {
        double currentDist = limitDist;
        Entity resultEntity = null;
        for (final Entity foundEntity : worldIn.getEntities(entityIn, boundingBox, (Predicate)selector)) {
            final AxisAlignedBB axisalignedbb = foundEntity.getBoundingBox().inflate(0.30000001192092896);
            final Optional<Vector3d> optional = (Optional<Vector3d>)axisalignedbb.clip(start, end);
            if (optional.isPresent()) {
                final double newDist = start.distanceToSqr((Vector3d)optional.get());
                if (newDist >= currentDist) {
                    continue;
                }
                resultEntity = foundEntity;
                currentDist = newDist;
            }
        }
        if (resultEntity == null) {
            return null;
        }
        return new EntityRayTraceResult(resultEntity);
    }
}
