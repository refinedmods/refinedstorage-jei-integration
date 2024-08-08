package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.grid.view.PlatformGridResource;
import com.refinedmods.refinedstorage.common.api.support.resource.PlatformResourceKey;
import com.refinedmods.refinedstorage.common.grid.screen.AbstractGridScreen;

import java.util.Optional;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IClickableIngredient;
import mezz.jei.api.runtime.IIngredientManager;

class GridGuiContainerHandler implements IGuiContainerHandler<AbstractGridScreen<?>> {
    private final IIngredientManager ingredientManager;

    GridGuiContainerHandler(final IIngredientManager ingredientManager) {
        this.ingredientManager = ingredientManager;
    }

    @Override
    public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(
        final AbstractGridScreen screen,
        final double mouseX,
        final double mouseY
    ) {
        final PlatformGridResource resource = screen.getCurrentGridResource();
        if (resource == null) {
            return Optional.empty();
        }
        final PlatformResourceKey underlyingResource = resource.getResourceForRecipeMods();
        if (underlyingResource == null) {
            return Optional.empty();
        }
        return RefinedStorageApi.INSTANCE.getIngredientConverter().convertToIngredient(underlyingResource).flatMap(
            ingredient -> convertToClickableIngredient(mouseX, mouseY, ingredient)
        );
    }

    private Optional<IClickableIngredient<?>> convertToClickableIngredient(final double x,
                                                                           final double y,
                                                                           final Object ingredient) {
        final IIngredientHelper<Object> helper = ingredientManager.getIngredientHelper(ingredient);
        final Optional<ITypedIngredient<Object>> maybeTypedIngredient =
            ingredientManager.createTypedIngredient(helper.getIngredientType(), ingredient);
        return maybeTypedIngredient
            .map(typedIngredient -> new ClickableIngredient<>(typedIngredient, (int) x, (int) y));
    }
}
