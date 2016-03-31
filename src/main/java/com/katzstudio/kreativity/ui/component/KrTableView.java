package com.katzstudio.kreativity.ui.component;

import com.badlogic.gdx.math.Vector2;
import com.katzstudio.kreativity.ui.KrToolkit;
import com.katzstudio.kreativity.ui.component.renderer.KrCellRenderer;
import com.katzstudio.kreativity.ui.component.renderer.KrDefaultCellRenderer;
import com.katzstudio.kreativity.ui.component.renderer.KrDefaultTableHeaderRenderer;
import com.katzstudio.kreativity.ui.component.renderer.KrTableHeaderRenderer;
import com.katzstudio.kreativity.ui.model.KrAbstractItemModel;
import com.katzstudio.kreativity.ui.model.KrAbstractItemModel.KrModelIndex;
import com.katzstudio.kreativity.ui.render.KrRenderer;
import com.katzstudio.kreativity.ui.style.KrItemViewStyle;
import lombok.Getter;
import lombok.Setter;

/**
 * A {@link KrTableView} widget displays data stored in a {@link KrAbstractItemModel}
 * using a table.
 */
public class KrTableView extends KrAbstractItemView<KrItemViewStyle> {

    public static final int ROW_HEIGHT = 20;

    @Getter @Setter private KrAbstractItemModel model;

    @Getter @Setter private KrTableColumnModel columnModel;

    @Getter @Setter private KrCellRenderer cellRenderer = new KrDefaultCellRenderer();

    @Getter @Setter private KrTableHeaderRenderer headerRenderer = new KrDefaultTableHeaderRenderer();

    public KrTableView(KrAbstractItemModel model) {
        this(model, null);
    }

    public KrTableView(KrAbstractItemModel model, KrTableColumnModel columnModel) {
        super(model);
        this.model = model;
        this.columnModel = columnModel;
        setStyle(KrToolkit.getDefaultToolkit().getSkin().getTableViewStyle());
    }

    @Override
    public void ensureUniqueStyle() {
        if (style == KrToolkit.getDefaultToolkit().getSkin().getTableViewStyle()) {
            style = new KrItemViewStyle(style);
        }
    }

    @Override
    protected void drawSelf(KrRenderer renderer) {

        boolean clipped = false;
        boolean drawHeader = columnModel != null;
        int columnCount = columnModel != null ? columnModel.getColumnCount() : model.getColumnCount();

        int x = 0;
        int y = 0;
        int columnWidth = (int) (getWidth() / columnCount);
        int rowHeight = ROW_HEIGHT;

        int gridSize = style.gridVisible ? 1 : 0;

        // draw columns
        if (drawHeader) {
            for (int i = 0; i < columnCount; ++i) {
                KrWidget cellWidget = headerRenderer.getComponent(i, columnModel);
                cellWidget.setGeometry(x, y, columnWidth - gridSize, rowHeight - gridSize);
                cellWidget.draw(renderer);
                x += columnWidth;
            }

            clipped = renderer.beginClip(0, ROW_HEIGHT, getWidth(), getHeight() - ROW_HEIGHT);
        }

        // draw elements
        x = 0;
        for (int i = 0; i < columnCount; ++i) {
            y = (int) -verticalScrollBar.getCurrentValue() + (drawHeader ? rowHeight : 0);
            for (int j = 0; j < model.getRowCount(); ++j) {
                // TODO: calculate selection flag
                KrWidget cellWidget = cellRenderer.getComponent(new KrModelIndex(j, i, null), model, selectionModel.getCurrentSelection().contains(new KrModelIndex(j)));
                cellWidget.setGeometry(x, y, columnWidth - gridSize, rowHeight - gridSize);
                cellWidget.draw(renderer);
                y += rowHeight;
            }
            x += columnWidth;
        }

        // draw grid

        if (clipped) {
            renderer.endClip();
        }
    }

    public KrModelIndex findItemIndexAt(int x, int y) {
        if (columnModel != null) {
            y -= ROW_HEIGHT;
        }
        int index = (int) ((y + verticalScrollBar.getCurrentValue()) / ROW_HEIGHT);
        return new KrModelIndex(index);
    }

    @Override
    public Vector2 calculatePreferredSize() {
        return new Vector2(100, model.getRowCount() * ROW_HEIGHT + (columnModel != null ? ROW_HEIGHT : 0));
    }

    // TODO: implement selection type: cell / row

    public interface KrTableColumnModel {

        int getColumnCount();

        String getColumnName(int index);

        int getModelIndex(int index);
    }
}
