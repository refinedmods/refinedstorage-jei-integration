package com.refinedmods.refinedstorage.jei.fabric;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.support.resource.FluidResource;
import com.refinedmods.refinedstorage.jei.common.Platform;

import java.util.Optional;

import mezz.jei.api.fabric.ingredients.fluids.IJeiFluidIngredient;

import static com.refinedmods.refinedstorage.fabric.support.resource.VariantUtil.ofFluidVariant;

public class FabricPlatform implements Platform {
    @Override
    public Optional<FluidResource> convertJeiIngredientToFluid(final Object ingredient) {
        if (ingredient instanceof IJeiFluidIngredient fluidIngredient) {
            return Optional.of(ofFluidVariant(fluidIngredient.getFluidVariant()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ResourceAmount> convertJeiIngredientToFluidAmount(final Object ingredient) {
        if (ingredient instanceof IJeiFluidIngredient fluidIngredient) {
            return Optional.of(new ResourceAmount(
                ofFluidVariant(fluidIngredient.getFluidVariant()),
                fluidIngredient.getAmount()
            ));
        }
        return Optional.empty();
    }
}
