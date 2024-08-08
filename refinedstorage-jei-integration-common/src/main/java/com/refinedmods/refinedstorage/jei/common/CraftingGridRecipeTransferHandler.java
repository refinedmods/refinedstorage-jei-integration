package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.api.resource.list.ResourceList;
import com.refinedmods.refinedstorage.common.content.Menus;
import com.refinedmods.refinedstorage.common.grid.CraftingGridContainerMenu;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

class CraftingGridRecipeTransferHandler implements
    IRecipeTransferHandler<CraftingGridContainerMenu, RecipeHolder<CraftingRecipe>> {
    @Override
    public Class<? extends CraftingGridContainerMenu> getContainerClass() {
        return CraftingGridContainerMenu.class;
    }

    @Override
    public Optional<MenuType<CraftingGridContainerMenu>> getMenuType() {
        return Optional.of(Menus.INSTANCE.getCraftingGrid());
    }

    @Override
    public RecipeType<RecipeHolder<CraftingRecipe>> getRecipeType() {
        return RecipeTypes.CRAFTING;
    }

    @Override
    @Nullable
    public IRecipeTransferError transferRecipe(final CraftingGridContainerMenu containerMenu,
                                               final RecipeHolder<CraftingRecipe> recipe,
                                               final IRecipeSlotsView recipeSlots,
                                               final Player player,
                                               final boolean maxTransfer,
                                               final boolean doTransfer) {
        if (doTransfer) {
            doTransfer(recipeSlots, containerMenu);
            return null;
        }
        final ResourceList available = containerMenu.getAvailableListForRecipeTransfer();
        final List<IRecipeSlotView> missingSlots = findMissingSlots(recipeSlots, available);
        return missingSlots.isEmpty() ? null : new MissingItemRecipeTransferError(missingSlots);
    }

    private void doTransfer(final IRecipeSlotsView recipeSlots, final CraftingGridContainerMenu containerMenu) {
        final List<List<ItemResource>> inputs = SlotUtil.getItems(recipeSlots, RecipeIngredientRole.INPUT);
        containerMenu.transferRecipe(inputs);
    }

    private List<IRecipeSlotView> findMissingSlots(final IRecipeSlotsView recipeSlots, final ResourceList available) {
        return recipeSlots.getSlotViews(RecipeIngredientRole.INPUT).stream().filter(slotView -> {
            if (slotView.isEmpty()) {
                return false;
            }
            return !isAvailable(available, slotView);
        }).toList();
    }

    private boolean isAvailable(final ResourceList available, final IRecipeSlotView slotView) {
        final List<ItemStack> possibilities = slotView.getItemStacks().toList();
        for (final ItemStack possibility : possibilities) {
            final ItemResource possibilityResource = ItemResource.ofItemStack(possibility);
            if (available.remove(possibilityResource, 1).isPresent()) {
                return true;
            }
        }
        return false;
    }
}
