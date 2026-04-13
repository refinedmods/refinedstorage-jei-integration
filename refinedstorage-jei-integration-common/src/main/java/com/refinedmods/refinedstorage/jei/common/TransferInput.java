package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import org.jspecify.annotations.Nullable;

record TransferInput(IRecipeSlotView view, TransferInputType type, @Nullable ItemResource autocraftableResource) {
}
