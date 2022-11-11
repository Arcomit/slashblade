//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.javafmlmod.*;
import mods.flammpfeil.slashblade.network.*;
import mods.flammpfeil.slashblade.capability.mobeffect.*;
import mods.flammpfeil.slashblade.capability.inputstate.*;
import mods.flammpfeil.slashblade.capability.concentrationrank.*;
import mods.flammpfeil.slashblade.util.*;
import mods.flammpfeil.slashblade.ability.*;
import net.minecraft.client.*;
import mods.flammpfeil.slashblade.client.renderer.layers.*;
import net.minecraft.client.renderer.entity.layers.*;
import mods.flammpfeil.slashblade.event.*;
import mods.flammpfeil.slashblade.event.client.*;
import mods.flammpfeil.slashblade.client.renderer.gui.*;
import net.minecraftforge.fml.client.registry.*;
import mods.flammpfeil.slashblade.client.renderer.entity.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.event.lifecycle.*;
import java.util.stream.*;
import java.util.*;
import net.minecraftforge.fml.event.server.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.client.event.*;
import mods.flammpfeil.slashblade.init.*;
import net.minecraft.client.renderer.model.*;
import mods.flammpfeil.slashblade.client.renderer.model.*;
import net.minecraft.client.renderer.entity.*;
import mods.flammpfeil.slashblade.capability.slashblade.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import java.util.concurrent.*;
import net.minecraft.client.renderer.tileentity.*;
import mods.flammpfeil.slashblade.entity.*;
import net.minecraftforge.event.*;
import net.minecraft.block.*;
import net.minecraftforge.registries.*;
import net.minecraft.item.crafting.*;
import net.minecraft.tags.*;
import net.minecraftforge.common.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import mods.flammpfeil.slashblade.item.*;
import com.google.common.base.*;
import net.minecraft.util.registry.*;
import net.minecraft.stats.*;
import mods.flammpfeil.slashblade.client.renderer.*;
import net.minecraft.entity.*;
import java.util.function.*;

@Mod("slashblade")
public class SlashBlade
{
    public static final String modid = "slashblade";
    public static final ItemGroup SLASHBLADE;
    public static final Logger LOGGER;
    
    public SlashBlade() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((Consumer)this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener((Consumer)this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener((Consumer)this::processIMC);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener((Consumer)this::doClientStuff);
            FMLJavaModLoadingContext.get().getModEventBus().addListener((Consumer)this::Baked);
            MinecraftForge.EVENT_BUS.addListener((Consumer)MoveInputHandler::onPlayerPostTick);
            return;
        });
        MinecraftForge.EVENT_BUS.register((Object)this);
        NetworkManager.register();
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        CapabilitySlashBlade.register();
        CapabilityMobEffect.register();
        CapabilityInputState.register();
        CapabilityConcentrationRank.register();
        MinecraftForge.EVENT_BUS.addListener((Consumer)KnockBackHandler::onLivingKnockBack);
        FallHandler.getInstance().register();
        LockOnManager.getInstance().register();
        MinecraftForge.EVENT_BUS.register((Object)new CapabilityAttachHandler());
        MinecraftForge.EVENT_BUS.register((Object)new StunManager());
        AnvilCrafting.getInstance().register();
        RefineHandler.getInstance().register();
        KillCounter.getInstance().register();
        RankPointHandler.getInstance().register();
        AllowFlightOverrwrite.getInstance().register();
        BlockPickCanceller.getInstance().register();
        MinecraftForge.EVENT_BUS.addListener((Consumer)TargetSelector::onInputChange);
        SummonedSwordArts.getInstance().register();
        SlayerStyleArts.getInstance().register();
        Untouchable.getInstance().register();
        PlacePreviewEntryPoint.getInstance().register();
    }
    
    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)BladeModelManager.getInstance());
        MinecraftForge.EVENT_BUS.register((Object)BladeMotionManager.getInstance());
        Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap().values().stream().forEach(lr -> ((PlayerRenderer)lr).addLayer((LayerRenderer)new LayerMainBlade(lr)));
        SneakingMotionCanceller.getInstance().register();
        UserPoseOverrider.getInstance().register();
        LockonCircleRender.getInstance().register();
        BladeComponentTooltips.getInstance().register();
        BladeMaterialTooltips.getInstance().register();
        AdvancementsRecipeRenderer.getInstance().register();
        RankRenderer.getInstance().register();
        RenderingRegistry.registerEntityRenderingHandler((EntityType)RegistryEvents.SummonedSword, SummonedSwordRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType)RegistryEvents.JudgementCut, JudgementCutRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType)RegistryEvents.BladeItem, BladeItemEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType)RegistryEvents.BladeStand, BladeStandEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType)RegistryEvents.SlashEffect, SlashEffectRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType)RegistryEvents.PlacePreview, PlacePreviewEntityRenderer::new);
        SlashBlade.LOGGER.info("Got game settings {}", (Object)event.getMinecraftSupplier().get().options);
    }
    
    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            SlashBlade.LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }
    
    private void processIMC(final InterModProcessEvent event) {
        SlashBlade.LOGGER.info("Got IMC {}", (Object)event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect((Collector<? super Object, ?, List<Object>>)Collectors.toList()));
    }
    
    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
        SlashBlade.LOGGER.info("HELLO from server starting");
    }
    
    @OnlyIn(Dist.CLIENT)
    private void Baked(final ModelBakeEvent event) {
        final ModelResourceLocation loc = new ModelResourceLocation(ForgeRegistries.ITEMS.getKey((IForgeRegistryEntry)SBItems.slashblade), "inventory");
        final BladeModel model = new BladeModel((IBakedModel)event.getModelRegistry().get(loc), event.getModelLoader());
        event.getModelRegistry().put(loc, model);
    }
    
    static {
        SLASHBLADE = new ItemGroup("slashblade") {
            @OnlyIn(Dist.CLIENT)
            public ItemStack makeIcon() {
                final ItemStack stack = new ItemStack((IItemProvider)SBItems.slashblade);
                stack.getCapability(ItemSlashBlade.BLADESTATE).ifPresent(s -> {
                    s.setModel(new ResourceLocation("slashblade", "model/named/yamato.obj"));
                    s.setTexture(new ResourceLocation("slashblade", "model/named/yamato.png"));
                });
                return stack;
            }
        };
        LOGGER = LogManager.getLogger();
    }
    
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents
    {
        static Supplier<Callable<ItemStackTileEntityRenderer>> teisr;
        public static final ResourceLocation BladeItemEntityLoc;
        public static final EntityType<BladeItemEntity> BladeItem;
        public static final ResourceLocation BladeStandEntityLoc;
        public static final EntityType<BladeStandEntity> BladeStand;
        public static final ResourceLocation SummonedSwordLoc;
        public static final EntityType<EntityAbstractSummonedSword> SummonedSword;
        public static final ResourceLocation JudgementCutLoc;
        public static final EntityType<EntityJudgementCut> JudgementCut;
        public static final ResourceLocation SlashEffectLoc;
        public static final EntityType<EntitySlashEffect> SlashEffect;
        public static final ResourceLocation PlacePreviewEntityLoc;
        public static final EntityType<PlacePreviewEntity> PlacePreview;
        public static final ResourceLocation SWORD_SUMMONED;
        
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = (IForgeRegistry<Block>)event.getRegistry();
            SlashBlade.LOGGER.info("HELLO from Register Block");
        }
        
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            final IForgeRegistry<Item> registry = (IForgeRegistry<Item>)event.getRegistry();
            final ITag<Item> tags;
            registry.register(new ItemSlashBlade((IItemTier)new ItemTierSlashBlade(() -> {
                tags = (ITag<Item>)ItemTags.getAllTags().getTag(new ResourceLocation("slashblade", "proudsouls"));
                return Ingredient.of((ITag)tags);
            }), 1, -2.4f, new Item.Properties().tab(ItemGroup.TAB_COMBAT).setISTER((Supplier)RegistryEvents.teisr)).setRegistryName("slashblade", "slashblade"));
            final ToolType proudsoulLevel = ToolType.get("proudsoul");
            registry.register(new Item(new Item.Properties().tab(SlashBlade.SLASHBLADE).addToolType(proudsoulLevel, 2)) {
                public boolean onEntityItemUpdate(final ItemStack stack, final ItemEntity entity) {
                    if (entity instanceof BladeItemEntity) {
                        return false;
                    }
                    final CompoundNBT tag = entity.serializeNBT();
                    tag.putInt("Health", 50);
                    final int age = tag.getShort("Age");
                    entity.deserializeNBT(tag);
                    if (entity.isGlowing()) {
                        entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.8, 0.0, 0.8).add(0.0, 0.04, 0.0));
                    }
                    else if (entity.isOnFire()) {
                        entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.8, 0.5, 0.8).add(0.0, 0.04, 0.0));
                    }
                    return false;
                }
                
                public boolean isFoil(final ItemStack stack) {
                    return true;
                }
            }.setRegistryName("slashblade", "proudsoul"));
            registry.register(new Item(new Item.Properties().tab(SlashBlade.SLASHBLADE).addToolType(proudsoulLevel, 3)) {
                public boolean isFoil(final ItemStack stack) {
                    return true;
                }
            }.setRegistryName("slashblade", "proudsoul_ingot"));
            registry.register(new Item(new Item.Properties().tab(SlashBlade.SLASHBLADE).addToolType(proudsoulLevel, 1)) {
                public boolean isFoil(final ItemStack stack) {
                    return true;
                }
            }.setRegistryName("slashblade", "proudsoul_tiny"));
            registry.register(new Item(new Item.Properties().tab(SlashBlade.SLASHBLADE).addToolType(proudsoulLevel, 4).rarity(Rarity.UNCOMMON)) {
                public boolean isFoil(final ItemStack stack) {
                    return true;
                }
            }.setRegistryName("slashblade", "proudsoul_sphere"));
            registry.register(new Item(new Item.Properties().tab(SlashBlade.SLASHBLADE).addToolType(proudsoulLevel, 5).rarity(Rarity.RARE)) {
                public boolean isFoil(final ItemStack stack) {
                    return true;
                }
            }.setRegistryName("slashblade", "proudsoul_crystal"));
            registry.register(new Item(new Item.Properties().tab(SlashBlade.SLASHBLADE).addToolType(proudsoulLevel, 6).rarity(Rarity.EPIC)) {
                public boolean isFoil(final ItemStack stack) {
                    return true;
                }
            }.setRegistryName("slashblade", "proudsoul_trapezohedron"));
            registry.register(new BladeStandItem(new Item.Properties().tab(SlashBlade.SLASHBLADE).rarity(Rarity.COMMON)).setRegistryName("slashblade", "bladestand_1"));
            registry.register(new BladeStandItem(new Item.Properties().tab(SlashBlade.SLASHBLADE).rarity(Rarity.COMMON)).setRegistryName("slashblade", "bladestand_2"));
            registry.register(new BladeStandItem(new Item.Properties().tab(SlashBlade.SLASHBLADE).rarity(Rarity.COMMON)).setRegistryName("slashblade", "bladestand_v"));
            registry.register(new BladeStandItem(new Item.Properties().tab(SlashBlade.SLASHBLADE).rarity(Rarity.COMMON)).setRegistryName("slashblade", "bladestand_s"));
            registry.register(new BladeStandItem(new Item.Properties().tab(SlashBlade.SLASHBLADE).rarity(Rarity.COMMON), true).setRegistryName("slashblade", "bladestand_1w"));
            registry.register(new BladeStandItem(new Item.Properties().tab(SlashBlade.SLASHBLADE).rarity(Rarity.COMMON), true).setRegistryName("slashblade", "bladestand_2w"));
        }
        
        @SubscribeEvent
        public static void onEntitiesRegistry(final RegistryEvent.Register<EntityType<?>> event) {
            final EntityType<EntityAbstractSummonedSword> entity = RegistryEvents.SummonedSword;
            entity.setRegistryName(RegistryEvents.SummonedSwordLoc);
            event.getRegistry().register((IForgeRegistryEntry)entity);
            final EntityType<EntityJudgementCut> entity2 = RegistryEvents.JudgementCut;
            entity2.setRegistryName(RegistryEvents.JudgementCutLoc);
            event.getRegistry().register((IForgeRegistryEntry)entity2);
            final EntityType<BladeItemEntity> entity3 = RegistryEvents.BladeItem;
            entity3.setRegistryName(RegistryEvents.BladeItemEntityLoc);
            event.getRegistry().register((IForgeRegistryEntry)entity3);
            final EntityType<BladeStandEntity> entity4 = RegistryEvents.BladeStand;
            entity4.setRegistryName(RegistryEvents.BladeStandEntityLoc);
            event.getRegistry().register((IForgeRegistryEntry)entity4);
            final EntityType<EntitySlashEffect> entity5 = RegistryEvents.SlashEffect;
            entity5.setRegistryName(RegistryEvents.SlashEffectLoc);
            event.getRegistry().register((IForgeRegistryEntry)entity5);
            final EntityType<PlacePreviewEntity> entity6 = RegistryEvents.PlacePreview;
            entity6.setRegistryName(RegistryEvents.PlacePreviewEntityLoc);
            event.getRegistry().register((IForgeRegistryEntry)entity6);
        }
        
        private static String classToString(final Class<? extends Entity> entityClass) {
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityClass.getSimpleName()).replace("entity_", "");
        }
        
        private static ResourceLocation registerCustomStat(final String name) {
            final ResourceLocation resourcelocation = new ResourceLocation("slashblade", name);
            Registry.register(Registry.CUSTOM_STAT, name, (Object)resourcelocation);
            Stats.CUSTOM.get((Object)resourcelocation, IStatFormatter.DEFAULT);
            return resourcelocation;
        }
        
        static {
            RegistryEvents.teisr = (Supplier<Callable<ItemStackTileEntityRenderer>>)(() -> SlashBladeTEISR::new);
            BladeItemEntityLoc = new ResourceLocation("slashblade", classToString((Class<? extends Entity>)BladeItemEntity.class));
            BladeItem = EntityType.Builder.of(BladeItemEntity::new, EntityClassification.MISC).sized(0.25f, 0.25f).setTrackingRange(4).setUpdateInterval(20).setCustomClientFactory((BiFunction)BladeItemEntity::createInstanceFromPacket).build(RegistryEvents.BladeItemEntityLoc.toString());
            BladeStandEntityLoc = new ResourceLocation("slashblade", classToString((Class<? extends Entity>)BladeStandEntity.class));
            BladeStand = EntityType.Builder.of(BladeStandEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).setTrackingRange(10).setUpdateInterval(20).setShouldReceiveVelocityUpdates(false).setCustomClientFactory((BiFunction)BladeStandEntity::createInstance).build(RegistryEvents.BladeStandEntityLoc.toString());
            SummonedSwordLoc = new ResourceLocation("slashblade", classToString((Class<? extends Entity>)EntityAbstractSummonedSword.class));
            SummonedSword = EntityType.Builder.of(EntityAbstractSummonedSword::new, EntityClassification.MISC).sized(0.5f, 0.5f).setTrackingRange(4).setUpdateInterval(20).setCustomClientFactory((BiFunction)EntityAbstractSummonedSword::createInstance).build(RegistryEvents.SummonedSwordLoc.toString());
            JudgementCutLoc = new ResourceLocation("slashblade", classToString((Class<? extends Entity>)EntityJudgementCut.class));
            JudgementCut = EntityType.Builder.of(EntityJudgementCut::new, EntityClassification.MISC).sized(2.5f, 2.5f).setTrackingRange(4).setUpdateInterval(20).setCustomClientFactory((BiFunction)EntityJudgementCut::createInstance).build(RegistryEvents.JudgementCutLoc.toString());
            SlashEffectLoc = new ResourceLocation("slashblade", classToString((Class<? extends Entity>)EntitySlashEffect.class));
            SlashEffect = EntityType.Builder.of(EntitySlashEffect::new, EntityClassification.MISC).sized(3.0f, 3.0f).setTrackingRange(4).setUpdateInterval(20).setCustomClientFactory((BiFunction)EntitySlashEffect::createInstance).build(RegistryEvents.SlashEffectLoc.toString());
            PlacePreviewEntityLoc = new ResourceLocation("slashblade", classToString((Class<? extends Entity>)PlacePreviewEntity.class));
            PlacePreview = EntityType.Builder.of(PlacePreviewEntity::new, EntityClassification.MISC).sized(0.5f, 0.5f).setTrackingRange(10).setUpdateInterval(20).setShouldReceiveVelocityUpdates(false).setCustomClientFactory((BiFunction)PlacePreviewEntity::createInstance).build(RegistryEvents.PlacePreviewEntityLoc.toString());
            SWORD_SUMMONED = registerCustomStat("sword_summoned");
        }
    }
}
