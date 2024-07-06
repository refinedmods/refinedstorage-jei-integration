package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.platform.api.PlatformApi;
import com.refinedmods.refinedstorage.platform.api.support.resource.PlatformResourceKey;
import com.refinedmods.refinedstorage.platform.common.support.AbstractBaseScreen;

import java.util.Optional;
import javax.annotation.Nullable;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IClickableIngredient;
import mezz.jei.api.runtime.IIngredientManager;

class ResourceGuiContainerHandler implements IGuiContainerHandler<AbstractBaseScreen<?>> {
    private final IIngredientManager ingredientManager;

    ResourceGuiContainerHandler(final IIngredientManager ingredientManager) {
        this.ingredientManager = ingredientManager;
    }

    @Override
    public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(
        final AbstractBaseScreen<?> baseScreen,
        final double mouseX,
        final double mouseY
    ) {
        return convertToIngredient(baseScreen.getHoveredResource()).flatMap(this::convertToClickableIngredient);
    }

    public Optional<Object> convertToIngredient(@Nullable final PlatformResourceKey resource) {
        if (resource == null) {
            return Optional.empty();
        }
        return PlatformApi.INSTANCE.getIngredientConverter().convertToIngredient(resource);
    }

    private Optional<IClickableIngredient<?>> convertToClickableIngredient(final Object ingredient) {
        final IIngredientHelper<Object> helper = ingredientManager.getIngredientHelper(ingredient);
        final Optional<ITypedIngredient<Object>> maybeTypedIngredient = ingredientManager.createTypedIngredient(
            helper.getIngredientType(),
            ingredient
        );
        return maybeTypedIngredient.map(typedIngredient -> new ClickableIngredient<>(typedIngredient, 16, 16));
    }
}
