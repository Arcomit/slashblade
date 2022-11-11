//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Moflop\.gradle\caches\forge_gradle\mcp_repo\net\minecraft\mapping\1.16.5\mapping-1.16.5-mapping"!

//Decompiled by Procyon!

package mods.flammpfeil.slashblade.event.client;

import net.minecraft.item.*;
import net.minecraft.client.gui.recipebook.*;
import net.minecraftforge.common.*;
import com.google.common.collect.*;
import net.minecraft.util.math.vector.*;
import net.minecraft.client.*;
import java.util.*;
import com.mojang.blaze3d.matrix.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.screen.*;
import net.minecraftforge.fml.client.gui.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraft.inventory.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.item.crafting.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.advancements.*;
import net.minecraft.advancements.*;

public class AdvancementsRecipeRenderer implements IRecipePlacer<Ingredient>
{
    private static final ResourceLocation GUI_TEXTURE_CRAFTING_TABLE;
    private static final ResourceLocation GUI_TEXTURE_FURNACE;
    private static final ResourceLocation GUI_TEXTURE_BLAST_FURNACE;
    private static final ResourceLocation GUI_TEXTURE_SMOKER;
    private static final ResourceLocation GUI_TEXTURE_SMITHING;
    private static final ResourceLocation GUI_TEXTURE_ANVIL;
    public static ItemStack target;
    static final IRecipeType dummy_anvilType;
    public static GhostRecipe gr;
    public static ResourceLocation currentRecipe;
    static RecipeView currentView;
    static Map<IRecipeType, RecipeView> typeRecipeViewMap;
    
    public static AdvancementsRecipeRenderer getInstance() {
        return SingletonHolder.instance;
    }
    
    private AdvancementsRecipeRenderer() {
    }
    
    public void register() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    static IRecipe overrideDummyRecipe(final IRecipe original) {
        if (!(original instanceof SmithingRecipe)) {
            return original;
        }
        if (original.getId().getPath().startsWith("anvilcrafting")) {
            return (IRecipe)new DummyAnvilRecipe((SmithingRecipe)original);
        }
        return (IRecipe)new DummySmithingRecipe((SmithingRecipe)original);
    }
    
    static Map<IRecipeType, RecipeView> createRecipeViewMap() {
        final Map<IRecipeType, RecipeView> map = (Map<IRecipeType, RecipeView>)Maps.newHashMap();
        List<Vector3i> list = (List<Vector3i>)Lists.newArrayList();
        list.add(new Vector3i(124, 35, 0));
        final int SlotMargin = 18;
        final int LeftMargin = 30;
        final int TopMargin = 17;
        final int RecipeGridX = 3;
        final int RecipeGridY = 3;
        for (int i = 0; i < RecipeGridX; ++i) {
            for (int j = 0; j < RecipeGridY; ++j) {
                list.add(new Vector3i(LeftMargin + j * SlotMargin, TopMargin + i * SlotMargin, 0));
            }
        }
        final IRecipeType key = IRecipeType.CRAFTING;
        map.put(key, new RecipeView(key, AdvancementsRecipeRenderer.GUI_TEXTURE_CRAFTING_TABLE, list));
        list = (List<Vector3i>)Lists.newArrayList();
        list.add(new Vector3i(116, 35, 0));
        list.add(new Vector3i(56, 17, 0));
        list.add(new Vector3i(56, 53, 0));
        IRecipeType key2 = IRecipeType.SMELTING;
        map.put(key2, new RecipeView(key2, AdvancementsRecipeRenderer.GUI_TEXTURE_FURNACE, list));
        key2 = IRecipeType.BLASTING;
        map.put(key2, new RecipeView(key2, AdvancementsRecipeRenderer.GUI_TEXTURE_BLAST_FURNACE, list));
        key2 = IRecipeType.SMOKING;
        map.put(key2, new RecipeView(key2, AdvancementsRecipeRenderer.GUI_TEXTURE_SMOKER, list));
        list = (List<Vector3i>)Lists.newArrayList();
        list.add(new Vector3i(134, 47, 0));
        list.add(new Vector3i(27, 47, 0));
        list.add(new Vector3i(76, 47, 0));
        key2 = IRecipeType.SMITHING;
        map.put(key2, new RecipeView(key2, AdvancementsRecipeRenderer.GUI_TEXTURE_SMITHING, list, false));
        key2 = AdvancementsRecipeRenderer.dummy_anvilType;
        map.put(key2, new RecipeView(key2, AdvancementsRecipeRenderer.GUI_TEXTURE_ANVIL, list, false));
        return map;
    }
    
    public void addItemToSlot(final Iterator<Ingredient> ingredients, final int slotIn, final int maxAmount, final int y, final int x) {
        final Ingredient ingredient = ingredients.next();
        if (!ingredient.isEmpty() && slotIn < AdvancementsRecipeRenderer.currentView.slots.size()) {
            final Vector3i slot = AdvancementsRecipeRenderer.currentView.slots.get(slotIn);
            AdvancementsRecipeRenderer.gr.addIngredient(ingredient, slot.getX(), slot.getY());
        }
    }
    
    void setGhostRecipe(final ResourceLocation loc) {
        if (!Objects.equals(loc, AdvancementsRecipeRenderer.currentRecipe)) {
            AdvancementsRecipeRenderer.currentRecipe = loc;
            final Optional<? extends IRecipe<?>> recipe = (Optional<? extends IRecipe<?>>)Minecraft.getInstance().level.getRecipeManager().byKey(loc);
            if (recipe.isPresent()) {
                AdvancementsRecipeRenderer.gr.clear();
                IRecipe<?> iRecipe = (IRecipe<?>)recipe.get();
                iRecipe = (IRecipe<?>)overrideDummyRecipe(iRecipe);
                AdvancementsRecipeRenderer.gr.setRecipe((IRecipe)iRecipe);
                AdvancementsRecipeRenderer.currentView = AdvancementsRecipeRenderer.typeRecipeViewMap.get(iRecipe.getType());
                if (AdvancementsRecipeRenderer.currentView != null && 0 < AdvancementsRecipeRenderer.currentView.slots.size()) {
                    final int outputslotIndex = 0;
                    final Vector3i outputSlot = AdvancementsRecipeRenderer.currentView.slots.get(0);
                    AdvancementsRecipeRenderer.gr.addIngredient(Ingredient.of(new ItemStack[] { iRecipe.getResultItem() }), outputSlot.getX(), outputSlot.getY());
                    this.placeRecipe(3, 3, 0, (IRecipe)iRecipe, iRecipe.getIngredients().iterator(), 1);
                }
            }
            else {
                AdvancementsRecipeRenderer.gr.clear();
            }
        }
    }
    
    void drawBackGround(final MatrixStack matrixStack, final int xCorner, final int yCorner, final int zOffset, final int xSize, final int ySize, final int yClip) {
        Minecraft.getInstance().getTextureManager().bind(AdvancementsRecipeRenderer.currentView.background);
        final int bPadding = 5;
        AbstractGui.blit(matrixStack, xCorner, yCorner, zOffset, 0.0f, 0.0f, xSize, yClip - bPadding, 256, 256);
        AbstractGui.blit(matrixStack, xCorner, yCorner + yClip - bPadding, zOffset, 0.0f, (float)(ySize - bPadding), xSize, bPadding, 256, 256);
    }
    
    void drawGhostRecipe(final MatrixStack matrixStack, final int xCorner, final int yCorner, final int zOffset, final float partialTicks) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, 0.0f, (float)zOffset);
        final ItemRenderer ir = Minecraft.getInstance().getItemRenderer();
        final float tmp = ir.blitOffset;
        ir.blitOffset = (float)(zOffset - 125);
        final int padding = 5;
        ir.renderAndDecorateFakeItem(AdvancementsRecipeRenderer.gr.getRecipe().getToastSymbol(), xCorner + padding, yCorner + padding);
        final boolean wideOutputSlot = AdvancementsRecipeRenderer.currentView.isWideOutputSlot;
        AdvancementsRecipeRenderer.gr.render(matrixStack, Minecraft.getInstance(), xCorner, yCorner, wideOutputSlot, partialTicks);
        ir.blitOffset = tmp;
        RenderSystem.popMatrix();
    }
    
    void drawTooltip(final MatrixStack matrixStack, final int xCorner, final int yCorner, final int zOffset, final int mouseX, final int mouseY, final Screen gui) {
        ItemStack itemStack = null;
        final int slotSize = 16;
        for (int i = 0; i < AdvancementsRecipeRenderer.gr.size(); ++i) {
            final GhostRecipe.GhostIngredient ghostIngredient = AdvancementsRecipeRenderer.gr.get(i);
            final int j = ghostIngredient.getX() + xCorner;
            final int k = ghostIngredient.getY() + yCorner;
            if (mouseX >= j && mouseY >= k && mouseX < j + slotSize && mouseY < k + slotSize) {
                itemStack = ghostIngredient.getItem();
            }
        }
        if (itemStack != null) {
            final FontRenderer font = Optional.ofNullable(itemStack.getItem().getFontRenderer(itemStack)).orElseGet(() -> Minecraft.getInstance().font);
            GuiUtils.preItemToolTip(itemStack);
            gui.renderWrappedToolTip(matrixStack, gui.getTooltipFromItem(itemStack), mouseX, mouseY, font);
            GuiUtils.postItemToolTip();
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onDrawScreenPost(final GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!(event.getGui() instanceof AdvancementsScreen)) {
            return;
        }
        final AdvancementsScreen gui = (AdvancementsScreen)event.getGui();
        if (AdvancementsRecipeRenderer.target != null) {
            try {
                event.getMatrixStack().pushPose();
                ResourceLocation rrl = null;
                if (AdvancementsRecipeRenderer.target.hasTag() && AdvancementsRecipeRenderer.target.getTag().contains("Crafting")) {
                    rrl = new ResourceLocation(AdvancementsRecipeRenderer.target.getTag().getString("Crafting"));
                }
                this.setGhostRecipe(rrl);
                if (AdvancementsRecipeRenderer.currentRecipe == null) {
                    return;
                }
                final MatrixStack matrixStack = event.getMatrixStack();
                final int zOffset = 425;
                final int zStep = 75;
                matrixStack.translate(0.0, 0.0, (double)zOffset);
                final int xSize = 176;
                final int ySize = 166;
                final int yClip = 85;
                final int xCorner = (gui.width - xSize) / 2;
                final int yCorner = (gui.height - yClip) / 2;
                this.drawBackGround(matrixStack, xCorner, yCorner, zOffset, xSize, ySize, yClip);
                this.drawGhostRecipe(matrixStack, xCorner, yCorner, zOffset, event.getRenderPartialTicks());
                matrixStack.translate(0.0, 0.0, (double)zStep);
                this.drawTooltip(matrixStack, xCorner, yCorner, zOffset, event.getMouseX(), event.getMouseY(), (Screen)gui);
            }
            finally {
                event.getMatrixStack().popPose();
            }
        }
    }
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onInitGuiPost(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.getGui() instanceof AdvancementsScreen)) {
            return;
        }
        final AdvancementsScreen gui = (AdvancementsScreen)event.getGui();
        gui.children().add(new AdvancementsExGuiEventListener(gui));
    }
    
    static {
        GUI_TEXTURE_CRAFTING_TABLE = new ResourceLocation("textures/gui/container/crafting_table.png");
        GUI_TEXTURE_FURNACE = new ResourceLocation("textures/gui/container/furnace.png");
        GUI_TEXTURE_BLAST_FURNACE = new ResourceLocation("textures/gui/container/blast_furnace.png");
        GUI_TEXTURE_SMOKER = new ResourceLocation("textures/gui/container/smoker.png");
        GUI_TEXTURE_SMITHING = new ResourceLocation("textures/gui/container/smithing.png");
        GUI_TEXTURE_ANVIL = new ResourceLocation("textures/gui/container/anvil.png");
        AdvancementsRecipeRenderer.target = null;
        dummy_anvilType = IRecipeType.register("sb_forgeing");
        AdvancementsRecipeRenderer.gr = new GhostRecipe();
        AdvancementsRecipeRenderer.currentRecipe = null;
        AdvancementsRecipeRenderer.currentView = null;
        AdvancementsRecipeRenderer.typeRecipeViewMap = createRecipeViewMap();
    }
    
    private static final class SingletonHolder
    {
        private static final AdvancementsRecipeRenderer instance;
        
        static {
            instance = new AdvancementsRecipeRenderer(null);
        }
    }
    
    static class DummyAnvilRecipe implements IRecipe<IInventory>
    {
        protected SmithingRecipe original;
        private final ItemStack result;
        private final ResourceLocation recipeId;
        NonNullList<Ingredient> nonnulllist;
        
        public DummyAnvilRecipe(final SmithingRecipe recipe) {
            this.nonnulllist = (NonNullList<Ingredient>)NonNullList.withSize(2, (Object)Ingredient.EMPTY);
            this.original = recipe;
            final PacketBuffer pb = new PacketBuffer(Unpooled.buffer());
            final SmithingRecipe.Serializer ss = new SmithingRecipe.Serializer();
            ss.toNetwork(pb, this.original);
            this.nonnulllist.set(0, (Object)Ingredient.fromNetwork(pb));
            this.nonnulllist.set(1, (Object)Ingredient.fromNetwork(pb));
            this.result = pb.readItem();
            this.recipeId = this.original.getId();
        }
        
        public boolean matches(final IInventory inv, final World worldIn) {
            return false;
        }
        
        public ItemStack assemble(final IInventory inv) {
            return this.result.copy();
        }
        
        public boolean canCraftInDimensions(final int width, final int height) {
            return false;
        }
        
        public ItemStack getResultItem() {
            return this.result;
        }
        
        public NonNullList<Ingredient> getIngredients() {
            return this.nonnulllist;
        }
        
        public ItemStack getToastSymbol() {
            return new ItemStack((IItemProvider)Blocks.ANVIL);
        }
        
        public ResourceLocation getId() {
            return this.recipeId;
        }
        
        public IRecipeSerializer<?> getSerializer() {
            return null;
        }
        
        public IRecipeType<?> getType() {
            return (IRecipeType<?>)AdvancementsRecipeRenderer.dummy_anvilType;
        }
    }
    
    static class DummySmithingRecipe extends DummyAnvilRecipe
    {
        public DummySmithingRecipe(final SmithingRecipe recipe) {
            super(recipe);
        }
        
        @Override
        public ItemStack getToastSymbol() {
            return this.original.getToastSymbol();
        }
        
        @Override
        public IRecipeType<?> getType() {
            return (IRecipeType<?>)this.original.getType();
        }
    }
    
    public static class RecipeView
    {
        final IRecipeType recipeType;
        final ResourceLocation background;
        List<Vector3i> slots;
        final boolean isWideOutputSlot;
        
        public RecipeView(final IRecipeType recipeType, final ResourceLocation background, final List<Vector3i> slots) {
            this(recipeType, background, slots, true);
        }
        
        public RecipeView(final IRecipeType recipeType, final ResourceLocation background, final List<Vector3i> slots, final boolean isWideOutputSlot) {
            this.slots = (List<Vector3i>)Lists.newArrayList();
            this.recipeType = recipeType;
            this.background = background;
            this.slots = slots;
            this.isWideOutputSlot = isWideOutputSlot;
        }
    }
    
    public static class AdvancementsExGuiEventListener implements IGuiEventListener
    {
        AdvancementsScreen screen;
        
        public AdvancementsExGuiEventListener(final AdvancementsScreen screen) {
            this.screen = screen;
        }
        
        public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
            if (button == 1) {
                AdvancementsRecipeRenderer.target = null;
                AdvancementsRecipeRenderer.currentRecipe = null;
                AdvancementsRecipeRenderer.gr.clear();
                return false;
            }
            final int offsetX = (this.screen.width - 252) / 2;
            final int offsetY = (this.screen.height - 140) / 2;
            ItemStack found = null;
            final AdvancementTabGui selectedTab = this.screen.selectedTab;
            final int mouseXX = (int)(mouseX - offsetX - 9.0);
            final int mouseYY = (int)(mouseY - offsetY - 18.0);
            final double scrollX = selectedTab.scrollX;
            final double scrollY = selectedTab.scrollY;
            final Map<Advancement, AdvancementEntryGui> guis = (Map<Advancement, AdvancementEntryGui>)selectedTab.widgets;
            final int i = MathHelper.floor(scrollX);
            final int j = MathHelper.floor(scrollY);
            if (mouseXX > 0 && mouseXX < 234 && mouseYY > 0 && mouseYY < 113) {
                for (final AdvancementEntryGui advancemententrygui : guis.values()) {
                    if (advancemententrygui.isMouseOver(i, j, mouseXX, mouseYY)) {
                        final DisplayInfo info = advancemententrygui.display;
                        found = info.getIcon();
                        break;
                    }
                }
            }
            AdvancementsRecipeRenderer.target = found;
            return false;
        }
    }
}
