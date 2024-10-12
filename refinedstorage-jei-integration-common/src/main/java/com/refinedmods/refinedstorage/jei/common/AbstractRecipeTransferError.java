package com.refinedmods.refinedstorage.jei.common;

import com.refinedmods.refinedstorage.common.grid.AutocraftableResourceHint;

import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;

abstract class AbstractRecipeTransferError implements IRecipeTransferError {
    protected static final ClientTooltipComponent MOVE_ITEMS = ClientTooltipComponent.create(
        Component.translatable("jei.tooltip.transfer").getVisualOrderText()
    );
    protected static final int AUTOCRAFTABLE_COLOR = AutocraftableResourceHint.AUTOCRAFTABLE.getColor();

    protected static ClientTooltipComponent createAutocraftableHint(final Component component) {
        return ClientTooltipComponent.create(component.copy().withColor(AUTOCRAFTABLE_COLOR).getVisualOrderText());
    }
}
