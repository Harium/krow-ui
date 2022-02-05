package com.harium.krow.ui.component.renderer;

import com.harium.krow.ui.component.KrLabel;
import com.harium.krow.ui.component.KrTableView;
import com.harium.krow.ui.component.KrWidget;

/**
 * Default implementation for {@link KrTableHeaderRenderer}
 */
public class KrDefaultTableHeaderRenderer implements KrTableHeaderRenderer {
    private final KrLabel label;

    public KrDefaultTableHeaderRenderer() {
        label = new KrLabel("");
        label.ensureUniqueStyle();
    }

    @Override
    public KrWidget getComponent(int column, KrTableView.KrTableColumnModel columnModel) {
        label.setText(columnModel.getColumnName(column));
        return label;
    }
}
