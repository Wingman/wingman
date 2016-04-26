package com.wingman.client.api.ui;

import com.wingman.client.ui.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideBar  {

    public JPanel panel;
    public JButton paneButton;
    public ActionListener paneButtonActionListener;

    public SideBar() {
        panel = new JPanel(new BorderLayout() {
            @Override
            public Dimension minimumLayoutSize(Container target) {
                return new Dimension(200, 0);
            }

            @Override
            public Dimension preferredLayoutSize(Container target) {
                return new Dimension(200, 0);
            }
        });
    }

    public void register() {
        if (paneButton != null) {
            if (paneButtonActionListener == null) {
                paneButtonActionListener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (panel != null) {
                            Client.sideBarBox.sideBarPanel.removeAll();
                            Client.sideBarBox.sideBarPanel.add(panel);
                            Client.framePanel.revalidate();
                            Client.framePanel.repaint();
                        }
                    }
                };
            }
            paneButton.addActionListener(paneButtonActionListener);

            if (panel != null) {
                if (Client.sideBarBox.sideBarPanel.getComponents().length == 0) {
                    Client.sideBarBox.sideBarPanel.add(panel);
                }
            }

            paneButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

            Client.sideBarBox.buttonPanel.add(paneButton);
        }
    }

    public void unregister() {
        if (paneButton != null) {
            Client.sideBarBox.buttonPanel.remove(paneButton);
            if (panel != null) {
                Client.sideBarBox.sideBarPanel.remove(panel);
            }
        }
    }
}
