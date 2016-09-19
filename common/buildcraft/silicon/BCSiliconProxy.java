package buildcraft.silicon;

import buildcraft.api.recipes.AssemblyRecipe;
import buildcraft.api.recipes.IntegrationRecipe;
import buildcraft.lib.recipe.AssemblyRecipeRegistry;
import buildcraft.lib.recipe.IntegrationRecipeRegistry;
import buildcraft.silicon.client.render.RenderLaser;
import buildcraft.silicon.container.ContainerAdvancedCraftingTable;
import buildcraft.silicon.container.ContainerAssemblyTable;
import buildcraft.silicon.container.ContainerIntegrationTable;
import buildcraft.silicon.gui.GuiAdvancedCraftingTable;
import buildcraft.silicon.gui.GuiAssemblyTable;
import buildcraft.silicon.gui.GuiIntegrationTable;
import buildcraft.silicon.tile.TileAdvancedCraftingTable;
import buildcraft.silicon.tile.TileAssemblyTable;
import buildcraft.silicon.tile.TileIntegrationTable;
import buildcraft.silicon.tile.TileLaser;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BCSiliconProxy implements IGuiHandler {
    @SidedProxy
    private static BCSiliconProxy proxy;

    public static BCSiliconProxy getProxy() {
        return proxy;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
        if (ID == BCSiliconGuis.ASSEMBLY_TABLE.ordinal()) {
            if (tile instanceof TileAssemblyTable) {
                TileAssemblyTable assemblyTable = (TileAssemblyTable) tile;
                return new ContainerAssemblyTable(player, assemblyTable);
            }
        }
        if (ID == BCSiliconGuis.ADVANCED_CRAFTING_TABLE.ordinal()) {
            if (tile instanceof TileAdvancedCraftingTable) {
                TileAdvancedCraftingTable advancedCraftingTable = (TileAdvancedCraftingTable) tile;
                return new ContainerAdvancedCraftingTable(player, advancedCraftingTable);
            }
        }
        if (ID == BCSiliconGuis.INTEGRATION_TABLE.ordinal()) {
            if (tile instanceof TileIntegrationTable) {
                TileIntegrationTable integrationTable = (TileIntegrationTable) tile;
                return new ContainerIntegrationTable(player, integrationTable);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void fmlPreInit() {}

    public void fmlInit() {
        AssemblyRecipeRegistry.INSTANCE.addRecipe(new AssemblyRecipe(10000000, ImmutableSet.of(new ItemStack(Items.BAKED_POTATO)), new ItemStack(Items.APPLE)));
        AssemblyRecipeRegistry.INSTANCE.addRecipe(new AssemblyRecipe(10000000, ImmutableSet.of(new ItemStack(Items.BAKED_POTATO)), new ItemStack(Items.GOLDEN_APPLE)));
        AssemblyRecipeRegistry.INSTANCE.addRecipe(new AssemblyRecipe(10000000, ImmutableSet.of(new ItemStack(Items.BAKED_POTATO)), new ItemStack(Items.GOLDEN_APPLE, 1, 1)));
        IntegrationRecipeRegistry.INSTANCE.addRecipe(new IntegrationRecipe(10000000, new ItemStack(Items.BAKED_POTATO), ImmutableList.of(new ItemStack(Items.REDSTONE)), new ItemStack(Items.FIRE_CHARGE)));
    }

    public void fmlPostInit() {}

    @SideOnly(Side.SERVER)
    public static class ServerProxy extends BCSiliconProxy {

    }

    @SideOnly(Side.CLIENT)
    public static class ClientProxy extends BCSiliconProxy {
        @Override
        public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
            if (ID == BCSiliconGuis.ASSEMBLY_TABLE.ordinal()) {
                if (tile instanceof TileAssemblyTable) {
                    TileAssemblyTable assemblyTable = (TileAssemblyTable) tile;
                    return new GuiAssemblyTable(new ContainerAssemblyTable(player, assemblyTable));
                }
            }
            if (ID == BCSiliconGuis.ADVANCED_CRAFTING_TABLE.ordinal()) {
                if (tile instanceof TileAdvancedCraftingTable) {
                    TileAdvancedCraftingTable advancedCraftingTable = (TileAdvancedCraftingTable) tile;
                    return new GuiAdvancedCraftingTable(new ContainerAdvancedCraftingTable(player, advancedCraftingTable));
                }
            }
            if (ID == BCSiliconGuis.INTEGRATION_TABLE.ordinal()) {
                if (tile instanceof TileIntegrationTable) {
                    TileIntegrationTable integrationTable = (TileIntegrationTable) tile;
                    return new GuiIntegrationTable(new ContainerIntegrationTable(player, integrationTable));
                }
            }
            return null;
        }

        @Override
        public void fmlInit() {
            super.fmlInit();
            ClientRegistry.bindTileEntitySpecialRenderer(TileLaser.class, new RenderLaser());
        }
    }
}
