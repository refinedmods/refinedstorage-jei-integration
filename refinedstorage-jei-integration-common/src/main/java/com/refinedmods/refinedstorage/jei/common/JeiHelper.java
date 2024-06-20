package com.refinedmods.refinedstorage.jei.common;

import mezz.jei.api.runtime.IJeiRuntime;

public final class JeiHelper {
    private JeiHelper() {
    }

    public static String getSearchFieldText() {
        final IJeiRuntime runtime = RefinedStorageModPlugin.getRuntime();
        if (runtime == null) {
            return "";
        }
        return runtime.getIngredientFilter().getFilterText();
    }

    public static void setSearchFieldText(final String text) {
        final IJeiRuntime runtime = RefinedStorageModPlugin.getRuntime();
        if (runtime != null) {
            runtime.getIngredientFilter().setFilterText(text);
        }
    }
}
