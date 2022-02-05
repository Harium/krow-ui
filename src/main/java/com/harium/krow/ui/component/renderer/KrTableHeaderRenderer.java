package com.harium.krow.ui.component.renderer;

import com.harium.krow.ui.component.KrTableView;
import com.harium.krow.ui.component.KrWidget;

/**
 * Renderer for table headers.
 */
public interface KrTableHeaderRenderer {
    KrWidget getComponent(int column, KrTableView.KrTableColumnModel columnModel);
}
