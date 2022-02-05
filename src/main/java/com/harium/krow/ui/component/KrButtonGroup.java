package com.harium.krow.ui.component;

import com.harium.krow.ui.KrOrientation;
import com.harium.krow.ui.KrToolkit;
import com.harium.krow.ui.layout.KrFlowLayout;
import com.harium.krow.ui.style.KrButtonGroupStyle;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A button group contains a list of horizontal buttons, aligned and glued together. Only one button may be
 * toggled at any time.
 */
public class KrButtonGroup extends KrWidget {

    private final List<KrToggleButton> toggleButtons = new ArrayList<>();

    private KrToggleButton currentlyCheckedButton;

    @Getter private boolean allowUnCheck = true;

    private boolean isAdjusting = false;

    public KrButtonGroup(KrToggleButton... buttons) {

        setDefaultStyle(KrToolkit.getDefaultToolkit().getSkin().getStyle(KrButtonGroup.class));

        toggleButtons.addAll(Arrays.asList(buttons));

        setLayout(new KrFlowLayout(KrOrientation.HORIZONTAL, 0, 0));

        toggleButtons.forEach(this::add);

        toggleButtons.forEach(button -> button.addToggleListener(isChecked -> buttonToggled(button)));

        applyStyle();
    }

    private void buttonToggled(KrToggleButton button) {
        if (isAdjusting) {
            return;
        }

        if (button.isChecked()) {
            if (button != currentlyCheckedButton) {
                if (currentlyCheckedButton != null) {
                    isAdjusting = true;
                    currentlyCheckedButton.setChecked(false);
                    isAdjusting = false;
                }
                currentlyCheckedButton = button;
            }
        } else {
            // unchecked
            if (allowUnCheck) {
                currentlyCheckedButton = null;
            } else {
                currentlyCheckedButton.setChecked(true);
            }
        }
    }

    private void applyStyle() {
        KrButtonGroupStyle buttonGroupStyle = (KrButtonGroupStyle) getStyle();
        if (toggleButtons.size() == 1) {
            toggleButtons.get(0).setDefaultStyle(buttonGroupStyle.singleButtonStyle);
        } else {
            toggleButtons.get(0).setDefaultStyle(buttonGroupStyle.firstButtonStyle);
            toggleButtons.get(toggleButtons.size() - 1).setDefaultStyle(buttonGroupStyle.lastButtonStyle);

            for (int i = 1; i < toggleButtons.size() - 1; ++i) {
                toggleButtons.get(i).setDefaultStyle(buttonGroupStyle.middleButtonStyle);
            }
        }
    }

    public void setAllowUnCheck(boolean allowUnCheck) {
        this.allowUnCheck = allowUnCheck;

        if (!allowUnCheck && currentlyCheckedButton == null) {
            toggleButtons.get(0).setChecked(true);
        }
    }

    @Override
    public void validate() {
        super.validate();
        applyStyle();
    }
}
