package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.autocrafting.PatternGridContainerMenu;
import com.refinedmods.refinedstorage.common.content.Menus;

import java.util.Optional;
import javax.annotation.Nullable;

import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.StonecutterRecipe;

class PatternGridStonecutterRecipeTransferHandler implements
    IRecipeTransferHandler<PatternGridContainerMenu, RecipeHolder<StonecutterRecipe>> {
    @Override
    public Class<? extends PatternGridContainerMenu> getContainerClass() {
        return PatternGridContainerMenu.class;
    }

    @Override
    public Optional<MenuType<PatternGridContainerMenu>> getMenuType() {
        return Optional.of(Menus.INSTANCE.getPatternGrid());
    }

    @Override
    public RecipeType<RecipeHolder<StonecutterRecipe>> getRecipeType() {
        return RecipeTypes.STONECUTTING;
    }

    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(final PatternGridContainerMenu containerMenu,
                                               final RecipeHolder<StonecutterRecipe> recipe,
                                               final IRecipeSlotsView recipeSlots,
                                               final Player player,
                                               final boolean maxTransfer,
                                               final boolean doTransfer) {
        if (doTransfer) {
            final var inputStacks = SlotUtil.getItems(recipeSlots, RecipeIngredientRole.INPUT);
            final var outputStacks = SlotUtil.getItems(recipeSlots, RecipeIngredientRole.OUTPUT);
            if (inputStacks.isEmpty()
                || outputStacks.isEmpty()
                || inputStacks.getFirst().isEmpty()
                || outputStacks.getFirst().isEmpty()) {
                return null;
            }
            containerMenu.transferStonecutterRecipe(
                inputStacks.getFirst().getFirst(),
                outputStacks.getFirst().getFirst()
            );
        }
        return null;
    }
}
