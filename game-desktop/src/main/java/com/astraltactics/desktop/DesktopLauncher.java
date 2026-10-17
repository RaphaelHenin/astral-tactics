package com.astraltactics.desktop;

import com.astraltactics.client.AstralTacticsGame;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.slf4j.LoggerFactory;

public final class DesktopLauncher {
    private DesktopLauncher() { }

    public static void main(String[] args) {
        LoggerFactory.getLogger(DesktopLauncher.class).info("Starting Astral Tactics on Java {}",
                System.getProperty("java.version"));
        var config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Astral Tactics");
        config.setWindowedMode(1200, 800);
        config.useVsync(true);
        config.setForegroundFPS(60);
        // This iteration has no audio: do not require an audio device.
        config.disableAudio(true);
        new Lwjgl3Application(new AstralTacticsGame(), config);
    }
}
