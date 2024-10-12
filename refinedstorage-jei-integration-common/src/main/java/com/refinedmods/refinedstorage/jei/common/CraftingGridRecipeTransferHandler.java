package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.api.grid.view.GridView;
import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.api.resource.list.MutableResourceList;
import com.refinedmods.refinedstorage.api.resource.list.MutableResourceListImpl;
import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
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
import mezz.jei.api.runtime.IRecipesGui;
import mezz.jei.common.transfer.RecipeTransferErrorInternal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import static java.util.Comparator.comparingLong;

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
        final GridView view = containerMenu.getView();
        final MutableResourceList available = containerMenu.getAvailableListForRecipeTransfer();
        final List<TransferInput> transferInputs = getTransferInputs(view, recipeSlots, available);
        final TransferType type = getTransferType(transferInputs);
        if (doTransfer) {
            if (type.canOpenAutocraftingPreview() && Screen.hasControlDown()) {
                openAutocraftingPreview(transferInputs);
                return RecipeTransferErrorInternal.INSTANCE;
            } else {
                doTransfer(recipeSlots, containerMenu);
            }
            return null;
        }
        return type == TransferType.AVAILABLE ? null : new CraftingGridRecipeTransferError(transferInputs, type);
    }

    private void openAutocraftingPreview(final List<TransferInput> transferInputs) {
        final Screen parentScreen = Minecraft.getInstance().screen instanceof IRecipesGui recipesGui
            ? recipesGui.getParentScreen().orElse(null)
            : null;
        final List<ResourceAmount> craftingRequests = createCraftingRequests(transferInputs);
        RefinedStorageApi.INSTANCE.openAutocraftingPreview(craftingRequests, parentScreen);
    }

    private TransferType getTransferType(final List<TransferInput> transferInputs) {
        if (transferInputs.stream().allMatch(input -> input.type() == TransferInputType.AVAILABLE)) {
            return TransferType.AVAILABLE;
        }
        final boolean hasMissing = transferInputs.stream().anyMatch(input -> input.type() == TransferInputType.MISSING);
        final boolean hasAutocraftable = transferInputs.stream()
            .anyMatch(input -> input.type() == TransferInputType.AUTOCRAFTABLE);
        if (hasMissing && hasAutocraftable) {
            return TransferType.MISSING_BUT_SOME_AUTOCRAFTABLE;
        } else if (hasAutocraftable) {
            return TransferType.MISSING_BUT_ALL_AUTOCRAFTABLE;
        }
        return TransferType.MISSING;
    }

    private void doTransfer(final IRecipeSlotsView recipeSlots, final CraftingGridContainerMenu containerMenu) {
        final List<List<ItemResource>> inputs = SlotUtil.getItems(recipeSlots, RecipeIngredientRole.INPUT);
        containerMenu.transferRecipe(inputs);
    }

    private List<TransferInput> getTransferInputs(final GridView view,
                                                  final IRecipeSlotsView recipeSlots,
                                                  final MutableResourceList available) {
        return recipeSlots.getSlotViews(RecipeIngredientRole.INPUT)
            .stream()
            .filter(slotView -> !slotView.isEmpty())
            .map(slotView -> toTransferInput(view, available, slotView))
            .toList();
    }

    private TransferInput toTransferInput(final GridView view,
                                          final MutableResourceList available,
                                          final IRecipeSlotView slotView) {
        final List<ItemStack> possibilities = slotView.getItemStacks().toList();
        for (final ItemStack possibility : possibilities) {
            final ItemResource possibilityResource = ItemResource.ofItemStack(possibility);
            if (available.remove(possibilityResource, 1).isPresent()) {
                return new TransferInput(slotView, TransferInputType.AVAILABLE, null);
            }
        }
        final List<ItemResource> autocraftingPossibilities = possibilities
            .stream()
            .map(ItemResource::ofItemStack)
            .filter(view::isAutocraftable)
            .sorted(comparingLong(view::getAmount))
            .toList();
        if (!autocraftingPossibilities.isEmpty()) {
            return new TransferInput(slotView, TransferInputType.AUTOCRAFTABLE, autocraftingPossibilities.getFirst());
        }
        return new TransferInput(slotView, TransferInputType.MISSING, null);
    }

    private static List<ResourceAmount> createCraftingRequests(final List<TransferInput> transferInputs) {
        final MutableResourceList requests = MutableResourceListImpl.orderPreserving();
        for (final TransferInput transferInput : transferInputs) {
            if (transferInput.type() == TransferInputType.AUTOCRAFTABLE
                && transferInput.autocraftableResource() != null) {
                requests.add(transferInput.autocraftableResource(), 1);
            }
        }
        return requests.copyState().stream().toList();
    }
}
