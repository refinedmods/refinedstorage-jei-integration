package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import javax.annotation.Nullable;

import mezz.jei.api.gui.ingredient.IRecipeSlotView;

record TransferInput(IRecipeSlotView view, TransferInputType type, @Nullable ItemResource autocraftableResource) {
}
