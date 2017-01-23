package com.wingman.client.ui.style;

import com.wingman.client.util.FileUtil;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.synth.SynthComboBoxUI;
import java.io.IOException;

public class OnyxComboBoxUI extends SynthComboBoxUI {

    public static ComponentUI createUI(JComponent c) {
        return new OnyxComboBoxUI();
    }

    @Override
    protected JButton createArrowButton() {
        try {
            return new JButton(new ImageIcon(FileUtil.getFileAsBytes("/images/icons/downarrow.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JButton();
    }

    @Override
    protected ListCellRenderer createRenderer() {
        return new OnyxListCellRenderer(true, OnyxStyleFactory.BASE_BLUE_DARKER);
    }
}
