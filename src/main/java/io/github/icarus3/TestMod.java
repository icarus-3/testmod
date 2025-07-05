package io.github.icarus3;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TestMod.MODID)
public class TestMod
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "testmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // 注册测试方块
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    // 注册测试方块对应的物品方块(在MC中，方块拿在手上或者放在背包里时不算是方块，而是"物品"，因此需要注册物品我们才能将此方块拿在手上、放在背包中，或是在创造模式物品栏中找到)
    // 定义ITEMS方法
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // 注册一个方块myblock
    private static final RegistryObject<Block> myblock = BLOCKS.register("myblock", () -> new Block(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.CROP)));
    // 注册myblock对应的物品方块(BlockItem)
    private static final RegistryObject<Item> myblockitem = ITEMS.register("myblock", () -> new BlockItem(myblock.get(), new Item.Properties()));

    // 注册一个仅为物品的方块(遥控器)
    private static final RegistryObject<Item> handset = ITEMS.register("handset", () -> new Item(new Item.Properties()));

    public TestMod() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

}
