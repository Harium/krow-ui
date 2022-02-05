package com.harium.krow.ui.component;

import com.harium.krow.ui.KrToolkit;
import com.harium.krow.ui.layout.KrAbsoluteLayout;
import com.harium.krow.ui.layout.KrLayout;

/**
 * A simple panel that can host other components.
 */
public class KrPanel extends KrWidget {

    public KrPanel() {
        this(new KrAbsoluteLayout());
    }

    public KrPanel(KrLayout layout) {
        setDefaultStyle(KrToolkit.getDefaultToolkit().getSkin().getStyle(KrPanel.class));
        setLayout(layout);
    }
}
