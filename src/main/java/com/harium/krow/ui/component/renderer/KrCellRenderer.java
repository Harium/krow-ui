package com.harium.krow.ui.component.renderer;

import com.harium.krow.ui.component.KrWidget;
import com.harium.krow.ui.model.KrItemModel;
import com.harium.krow.ui.model.KrItemModel.KrModelIndex;

/**
 * The cell renderer generates components that are used to render various parts
 * of a collection view widget such as lists or tables.
 * <p>
 * Components can be cached, since only their render method is used. They're not
 * participating to the widget layout.
 */
public interface KrCellRenderer {
    /**
     * Returns a component to be used when rendering a cell inside
     * a view widget
     *
     * @param index the index of the cell
     * @param model the model holding the data
     * @return a widget used to render the cell that holds the data at the requested index
     */
    KrWidget getComponent(KrModelIndex index, KrItemModel model, boolean isSelected);

    /**
     * Overloaded method which avoids creating model index objects
     */
    KrWidget getComponent(int row, int col, KrModelIndex parent, KrItemModel model, boolean isSelected);
}
