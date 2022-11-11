//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.capability.mobeffect;

import java.util.*;
import net.minecraft.potion.*;
import com.google.common.collect.*;

public class MobEffectState implements IMobEffectState
{
    long stunTimeout;
    long freezeTimeout;
    int stunLimit;
    int freezeLimit;
    Optional<Long> UntouchableTimeout;
    int untouchableLimit;
    Set<Effect> effectSet;
    float storedHealth;
    boolean hasWorked;
    Optional<Long> avoidCooldown;
    int avoidCount;
    
    public MobEffectState() {
        this.stunTimeout = -1L;
        this.freezeTimeout = -1L;
        this.stunLimit = 200;
        this.freezeLimit = 200;
        this.UntouchableTimeout = Optional.empty();
        this.untouchableLimit = 200;
        this.effectSet = (Set<Effect>)Sets.newHashSet();
        this.avoidCooldown = Optional.empty();
        this.avoidCount = 0;
    }
    
    public void setStunTimeOut(final long timeout) {
        this.stunTimeout = timeout;
    }
    
    public long getStunTimeOut() {
        return this.stunTimeout;
    }
    
    public void setFreezeTimeOut(final long timeout) {
        this.freezeTimeout = timeout;
    }
    
    public long getFreezeTimeOut() {
        return this.freezeTimeout;
    }
    
    public int getStunLimit() {
        return this.stunLimit;
    }
    
    public void setStunLimit(final int limit) {
        this.stunLimit = limit;
    }
    
    public int getFreezeLimit() {
        return this.freezeLimit;
    }
    
    public void setFreezeLimit(final int limit) {
        this.freezeLimit = limit;
    }
    
    public int getUntouchableLimit() {
        return this.untouchableLimit;
    }
    
    public void setUntouchableLimit(final int limit) {
        this.untouchableLimit = limit;
    }
    
    public void setUntouchableTimeOut(final Optional<Long> timeout) {
        this.UntouchableTimeout = timeout;
    }
    
    public Optional<Long> getUntouchableTimeOut() {
        return this.UntouchableTimeout;
    }
    
    public Set<Effect> getEffectSet() {
        return this.effectSet;
    }
    
    public boolean hasUntouchableWorked() {
        return this.hasWorked;
    }
    
    public void setUntouchableWorked(final boolean b) {
        this.hasWorked = b;
    }
    
    public float getStoredHealth() {
        return this.storedHealth;
    }
    
    public void storeHealth(final float health) {
        this.storedHealth = health;
    }
    
    public Optional<Long> getAvoidCooldown() {
        return this.avoidCooldown;
    }
    
    public int getAvoidCount() {
        return this.avoidCount;
    }
    
    public void setAvoidCooldown(final Optional<Long> time) {
        this.avoidCooldown = time;
    }
    
    public void setAvoidCount(final int value) {
        this.avoidCount = value;
    }
}
