package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.autocrafting.PatternGridContainerMenu;
import com.refinedmods.refinedstorage.common.content.Menus;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeHolder;

class PatternGridProcessingRecipeTransferHandler implements
    IRecipeTransferHandler<PatternGridContainerMenu, RecipeHolder<?>> {
    @Override
    public Class<? extends PatternGridContainerMenu> getContainerClass() {
        return PatternGridContainerMenu.class;
    }

    @Override
    public Optional<MenuType<PatternGridContainerMenu>> getMenuType() {
        return Optional.of(Menus.INSTANCE.getPatternGrid());
    }

    @Override
    @SuppressWarnings({"DataFlowIssue"})
    public RecipeType<RecipeHolder<?>> getRecipeType() {
        return null; // universal transfer handler makes this null safe
    }

    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(final PatternGridContainerMenu containerMenu,
                                               final RecipeHolder<?> recipe,
                                               final IRecipeSlotsView recipeSlots,
                                               final Player player,
                                               final boolean maxTransfer,
                                               final boolean doTransfer) {
        if (doTransfer) {
            final List<List<ResourceAmount>> inputs = SlotUtil.getResources(recipeSlots, RecipeIngredientRole.INPUT);
            final List<List<ResourceAmount>> outputs = SlotUtil.getResources(recipeSlots, RecipeIngredientRole.OUTPUT);
            containerMenu.transferProcessingRecipe(inputs, outputs);
        }
        return null;
    }
}
