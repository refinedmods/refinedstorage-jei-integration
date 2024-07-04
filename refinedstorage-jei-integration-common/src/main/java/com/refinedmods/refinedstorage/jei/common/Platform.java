package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.platform.common.support.resource.FluidResource;

import java.util.Optional;

public interface Platform {
    Optional<FluidResource> convertJeiIngredientToFluid(Object ingredient);
}
