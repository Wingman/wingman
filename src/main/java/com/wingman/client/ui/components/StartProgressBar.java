package com.wingman.client.ui.components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

/**
 * Progress bar to be displayed when starting the client.
 */
public class StartProgressBar extends JProgressBar {

    private static final String DOWNLOAD_PROGRESS_MESSAGE;
    private static final String DOWNLOAD_COMPLETE_MESSAGE;
    private static final String UPDATE_CHECK_MESSAGE;
    private static final String NO_UPDATES_MESSAGE;

    private Mode mode;
    private UI ui;

    static{
        DOWNLOAD_PROGRESS_MESSAGE = "Downloading Oldschool Runescape %.1f%%";
        DOWNLOAD_COMPLETE_MESSAGE = "Download complete. Launching Oldschool Runescape...";
        UPDATE_CHECK_MESSAGE = "Checking for updates...";
        NO_UPDATES_MESSAGE = "No updates detected. Launching Oldschool Runescape...";
    }

    /**
     * Modes the progress bar can be in.
     */
    public enum Mode{
        CHECKING_FOR_UPDATES,
        DOWNLOADING,
        DOWNLOADING_FINISHED,
        NO_UPDATES
    }

    /**
     * Custom BasicProgressBarUI. Implements a custom indeterminate animation and sets the
     * color of text when the bar is/isn't overlapping.
     */
    private class UI extends BasicProgressBarUI{
        int animationCount = 0;
        boolean finalFrame = false;

        static final int INDETERMINATE_START_FULL_COUNT = 1;
        static final int INDETERMINATE_START_EMPTY_COUNT = 0;

        @Override
        protected Color getSelectionBackground() {
            // Color of the text when the bar *is not* overlapping.
            return StartProgressBar.this.getForeground();
        }

        @Override
        protected Color getSelectionForeground() {
            // Color of the text when the bar *is* overlapping.
            return StartProgressBar.this.getBackground();
        }

        @Override
        protected Rectangle getBox(Rectangle r) {
            // This is where we customize the box painting at each frame.

            Rectangle box = super.getBox(r);

            // Ratio of animation completion
            double ratio = (double) getAnimationIndex()/getFrameCount();

            if((animationCount & 1) == 0){
                box.setLocation(0,0);
                box.setSize((int) (progressBar.getWidth() * ratio), (int) box.getHeight());
            } else{
                box.setLocation((int) (progressBar.getWidth() * ratio), 0);
                box.setSize((int) (progressBar.getWidth() * (1 - ratio)), (int) box.getHeight());
            }

            if(getAnimationIndex() + 1 == getFrameCount()){
                finalFrame = true;
            }
            else if(finalFrame && getAnimationIndex() + 1 < getFrameCount()){
                ++animationCount;
                finalFrame = false;
            }

            return box;
        }
    }

    public StartProgressBar(int min, int max){
        super(min, max);
        setStringPainted(true);

        // Use our custom BasicProgressBarUI to make sure text is readable
        // when the bar is overlapping. Also, use a custom indeterminate animation.
        ui = new UI();
        setUI(ui);
    }

    public StartProgressBar(){
        this(0,0);
    }

    @Override
    public void setValue(int n) {
        super.setValue(n);

        if(n < getMaximum()){
            setString(String.format(DOWNLOAD_PROGRESS_MESSAGE, getPercentComplete() * 100));
        } else{
            setMode(Mode.DOWNLOADING_FINISHED);
        }
    }

    /**
     * Sets the progress bar's mode.
     *
     * @param mode The mode.
     */
    public void setMode(Mode mode){
        switch(mode){
            case CHECKING_FOR_UPDATES:
                super.setValue(0); // change progress without changing string.
                setString(UPDATE_CHECK_MESSAGE);
                break;
            case DOWNLOADING_FINISHED:
                super.setValue(getMaximum()); // change progress without changing string.
                setString(DOWNLOAD_COMPLETE_MESSAGE);
                ui.animationCount = UI.INDETERMINATE_START_FULL_COUNT;
                setIndeterminate(true);
                break;
            case NO_UPDATES:
                super.setValue(0); // change progress without changing string.
                setString(NO_UPDATES_MESSAGE);
                setIndeterminate(true);
                break;
            case DOWNLOADING:
                setValue(0);
                break;
        }

        this.mode = mode;
    }

    public Mode getMode(){
        return mode;
    }
}
