package com.harium.krow.ui.component.renderer;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.harium.krow.ui.KrPadding;
import com.harium.krow.ui.KrSkin;
import com.harium.krow.ui.KrToolkit;
import com.harium.krow.ui.component.KrLabel;
import com.harium.krow.ui.component.KrWidget;
import com.harium.krow.ui.model.KrItemModel;
import com.harium.krow.ui.model.KrItemModel.KrModelIndex;

/**
 * A simple list view renderer implementation
 */
public class KrDefaultCellRenderer implements KrCellRenderer {

    private final KrLabel label;

    private final Drawable unselectedBackground;

    private final Drawable selectedBackground;

    public KrDefaultCellRenderer() {
        label = new KrLabel("");
        label.ensureUniqueStyle();
        label.setPadding(new KrPadding(4, 4, 4, 4));
        unselectedBackground = label.getStyle().background;
        selectedBackground = KrToolkit.getDefaultToolkit().getDrawable(
                KrToolkit.getDefaultToolkit().getSkin().getColor(KrSkin.ColorKey.SELECTION_BACKGROUND));
    }

    @Override
    public KrWidget getComponent(KrModelIndex index, KrItemModel model, boolean isSelected) {
        return getComponent(index.getRow(), index.getColumn(), index.getParentIndex(), model, isSelected);
    }

    @Override
    public KrWidget getComponent(int row, int col, KrModelIndex parent, KrItemModel model, boolean isSelected) {
        label.setText(model.getValue(row, col, parent).toString());
        if (isSelected) {
            label.setBackground(selectedBackground);
        } else {
            label.setBackground(unselectedBackground);
        }

        return label;
    }

}
