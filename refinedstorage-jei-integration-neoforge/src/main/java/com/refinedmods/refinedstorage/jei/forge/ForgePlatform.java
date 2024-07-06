package com.refinedmods.refinedstorage.jei.forge;

import com.refinedmods.refinedstorage.jei.common.Platform;
import com.refinedmods.refinedstorage.platform.common.support.resource.FluidResource;

import java.util.Optional;

import net.neoforged.neoforge.fluids.FluidStack;

import static com.refinedmods.refinedstorage.platform.neoforge.support.resource.VariantUtil.ofFluidStack;

public class ForgePlatform implements Platform {
    @Override
    public Optional<FluidResource> convertJeiIngredientToFluid(final Object ingredient) {
        if (ingredient instanceof FluidStack fluidStack) {
            return Optional.of(ofFluidStack(fluidStack));
        }
        return Optional.empty();
    }
}
