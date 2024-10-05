package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.api.resource.ResourceAmount;
import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.ItemStack;

final class SlotUtil {
    private SlotUtil() {
    }

    static List<List<ItemResource>> getItems(final IRecipeSlotsView recipeSlots,
                                             final RecipeIngredientRole role) {
        return recipeSlots.getSlotViews(role).stream().map(SlotUtil::getItems).toList();
    }

    private static List<ItemResource> getItems(final IRecipeSlotView slotView) {
        final List<ItemStack> stacks = slotView.getItemStacks().collect(Collectors.toList());
        prioritizeDisplayedStack(slotView, stacks);
        return stacks.stream().map(ItemResource::ofItemStack).collect(Collectors.toList());
    }

    private static void prioritizeDisplayedStack(final IRecipeSlotView slotView, final List<ItemStack> stacks) {
        slotView.getDisplayedItemStack().ifPresent(displayed -> {
            final int index = stacks.indexOf(displayed);
            if (index < 0) {
                return;
            }
            stacks.remove(index);
            stacks.addFirst(displayed);
        });
    }

    static List<List<ResourceAmount>> getResources(final IRecipeSlotsView recipeSlots,
                                                   final RecipeIngredientRole role) {
        return recipeSlots.getSlotViews(role).stream().map(SlotUtil::getResources).toList();
    }

    public static List<ResourceAmount> getResources(final IRecipeSlotView slotView) {
        final List<ResourceAmount> resources = slotView.getAllIngredients()
            .flatMap(ingredient -> RefinedStorageApi.INSTANCE.getIngredientConverter().convertToResourceAmount(
                ingredient.getIngredient()).stream())
            .collect(Collectors.toList());
        prioritizeDisplayedIngredient(slotView, resources);
        return resources;
    }

    private static void prioritizeDisplayedIngredient(final IRecipeSlotView slotView,
                                                      final List<ResourceAmount> resources) {
        slotView.getDisplayedIngredient()
            .flatMap(displayed -> RefinedStorageApi.INSTANCE.getIngredientConverter()
                .convertToResourceAmount(displayed))
            .ifPresent(resourceAmount -> {
                final int index = resources.indexOf(resourceAmount);
                if (index < 0) {
                    return;
                }
                resources.remove(index);
                resources.addFirst(resourceAmount);
            });
    }
}
