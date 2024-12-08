package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.api.grid.view.GridView;
import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.autocrafting.patterngrid.PatternGridContainerMenu;

import java.util.List;
import javax.annotation.Nullable;

import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;

abstract class AbstractPatternGridRecipeTransferHandler<R>
    implements IRecipeTransferHandler<PatternGridContainerMenu, R> {

    @Nullable
    public static IRecipeTransferError autocraftableHint(final GridView view, final IRecipeSlotsView recipeSlots) {
        final List<IRecipeSlotView> inputs = recipeSlots.getSlotViews(RecipeIngredientRole.INPUT);
        final boolean allAreAutocraftable = inputs.stream()
            .filter(slotView -> !slotView.isEmpty())
            .allMatch(slotView -> SlotUtil.getResources(slotView)
                .stream()
                .map(ResourceAmount::resource)
                .anyMatch(view::isAutocraftable));
        final List<IRecipeSlotView> autocraftableSlots = inputs.stream()
            .filter(slotView -> SlotUtil.getResources(slotView)
                .stream()
                .map(ResourceAmount::resource)
                .anyMatch(view::isAutocraftable))
            .toList();
        return autocraftableSlots.isEmpty()
            ? null
            : new PatternGridRecipeTransferError(autocraftableSlots, allAreAutocraftable);
    }
}
