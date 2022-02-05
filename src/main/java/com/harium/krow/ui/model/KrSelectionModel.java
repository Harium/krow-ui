package com.harium.krow.ui.model;

import com.harium.krow.ui.component.KrListView;
import com.harium.krow.ui.model.KrItemModel.KrModelIndex;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A selection model for a {@link KrListView} widget.
 * <p>
 * The selection model determines what selections are possible (multiple, single, etc)
 * and what items are currently selected.
 */
public class KrSelectionModel {

    private final List<KrListSelectionListener> listeners = new ArrayList<>();

    @Getter private KrSelection currentSelection = KrSelection.EMPTY;

    public void setSelection(KrSelection newSelection) {
        if (!Objects.equals(currentSelection, newSelection)) {
            KrSelection oldSelection = currentSelection;
            currentSelection = newSelection;
            notifySelectionChanged(oldSelection, newSelection);
        }
    }

    public void add(KrModelIndex index) {
        setSelection(currentSelection.expand(index));
    }

    public void remove(KrModelIndex index) {
        setSelection(currentSelection.shrink(index));
    }

    public void clearSelection() {
        setSelection(KrSelection.EMPTY);
    }

    public void addSelectionListener(KrListSelectionListener listener) {
        listeners.add(listener);
    }

    public void removeSelectionListener(KrListSelectionListener listener) {
        listeners.remove(listener);
    }

    protected void notifySelectionChanged(KrSelection oldSelection, KrSelection newSelection) {
        listeners.forEach(l -> l.selectionChanged(oldSelection, newSelection));
    }

    public interface KrListSelectionListener {
        void selectionChanged(KrSelection oldSelection, KrSelection newSelection);
    }
}
