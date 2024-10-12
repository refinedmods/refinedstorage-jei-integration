package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.content.Items;
import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;
import com.refinedmods.refinedstorage.common.support.AbstractBaseScreen;

import java.util.function.Supplier;
import javax.annotation.Nullable;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IIngredientAliasRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import static com.refinedmods.refinedstorage.jei.common.Common.MOD_ID;

@JeiPlugin
public class RefinedStorageModPlugin implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MOD_ID, "plugin");
    @Nullable
    private static IJeiRuntime runtime;

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerIngredientAliases(final IIngredientAliasRegistration registration) {
        registration.addAliases(VanillaTypes.ITEM_STACK, Items.INSTANCE.getDiskInterfaces()
            .stream()
            .map(Supplier::get)
            .map(Item::getDefaultInstance)
            .toList(), "alias.%s.refinedstorage1_disk_manipulator".formatted(MOD_ID));
    }

    @Override
    public void registerRecipeTransferHandlers(final IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new CraftingGridRecipeTransferHandler(), RecipeTypes.CRAFTING);
        registration.addRecipeTransferHandler(new PatternGridCraftingRecipeTransferHandler(), RecipeTypes.CRAFTING);
        registration.addRecipeTransferHandler(
            new PatternGridStonecutterRecipeTransferHandler(),
            RecipeTypes.STONECUTTING
        );
        registration.addRecipeTransferHandler(
            new PatternGridSmithingTableRecipeTransferHandler(),
            RecipeTypes.SMITHING
        );
        registration.addUniversalRecipeTransferHandler(new PatternGridProcessingRecipeTransferHandler());
    }

    @Override
    public void onRuntimeAvailable(final IJeiRuntime newRuntime) {
        RefinedStorageModPlugin.runtime = newRuntime;
    }

    @Override
    public void onRuntimeUnavailable() {
        RefinedStorageModPlugin.runtime = null;
    }

    @Override
    public void registerGuiHandlers(final IGuiHandlerRegistration registration) {
        registration.addGenericGuiContainerHandler(
            AbstractBaseScreen.class,
            new ResourceGuiContainerHandler(registration.getJeiHelpers().getIngredientManager())
        );
        registration.addGenericGuiContainerHandler(
            AbstractGridScreen.class,
            new GridGuiContainerHandler(registration.getJeiHelpers().getIngredientManager())
        );
        registration.addGenericGuiContainerHandler(
            AbstractBaseScreen.class,
            new ExclusionZonesGuiContainerHandler()
        );
        registration.addGhostIngredientHandler(AbstractBaseScreen.class, new ResourceGhostIngredientHandler());
        registration.addGhostIngredientHandler(AbstractBaseScreen.class, new FilterGhostIngredientHandler());
    }

    @Nullable
    public static IJeiRuntime getRuntime() {
        return runtime;
    }
}
