package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.quartzarsenal.common.wirelesscraftinggrid.WirelessCraftingGridContainerMenu;

import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeTransferRegistration;

public final class QuartzArsenalIntegration {
    private QuartzArsenalIntegration() {
    }

    public static boolean isLoaded() {
        try {
            Class.forName(
                "com.refinedmods.refinedstorage.quartzarsenal.common.wirelesscraftinggrid"
                    + ".WirelessCraftingGridContainerMenu"
            );
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }

    public static void load(final IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new CraftingGridRecipeTransferHandler<>(
            WirelessCraftingGridContainerMenu.class
        ), RecipeTypes.CRAFTING);
    }
}
