//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.mobeffect;

import net.minecraft.potion.*;
import java.util.*;

public interface IMobEffectState
{
    public static final int AVOID_MAX = 3;
    public static final int COOLDOWN_TICKS = 20;
    
    default void setManagedStun(final long now, final long duration) {
        if (duration <= 0L) {
            return;
        }
        final long solvedDuration = Math.min(duration, this.getStunLimit());
        final long timeout = now + solvedDuration;
        if (this.getStunTimeOut() < timeout) {
            this.setStunTimeOut(timeout);
        }
    }
    
    void setStunTimeOut(final long p0);
    
    default void clearStunTimeOut() {
        this.setStunTimeOut(-1L);
    }
    
    long getStunTimeOut();
    
    default boolean isStun(final long now) {
        return this.isStun(now, false);
    }
    
    default boolean isStun(final long now, final boolean isVirtual) {
        long timeout = this.getStunTimeOut();
        if (timeout <= 0L) {
            return false;
        }
        timeout -= now;
        if (timeout <= 0L || this.getStunLimit() < timeout) {
            if (!isVirtual) {
                this.clearStunTimeOut();
            }
            return false;
        }
        return true;
    }
    
    default void setManagedFreeze(final long now, final long duration) {
        if (duration <= 0L) {
            return;
        }
        final long solvedDuration = Math.min(duration, this.getFreezeLimit());
        final long timeout = now + solvedDuration;
        if (this.getFreezeTimeOut() < timeout) {
            this.setFreezeTimeOut(timeout);
        }
    }
    
    void setFreezeTimeOut(final long p0);
    
    default void clearFreezeTimeOut() {
        this.setFreezeTimeOut(-1L);
    }
    
    long getFreezeTimeOut();
    
    default boolean isFreeze(final long now) {
        return this.isFreeze(now, false);
    }
    
    default boolean isFreeze(final long now, final boolean isVirtual) {
        long timeout = this.getFreezeTimeOut();
        if (timeout <= 0L) {
            return false;
        }
        timeout -= now;
        if (timeout <= 0L || this.getFreezeLimit() < timeout) {
            if (!isVirtual) {
                this.clearFreezeTimeOut();
            }
            return false;
        }
        return true;
    }
    
    int getStunLimit();
    
    void setStunLimit(final int p0);
    
    int getFreezeLimit();
    
    void setFreezeLimit(final int p0);
    
    int getUntouchableLimit();
    
    void setUntouchableLimit(final int p0);
    
    default void setManagedUntouchable(final long now, final long duration) {
        if (duration <= 0L) {
            return;
        }
        final long solvedDuration = Math.min(duration, this.getUntouchableLimit());
        final long timeout = now + solvedDuration;
        if (!this.getUntouchableTimeOut().isPresent() || this.getUntouchableTimeOut().get() < timeout) {
            this.setUntouchableTimeOut(Optional.of(timeout));
        }
    }
    
    void setUntouchableTimeOut(final Optional<Long> p0);
    
    default void clearUntouchableTimeOut(final boolean isVirtual) {
        if (!isVirtual) {
            this.setUntouchableTimeOut(Optional.empty());
        }
    }
    
    Optional<Long> getUntouchableTimeOut();
    
    default boolean isUntouchable(final long now) {
        return this.isUntouchable(now, false);
    }
    
    default boolean isUntouchable(final long now, final boolean isVirtual) {
        return this.getUntouchableTimeOut().filter(timeout -> now < timeout).map(t -> {
            this.setUntouchableWorked();
            return true;
        }).orElseGet(() -> {
            this.clearUntouchableTimeOut(isVirtual);
            return false;
        });
    }
    
    Set<Effect> getEffectSet();
    
    default void storeEffects(final Collection<Effect> effects) {
        this.getEffectSet().clear();
        this.getEffectSet().addAll(effects);
    }
    
    boolean hasUntouchableWorked();
    
    void setUntouchableWorked(final boolean p0);
    
    default void setUntouchableWorked() {
        this.setUntouchableWorked(true);
    }
    
    float getStoredHealth();
    
    void storeHealth(final float p0);
    
    Optional<Long> getAvoidCooldown();
    
    int getAvoidCount();
    
    void setAvoidCooldown(final Optional<Long> p0);
    
    void setAvoidCount(final int p0);
    
    default boolean checkCanAvoid(final long now) {
        return this.getAvoidCount() < 3 || !this.getAvoidCooldown().filter(ct -> now < ct).isPresent();
    }
    
    default int doAvoid(final long now) {
        if (!this.checkCanAvoid(now)) {
            return 0;
        }
        final int count;
        return this.getAvoidCooldown().filter(ct -> now < ct).map(ct -> {
            count = this.getAvoidCount() + 1;
            this.setAvoidCount(count);
            return count;
        }).orElseGet(() -> {
            this.setAvoidCount(1);
            this.setAvoidCooldown(Optional.of(now + 20L));
            return 1;
        });
    }
}
