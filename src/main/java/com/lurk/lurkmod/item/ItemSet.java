package com.lurk.lurkmod.item;

import com.lurk.lurkmod.registries.ModArmorMaterial;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.RegistryObject;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

import static com.lurk.lurkmod.LurkMod.BLOCKS;
import static com.lurk.lurkmod.LurkMod.ITEMS;

public class ItemSet {

    public static ArrayList<ItemSet> sets = new ArrayList<>();

    public ModArmorMaterial armorMaterial;

    public String name;
    public RegistryObject<Block> oreBlock;
    public RegistryObject<BlockItem> oreItem;
    public RegistryObject<Item> ingot;

    public RegistryObject<ArmorItem>helmet;
    public RegistryObject<ArmorItem>chestplate;
    public RegistryObject<ArmorItem>leggings;
    public RegistryObject<ArmorItem>boots;

    public RegistryObject<HoeItem>hoe;
    public RegistryObject<AxeItem>axe;
    public RegistryObject<ShovelItem>shovel;
    public RegistryObject<PickaxeItem>pickaxe;
    public RegistryObject<SwordItem>sword;

    public RegistryObject<IModTool> hammer;
    public RegistryObject<IModTool> chisel;
    public RegistryObject<IModTool> file;

    public int tier;

    public ItemSet(String name, int tier, int[] defence){
        sets.add(this);
        this.name = name;
        this.tier = tier;
        this.armorMaterial = new ModArmorMaterial(name,15 + 2 * tier, defence,9, SoundEvents.ARMOR_EQUIP_IRON,0.0F, 0.0F, () -> {
            return Ingredient.of(ingot.get());
        });
        oreBlock = BLOCKS.register(name + "_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F * tier, 3.0F)));
        oreItem = ITEMS.register(name + "_ore", () -> new BlockItem(oreBlock.get(), new Item.Properties()));
        ingot = ITEMS.register(name + "_ingot", () -> new Item(new Item.Properties()));

        helmet = ITEMS.register(name + "_helmet", () -> new ArmorItem(armorMaterial, EquipmentSlot.HEAD, new Item.Properties()));
        chestplate = ITEMS.register(name + "_chestplate", () -> new ArmorItem(armorMaterial, EquipmentSlot.CHEST, new Item.Properties()));
        leggings = ITEMS.register(name + "_leggings", () -> new ArmorItem(armorMaterial, EquipmentSlot.LEGS, new Item.Properties()));
        boots = ITEMS.register(name + "_boots", () -> new ArmorItem(armorMaterial, EquipmentSlot.FEET, new Item.Properties()));

        hoe = ITEMS.register(name + "_hoe", () -> new HoeItem(Tiers.DIAMOND, 2 + 1 * tier, 1f, new Item.Properties()));
        axe = ITEMS.register(name + "_axe", () -> new AxeItem(Tiers.DIAMOND, 2 + 1 * tier, 1f, new Item.Properties()));
        shovel = ITEMS.register(name + "_shovel", () -> new ShovelItem(Tiers.DIAMOND, 2 + 1 * tier, 1f, new Item.Properties()));

        pickaxe = ITEMS.register(name + "_pickaxe", () -> new PickaxeItem(getByLevel(tier), 2 + 1 * tier, 1f, new Item.Properties()));

        sword = ITEMS.register(name + "_sword", () -> new SwordItem(Tiers.DIAMOND, 2 + 1 * tier, 1f, new Item.Properties()));
        hammer = ITEMS.register(name + "_hammer", ()-> new IModTool(new Item.Properties()) {
            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public ToolType getType() {
                return ToolType.HAMMER;
            }
        });

        file = ITEMS.register(name + "_file", ()-> new IModTool(new Item.Properties()) {
            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public ToolType getType() {
                return ToolType.FILE;
            }
        });

        chisel = ITEMS.register(name + "_chisel", ()-> new IModTool(new Item.Properties()) {
            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public ToolType getType() {
                return ToolType.CHISEL;
            }
        });
    }

    public void accept(CreativeModeTabEvent.BuildContents event){
        event.accept(oreBlock);
        for (RegistryObject<? extends Item>r : getAll()) {
            event.accept(r);
        }
    }

    static Tiers getByLevel(int level){
        if (level==0)return Tiers.STONE;
        if (level==1)return Tiers.IRON;
        return Tiers.DIAMOND;
    }

    public ArrayList<RegistryObject<? extends Item>> getAll(){
        ArrayList<RegistryObject<? extends Item>> list = new ArrayList<>();
        list.add(oreItem);
        list.add(ingot);
        list.add(helmet);
        list.add(chestplate);
        list.add(leggings);
        list.add(boots);
        list.add(hoe);
        list.add(axe);
        list.add(shovel);
        list.add(pickaxe);
        list.add(sword);
        list.add(hammer);
        list.add(file);
        list.add(chisel);
        return list;
    }


}
