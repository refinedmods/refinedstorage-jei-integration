package com.refinedmods.refinedstorage.jei.common;

import mezz.jei.api.runtime.IJeiRuntime;

public class JeiProxy {
    public String getSearchFieldText() {
        final IJeiRuntime runtime = RefinedStorageModPlugin.getRuntime();
        if (runtime == null) {
            return "";
        }
        return runtime.getIngredientFilter().getFilterText();
    }

    public void setSearchFieldText(final String text) {
        final IJeiRuntime runtime = RefinedStorageModPlugin.getRuntime();
        if (runtime != null) {
            runtime.getIngredientFilter().setFilterText(text);
        }
    }
}
