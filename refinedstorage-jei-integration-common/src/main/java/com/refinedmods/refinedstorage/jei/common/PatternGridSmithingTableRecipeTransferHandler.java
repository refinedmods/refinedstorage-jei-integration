package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.autocrafting.PatternGridContainerMenu;
import com.refinedmods.refinedstorage.common.content.Menus;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import java.util.List;
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
import net.minecraft.world.item.crafting.SmithingRecipe;

class PatternGridSmithingTableRecipeTransferHandler implements
    IRecipeTransferHandler<PatternGridContainerMenu, RecipeHolder<SmithingRecipe>> {
    @Override
    public Class<? extends PatternGridContainerMenu> getContainerClass() {
        return PatternGridContainerMenu.class;
    }

    @Override
    public Optional<MenuType<PatternGridContainerMenu>> getMenuType() {
        return Optional.of(Menus.INSTANCE.getPatternGrid());
    }

    @Override
    public RecipeType<RecipeHolder<SmithingRecipe>> getRecipeType() {
        return RecipeTypes.SMITHING;
    }

    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(final PatternGridContainerMenu containerMenu,
                                               final RecipeHolder<SmithingRecipe> recipe,
                                               final IRecipeSlotsView recipeSlots,
                                               final Player player,
                                               final boolean maxTransfer,
                                               final boolean doTransfer) {
        if (doTransfer) {
            final List<List<ItemResource>> inputSlots = SlotUtil.getItems(recipeSlots, RecipeIngredientRole.INPUT);
            if (inputSlots.size() != 3) {
                return null;
            }
            containerMenu.transferSmithingTableRecipe(
                inputSlots.getFirst(),
                inputSlots.get(1),
                inputSlots.get(2)
            );
        }
        return null;
    }
}
