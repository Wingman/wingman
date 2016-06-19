package com.wingman.client.ui.components;

import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.Color;

/**
 * Progress bar to be displayed when downloading the gamepack.
 */
public class DownloadProgressBar extends JProgressBar {

    private static final String DOWNLOAD_PROGRESS_MESSAGE;
    private static final String DOWNLOAD_COMPLETE_MESSAGE;

    static{
        DOWNLOAD_PROGRESS_MESSAGE = "Downloading Oldschool Runescape %.1f%%";
        DOWNLOAD_COMPLETE_MESSAGE = "Launching Oldschool Runescape...";
    }

    public DownloadProgressBar(int min, int max){
        super(min, max);
        setStringPainted(true);

        // Make sure the text is readable when the bar is overlapping.
        setUI(new BasicProgressBarUI(){
            @Override
            protected Color getSelectionBackground() {
                // Color of the text when the bar *is not* overlapping.
                return DownloadProgressBar.this.getForeground();
            }

            @Override
            protected Color getSelectionForeground() {
                // Color of the text when the bar *is* overlapping.
                return DownloadProgressBar.this.getBackground();
            }
        });
    }

    @Override
    public void setValue(int n) {
        super.setValue(n);

        if(n < getMaximum()){
            setString(String.format(DOWNLOAD_PROGRESS_MESSAGE, getPercentComplete() * 100));
        } else{
            setString(DOWNLOAD_COMPLETE_MESSAGE);
            // TODO: Implement an indeterminate animation?
            //setIndeterminate(true);
        }
    }
}
