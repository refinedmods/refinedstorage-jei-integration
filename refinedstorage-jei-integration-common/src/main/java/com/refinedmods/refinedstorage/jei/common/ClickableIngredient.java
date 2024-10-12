package com.refinedmods.refinedstorage.jei.common;

import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IClickableIngredient;
import net.minecraft.client.renderer.Rect2i;

class ClickableIngredient<T> implements IClickableIngredient<T> {
    private final ITypedIngredient<T> ingredient;
    private final Rect2i area;

    ClickableIngredient(final ITypedIngredient<T> ingredient, final int x, final int y) {
        this.ingredient = ingredient;
        area = new Rect2i(x, y, 16, 16);
    }

    @Override
    @SuppressWarnings({"removal"})
    public ITypedIngredient<T> getTypedIngredient() {
        return ingredient;
    }

    @Override
    public IIngredientType<T> getIngredientType() {
        return ingredient.getType();
    }

    @Override
    public T getIngredient() {
        return ingredient.getIngredient();
    }

    @Override
    public Rect2i getArea() {
        return area;
    }
}
