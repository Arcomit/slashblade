//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.util;

import net.minecraft.util.math.vector.*;
import net.minecraft.nbt.*;
import java.util.function.*;
import java.util.*;

public class NBTHelper
{
    public static Vector3d getVector3d(final CompoundNBT tag, final String key) {
        final ListNBT listnbt = tag.getList(key, 6);
        return new Vector3d(listnbt.getDouble(0), listnbt.getDouble(1), listnbt.getDouble(2));
    }
    
    public static void putVector3d(final CompoundNBT tag, final String key, final Vector3d value) {
        tag.put(key, (INBT)newDoubleNBTList(value.x, value.y, value.z));
    }
    
    public static ListNBT newDoubleNBTList(final Vector3d value) {
        return newDoubleNBTList(value.x, value.y, value.z);
    }
    
    public static ListNBT newDoubleNBTList(final double... numbers) {
        final ListNBT listnbt = new ListNBT();
        for (final double dValue : numbers) {
            listnbt.add((Object)DoubleNBT.valueOf(dValue));
        }
        return listnbt;
    }
    
    public static NBTCoupler getNBTCoupler(final CompoundNBT tag) {
        return new NBTCoupler(tag);
    }
    
    public static <T> void writeNBT(final CompoundNBT dest, final String key, final T... value) {
        if (value == null || value.length != 1 || value[0] == null) {
            return;
        }
        final Class<T> type = (Class<T>)value.getClass().getComponentType();
        if (type.equals(Integer.class)) {
            dest.putInt(key, (int)value[0]);
        }
        else if (type.equals(Float.class)) {
            dest.putFloat(key, (float)value[0]);
        }
        else if (type.equals(Short.class)) {
            dest.putShort(key, (short)value[0]);
        }
        else if (type.equals(Byte.class)) {
            dest.putByte(key, (byte)value[0]);
        }
        else if (type.equals(Long.class)) {
            dest.putLong(key, (long)value[0]);
        }
        else if (type.equals(Double.class)) {
            dest.putDouble(key, (double)value[0]);
        }
        else if (type.equals(Boolean.class)) {
            dest.putBoolean(key, (boolean)value[0]);
        }
        else if (value[0] != null) {
            if (type.equals(UUID.class)) {
                dest.putUUID(key, (UUID)value[0]);
            }
            else if (type.equals(byte[].class)) {
                dest.putByteArray(key, (byte[])(byte[])(Object)value[0]);
            }
            else if (type.equals(int[].class)) {
                dest.putIntArray(key, (int[])(int[])(Object)value[0]);
            }
            else if (type.equals(long[].class)) {
                dest.putLongArray(key, (long[])(long[])(Object)value[0]);
            }
            else if (type.equals(CompoundNBT.class)) {
                dest.put(key, (INBT)value[0]);
            }
            else if (type.equals(String.class)) {
                dest.putString(key, (String)value[0]);
            }
            else if (type.equals(Vector3d.class)) {
                putVector3d(dest, key, (Vector3d)value[0]);
            }
        }
    }
    
    public static <T> void readNBT(final CompoundNBT src, final String key, final Consumer<T> dest, final T... values) {
        readNBT(src, key, dest, false, values);
    }
    
    public static <T> void readNBT(final CompoundNBT src, final String key, final Consumer<T> dest, final boolean isNullable, final T... defaultValue) {
        if (isNullable) {
            dest.accept(castValue(key, src, defaultValue).orElse(null));
        }
        else {
            castValue(key, src, defaultValue).ifPresent(dest);
        }
    }
    
    public static <T> Optional<T> castValue(final String key, final CompoundNBT src, final T... defaultValue) {
        if (defaultValue == null) {
            return Optional.empty();
        }
        final Class<T> type = (Class<T>)defaultValue.getClass().getComponentType();
        Object result = null;
        int typeId = -1;
        if (type.equals(Integer.class)) {
            typeId = 99;
            result = src.getInt(key);
        }
        else if (type.equals(Float.class)) {
            typeId = 99;
            result = src.getFloat(key);
        }
        else if (type.equals(Short.class)) {
            typeId = 99;
            result = src.getShort(key);
        }
        else if (type.equals(Byte.class)) {
            typeId = 99;
            result = src.getByte(key);
        }
        else if (type.equals(Long.class)) {
            typeId = 99;
            result = src.getLong(key);
        }
        else if (type.equals(Double.class)) {
            typeId = 99;
            result = src.getDouble(key);
        }
        else if (type.equals(Boolean.class)) {
            typeId = 99;
            result = src.getBoolean(key);
        }
        else if (src.contains(key)) {
            if (type.equals(UUID.class)) {
                typeId = -2;
                if (src.hasUUID(key)) {
                    result = src.getUUID(key);
                }
            }
            else if (type.equals(byte[].class)) {
                typeId = 7;
                result = src.getByteArray(key);
            }
            else if (type.equals(int[].class)) {
                typeId = 11;
                result = src.getIntArray(key);
            }
            else if (type.equals(long[].class)) {
                typeId = 12;
                result = src.getLongArray(key);
            }
            else if (type.equals(CompoundNBT.class)) {
                typeId = 10;
                result = src.getCompound(key);
            }
            else if (type.equals(String.class)) {
                typeId = 8;
                result = src.getString(key);
            }
        }
        else if (type.equals(Vector3d.class)) {
            typeId = 6;
            result = getVector3d(src, key);
        }
        if (0 < defaultValue.length) {
            final boolean exists = (typeId == -2) ? src.hasUUID(key) : src.contains(key, typeId);
            if (!exists) {
                result = defaultValue;
            }
        }
        return Optional.ofNullable(result);
    }
    
    public static class NBTCoupler
    {
        CompoundNBT instance;
        NBTCoupler parent;
        
        protected NBTCoupler(final CompoundNBT tag) {
            this.parent = null;
            this.instance = tag;
        }
        
        public <T> NBTCoupler put(final String key, final T... value) {
            NBTHelper.writeNBT(this.instance, key, value);
            return this;
        }
        
        public <T> NBTCoupler get(final String key, final Consumer<T> dest, final T... values) {
            return this.get(key, dest, false, values);
        }
        
        public <T> NBTCoupler get(final String key, final Consumer<T> dest, final boolean isNullable, final T... values) {
            NBTHelper.readNBT(this.instance, key, dest, isNullable, values);
            return this;
        }
        
        public NBTCoupler remove(final String key) {
            if (this.instance.hasUUID(key)) {
                this.instance.remove(key + "Most");
                this.instance.remove(key + "Least");
            }
            else {
                this.instance.remove(key);
            }
            return this;
        }
        
        public NBTCoupler getChild(final String key) {
            CompoundNBT tag;
            if (this.instance.contains(key, 10)) {
                tag = this.instance.getCompound(key);
            }
            else {
                tag = new CompoundNBT();
                this.instance.put(key, (INBT)tag);
            }
            return NBTHelper.getNBTCoupler(tag);
        }
        
        public NBTCoupler getParent() {
            if (this.parent != null) {
                return this.parent;
            }
            return this;
        }
        
        public CompoundNBT getRawCompound() {
            return this.instance;
        }
        
        public CompoundNBT getRawCompound(final String key) {
            if (this.instance.contains(key, 10)) {
                return this.instance.getCompound(key);
            }
            final CompoundNBT nbt = new CompoundNBT();
            this.instance.put(key, (INBT)nbt);
            return nbt;
        }
        
        public NBTCoupler doRawCompound(final String key, final Consumer<CompoundNBT> action) {
            if (this.instance.contains(key, 10)) {
                action.accept(this.instance.getCompound(key));
            }
            return this;
        }
    }
}
