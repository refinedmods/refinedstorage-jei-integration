package com.refinedmods.refinedstorage.jei.fabric;

import com.refinedmods.refinedstorage.jei.common.Platform;

import java.util.Optional;

import com.refinedmods.refinedstorage2.platform.common.support.resource.FluidResource;
import mezz.jei.api.fabric.ingredients.fluids.IJeiFluidIngredient;

public class FabricPlatform implements Platform {
    @Override
    public Optional<FluidResource> convertJeiIngredientToFluid(final Object ingredient) {
        if (ingredient instanceof IJeiFluidIngredient fluidIngredient) {
            return Optional.of(new FluidResource(
                fluidIngredient.getFluid(),
                fluidIngredient.getTag().orElse(null)
            ));
        }
        return Optional.empty();
    }
}
