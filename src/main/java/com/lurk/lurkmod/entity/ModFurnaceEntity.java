package com.lurk.lurkmod.entity;

import com.lurk.lurkmod.LurkMod;
import com.lurk.lurkmod.common.menus.ModFurnaceMenu;
import com.lurk.lurkmod.item.ItemSet;
import com.lurk.lurkmod.registries.ModBlockEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.CrashReportCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

public class ModFurnaceEntity extends BlockEntity implements MenuProvider {
    static Random random = new Random();
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    public int currentTick = 0;
    public int temperature = 20;
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public ModFurnaceEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.GEM_CUTTING_STATION_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ModFurnaceEntity pBlockEntity) {
        pBlockEntity.currentTick++;
        if (pBlockEntity.currentTick % 60 == 0) {
            if (hasNotReachedStackLimit(pBlockEntity) && pBlockEntity.itemHandler.getStackInSlot(1) != ItemStack.EMPTY
                    && updateTemperature(pBlockEntity)) {
                craftItem(pBlockEntity);
                pBlockEntity.currentTick = 0;
            }
        }
    }

    private static boolean updateTemperature(ModFurnaceEntity entity){
        ItemStack bucket = entity.itemHandler.getStackInSlot(0);
        if (bucket.getItem() != Items.LAVA_BUCKET) {
            entity.temperature = Math.max(0, entity.temperature - 3);
            return entity.temperature >= 150;
        }
        CompoundTag tag = bucket.getTag();
        if (tag==null){
            tag = new CompoundTag();
            tag.putInt("power", 20);
        }
        entity.temperature += random.nextInt(20) + 5;
        if (entity.temperature > 300) entity.temperature = 300;
        tag.putInt("power", tag.getInt("power") - 1);
        bucket.setTag(tag);
        if (tag.getInt("power") <= 0)
            entity.itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
        return entity.temperature >= 150;
    }

    private static void craftItem(ModFurnaceEntity entity) {

        ItemSet set = null;
        for (ItemSet s : ItemSet.sets) {
            if (s.oreItem.get() == entity.itemHandler.getStackInSlot(1).getItem())
                set = s;
        }
        entity.itemHandler.extractItem(1, 1, false); //ore
        entity.itemHandler.setStackInSlot(2, new ItemStack(set.ingot.get(),
                entity.itemHandler.getStackInSlot(2).getCount() + 1));
    }

    private static boolean hasNotReachedStackLimit(ModFurnaceEntity entity) {
        return entity.itemHandler.getStackInSlot(2).getCount() < entity.itemHandler.getStackInSlot(2).getMaxStackSize();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new ModFurnaceMenu(pContainerId, pInventory, this);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return MutableComponent.create(new TranslatableContents("Megafurnace"));
    }
}