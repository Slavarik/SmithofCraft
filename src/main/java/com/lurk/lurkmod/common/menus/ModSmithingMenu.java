package com.lurk.lurkmod.common.menus;

import com.lurk.lurkmod.LurkMod;
import com.lurk.lurkmod.item.IModTool;
import com.lurk.lurkmod.item.ItemSet;
import com.lurk.lurkmod.recipe.ModSmithingRecipe;
import com.lurk.lurkmod.registries.ModMenuTypes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class ModSmithingMenu extends RecipeBookMenu<CraftingContainer> {
    public static final int RESULT_SLOT = 0;
    public static final ResourceLocation EMPTY_SLOT_HAMMER = new ResourceLocation(LurkMod.MODID, "item/empty_hammer");
    public static final ResourceLocation EMPTY_SLOT_CHISEL = new ResourceLocation(LurkMod.MODID, "item/empty_chisel");
    public static final ResourceLocation EMPTY_SLOT_FILE = new ResourceLocation(LurkMod.MODID, "item/empty_file");
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_END = 10;
    private static final int INV_SLOT_START = 10;
    private static final int INV_SLOT_END = 37;
    private static final int USE_ROW_SLOT_START = 37;
    private static final int USE_ROW_SLOT_END = 46;
    private final int craftWidth = 3;
    private final int craftHeight = 3;
    private final CraftingContainer craftSlots = new CraftingContainer(this, craftWidth, craftHeight);
    private final CraftingContainer toolsSlots = new CraftingContainer(this, 1, 3);
    private final ResultContainer resultSlots = new ResultContainer();
    private final Player player;

    public ModSmithingMenu(int i, Inventory inventory, Player player) {
        this(i, inventory);
    }

    public ModSmithingMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory);
    }

    public ModSmithingMenu(int pContainerId, Inventory pPlayerInventory) {
        super(ModMenuTypes.MENU_TYPE_SMITHING.get(), pContainerId);
        this.player = pPlayerInventory.player;

        this.addSlot(new ResultSlot(pPlayerInventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for (int j = 0; j < 3; ++j) {
            int finalJ = j;
            this.addSlot(new Slot(this.toolsSlots, finalJ, 8, 17 + j * 18) {
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    if (finalJ == 0) return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_HAMMER);
                    if (finalJ == 1) return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_CHISEL);
                    if (finalJ == 2) return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_FILE);
                    return null;
                }

                @Override
                public boolean mayPlace(ItemStack pStack) {
                    if (!(pStack.getItem() instanceof IModTool imt)) return false;
                    if (getSlotIndex() == 0) return imt.getType() == IModTool.ToolType.HAMMER;
                    if (getSlotIndex() == 1) return imt.getType() == IModTool.ToolType.CHISEL;
                    if (getSlotIndex() == 2) return imt.getType() == IModTool.ToolType.FILE;
                    return false;
                }
            });
        }


        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(pPlayerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(pPlayerInventory, l, 8 + l * 18, 142));
        }
    }


    public static boolean isHotbarSlot(int pIndex) {
        return pIndex >= 36 && pIndex < 45 || pIndex == 45;
    }

    void slotChangedCraftingGrid(AbstractContainerMenu pMenu, Level pLevel, Player pPlayer, CraftingContainer pContainer, ResultContainer pResult) {
        if (!pLevel.isClientSide) {
            ServerPlayer serverplayer = (ServerPlayer) pPlayer;
            ItemStack result = ItemStack.EMPTY;

            Optional<ModSmithingRecipe> optional = pLevel.getServer().getRecipeManager().getRecipeFor(ModSmithingRecipe.Type.INSTANCE, pContainer, pLevel);

            if (optional.isPresent()) {
                ModSmithingRecipe ModSmithingRecipe = optional.get();
                if (pResult.setRecipeUsed(pLevel, serverplayer, ModSmithingRecipe)) {
                    ItemStack itemstack1 = ModSmithingRecipe.assemble(pContainer);
                        if (sufficientTools(itemstack1))
                            if (itemstack1.isItemEnabled(pLevel.enabledFeatures())) {
                                result = itemstack1;
                                System.out.println("Found result : " + result.getItem().getDescriptionId());
                            }
                }
            }

            pResult.setItem(0, result);
            pMenu.setRemoteSlot(0, result);
            serverplayer.connection.send(new ClientboundContainerSetSlotPacket(pMenu.containerId, pMenu.incrementStateId(), 0, result));
        }
    }

    boolean sufficientTools(ItemStack result) {
        int tier = getTier(result);
        if (tier == 0) {
            return true;
        }
        if (tier == 1) {
            return toolsSlots.getItem(0) != ItemStack.EMPTY;
        } else if (tier == 2) {
            return toolsSlots.getItem(0) != ItemStack.EMPTY && toolsSlots.getItem(1) != ItemStack.EMPTY;
        } else {
            return toolsSlots.getItem(0) != ItemStack.EMPTY && toolsSlots.getItem(1) != ItemStack.EMPTY && toolsSlots.getItem(2) != ItemStack.EMPTY;
        }
    }

    int getTier(ItemStack stack) {
        Item item = stack.getItem();
        for (ItemSet set : ItemSet.sets)
            for (RegistryObject<? extends Item> ro : set.getAll()) {
                if (ro.get().equals(item))
                    return set.tier;
            }
        if (item == LurkMod.topazRing.get()) return 0;
        if (item == LurkMod.rubyRing.get()) return 2;
        if (item == LurkMod.emeraldRing.get()) return 1;
        if (item == LurkMod.diamondRing.get()) return 3;
        return 0;
    }


    public void fillCraftSlotsStackedContents(StackedContents pItemHelper) {
        this.craftSlots.fillStackedContents(pItemHelper);
    }

    public void clearCraftingContent() {
        this.resultSlots.clearContent();
        this.craftSlots.clearContent();
    }

    public boolean recipeMatches(Recipe<? super CraftingContainer> pRecipe) {
        return pRecipe.matches(this.craftSlots, this.player.level);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void slotsChanged(Container pInventory) {
        slotChangedCraftingGrid(this, this.player.level, this.player, this.craftSlots, this.resultSlots);
    }

    /**
     * Called when the container is closed.
     */
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.resultSlots.clearContent();
        if (!pPlayer.level.isClientSide) {
            this.clearContainer(pPlayer, this.craftSlots);
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
            if (pIndex == 0) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex >= 1 && pIndex < 5) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 5 && pIndex < 9) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - equipmentslot.getIndex()).hasItem()) {
                int i = 8 - equipmentslot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot == EquipmentSlot.OFFHAND && !this.slots.get(45).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 9 && pIndex < 36) {
                if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 36 && pIndex < 45) {
                if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            if (pIndex == 0) {
                pPlayer.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is
     * null for the initial slot that was double-clicked.
     */
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultSlots && super.canTakeItemForPickAll(pStack, pSlot);
    }

    public int getResultSlotIndex() {
        return 0;
    }

    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    public int getSize() {
        return 5;
    }

    public CraftingContainer getCraftSlots() {
        return this.craftSlots;
    }

    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public boolean shouldMoveToInventory(int pSlotIndex) {
        return pSlotIndex != this.getResultSlotIndex();
    }
}