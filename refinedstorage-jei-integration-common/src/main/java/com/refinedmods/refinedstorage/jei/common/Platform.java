package com.refinedmods.refinedstorage.jei.common;

import java.util.Optional;

import com.refinedmods.refinedstorage2.platform.common.support.resource.FluidResource;

public interface Platform {
    Optional<FluidResource> convertJeiIngredientToFluid(Object ingredient);
}
