package com.wingman.client;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import com.google.common.base.Throwables;
import com.wingman.client.ui.Client;
import org.slf4j.LoggerFactory;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;

public class RelaunchedMain {

    /**
     * The second entry point of the client. <br>
     * This one is supposed to be called through the first entry point in {@link Main#main(String[])}.
     *
     * @param args application arguments, passed from {@link Main}
     */
    public static void main(String[] args) {
        setupConsoleLogging();
        createDirectories();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Client();
            }
        });
    }

    /**
     * Sets up SLF4J logging for {@link System#out} and {@link System#err} at INFO level. <br>
     * The logger also logs to {@link ClientSettings#LOGGING_FILE}, with the same format as for the console.
     */
    private static void setupConsoleLogging() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.INFO);

        ConsoleAppender<ILoggingEvent> consoleAppender =
                (ConsoleAppender<ILoggingEvent>) root.getAppender("console");

        LayoutWrappingEncoder<ILoggingEvent> consoleWrappingEncoder =
                (LayoutWrappingEncoder<ILoggingEvent>) consoleAppender.getEncoder();

        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<>();
        encoder.setLayout(consoleWrappingEncoder.getLayout());
        encoder.setCharset(Charset.forName("UTF-16"));
        encoder.setContext(root.getLoggerContext());
        encoder.start();

        FileAppender<ILoggingEvent> logFileAppender = new FileAppender<>();
        logFileAppender.setFile(ClientSettings.LOGGING_FILE);
        logFileAppender.setContext(root.getLoggerContext());
        logFileAppender.setEncoder(encoder);
        logFileAppender.setAppend(false);
        logFileAppender.start();

        root.addAppender(logFileAppender);

        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
    }

    /**
     * Creates file directories required for the operation of the client.
     */
    private static void createDirectories(){
        if (!ClientSettings.PLUGINS_DIR.toFile().exists()) {
            if (!ClientSettings.PLUGINS_DIR.toFile().mkdirs()) {
                throw Throwables.propagate(new IOException("Couldn't create directory " + ClientSettings.PLUGINS_DIR));
            }
        }

        if (!ClientSettings.SETTINGS_DIR.toFile().exists()) {
            if (!ClientSettings.SETTINGS_DIR.toFile().mkdirs()) {
                throw Throwables.propagate(new IOException("Couldn't create directory " + ClientSettings.SETTINGS_DIR));
            }
        }
    }
}
