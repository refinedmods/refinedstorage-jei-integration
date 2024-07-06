package com.refinedmods.refinedstorage.jei.fabric;

import com.refinedmods.refinedstorage.jei.common.Platform;
import com.refinedmods.refinedstorage.platform.common.support.resource.FluidResource;

import java.util.Optional;

import mezz.jei.api.fabric.ingredients.fluids.IJeiFluidIngredient;

import static com.refinedmods.refinedstorage.platform.fabric.support.resource.VariantUtil.ofFluidVariant;

public class FabricPlatform implements Platform {
    @Override
    public Optional<FluidResource> convertJeiIngredientToFluid(final Object ingredient) {
        if (ingredient instanceof IJeiFluidIngredient fluidIngredient) {
            return Optional.of(ofFluidVariant(fluidIngredient.getFluidVariant()));
        }
        return Optional.empty();
    }
}
