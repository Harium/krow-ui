package com.katzstudio.kreativity.ui.model;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspired by Qt's QAbstractItemModel, the {@link KrAbstractItemModel} class can be used to
 * represent both table and hierarchical values.
 *
 * @see <a href="http://doc.qt.io/qt-4.8/qabstractitemmodel.html#details">QAbstractItemModel Class</a>
 */
@SuppressWarnings("WeakerAccess")
public class KrAbstractItemModel<T> {

    protected final List<Listener> listeners = new ArrayList<>();

    public T getValue(KrModelIndex index) {
        return null;
    }

    public void setValue(KrModelIndex index, T value) {

    }

    public int getColumnCount() {
        return getColumnCount(KrModelIndex.ROOT);
    }

    public int getColumnCount(KrModelIndex index) {
        return 0;
    }

    public int getRowCount() {
        return getColumnCount(KrModelIndex.ROOT);
    }

    public int getRowCount(KrModelIndex index) {
        return 0;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    protected void notifyDataChanged() {
        listeners.forEach(Listener::dataChanged);
    }

    // TODO(alex): add support for adding / removing rows

    @Data
    public static class KrModelIndex {
        public static final KrModelIndex ROOT = new KrModelIndex(0, 0, null);

        @Getter private final int row;

        @Getter private final int column;

        @Getter private final KrModelIndex parentIndex;

        public KrModelIndex(int row) {
            this(row, 0, ROOT);
        }

        public KrModelIndex(int row, int column) {
            this(row, column, ROOT);
        }

        public KrModelIndex(int row, int column, KrModelIndex parent) {
            this.row = row;
            this.column = column;
            this.parentIndex = parent;
        }


        @Override
        public String toString() {
            return "KrModelIndex(row: " + row + ", col: " + column + ")";
        }
    }

    public interface Listener {
        void dataChanged();
    }
}
