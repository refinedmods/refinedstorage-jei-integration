package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;
import com.refinedmods.refinedstorage.common.support.AbstractBaseScreen;

import javax.annotation.Nullable;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class RefinedStorageModPlugin implements IModPlugin {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Common.MOD_ID, "plugin");
    @Nullable
    private static IJeiRuntime runtime;

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipeTransferHandlers(final IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new CraftingGridRecipeTransferHandler(), RecipeTypes.CRAFTING);
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
        registration.addGhostIngredientHandler(AbstractBaseScreen.class, new GhostIngredientHandler());
    }

    @Nullable
    public static IJeiRuntime getRuntime() {
        return runtime;
    }
}
