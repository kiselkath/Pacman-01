package org.example;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioManager {

    private Clip wakaClip;

    public AudioManager() {
        loadWaka();
    }

    /**
     * Helper method to load a sound clip from resources.
     * Makes it easy to add new sounds later without code duplication.
     */
    private Clip loadSoundClip(String resourcePath, String soundName) {
        try {
            URL soundURL = getClass().getClassLoader().getResource(resourcePath);
            
            if (soundURL == null) {
                System.err.println("Sound not found: " + soundName + " at " + resourcePath);
                return null;
            }

            AudioInputStream stream = AudioSystem.getAudioInputStream(soundURL);
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                return clip;
            } finally {
                // o prevent resource leak
                stream.close();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Failed to load sound: " + soundName + " - " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void loadWaka() {
        wakaClip = loadSoundClip("Sounds/Waka-waka.wav", "waka-waka");
    }

    public void playWaka() {
        // Check if clip exists and is open before playing
        if (wakaClip == null || !wakaClip.isOpen()) {
            return;
        }

        // Stop and restart if already playing
        if (wakaClip.isRunning()) {
            wakaClip.stop();
        }
        wakaClip.setFramePosition(0);
        wakaClip.start();
    }

    /**
     * Cleanup method to properly close audio resources.
     * Should be called when AudioManager is no longer needed.
     */
    public void cleanup() {
        if (wakaClip != null && wakaClip.isOpen()) {
            wakaClip.stop();
            wakaClip.close();
        }
    }
}
