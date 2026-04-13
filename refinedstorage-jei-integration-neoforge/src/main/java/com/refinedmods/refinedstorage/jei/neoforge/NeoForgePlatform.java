package com.refinedmods.refinedstorage.jei.neoforge;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.support.resource.FluidResource;
import com.refinedmods.refinedstorage.jei.common.Platform;

import java.util.Optional;

import net.neoforged.neoforge.fluids.FluidStack;

public class NeoForgePlatform implements Platform {
    @Override
    public Optional<FluidResource> convertJeiIngredientToFluid(final Object ingredient) {
        if (ingredient instanceof FluidStack fluidStack) {
            return Optional.of(ofFluidStack(fluidStack));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ResourceAmount> convertJeiIngredientToFluidAmount(final Object ingredient) {
        if (ingredient instanceof FluidStack fluidStack) {
            return Optional.of(new ResourceAmount(ofFluidStack(fluidStack), fluidStack.getAmount()));
        }
        return Optional.empty();
    }

    private static FluidResource ofFluidStack(final FluidStack fluidStack) {
        return new FluidResource(fluidStack.getFluid(), fluidStack.getComponentsPatch());
    }
}
