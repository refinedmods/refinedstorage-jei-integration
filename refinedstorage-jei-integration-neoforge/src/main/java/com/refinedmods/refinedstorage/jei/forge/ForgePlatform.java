package com.refinedmods.refinedstorage.jei.forge;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.support.resource.FluidResource;
import com.refinedmods.refinedstorage.jei.common.Platform;

import java.util.Optional;

import net.neoforged.neoforge.fluids.FluidStack;

import static com.refinedmods.refinedstorage.neoforge.support.resource.VariantUtil.ofFluidStack;

public class ForgePlatform implements Platform {
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
}
