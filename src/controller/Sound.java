package controller;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private static Clip clip;
    private static boolean isSoundPlaying = false;
    private static float volume = 1.0f; 
    private static float rowSoundVolume = 1.0f;
    public static int getVolume() {return (int) (volume * 100);}
    public static float getRowSoundVolume() {return rowSoundVolume;}
    public static void setRowSoundVolume(float volume) {rowSoundVolume = volume;}
    public static void setVolume(int volumePercentage) {
        volume = volumePercentage / 100.0f;
        if (clip != null && clip.isOpen()) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
    }
    public static void playSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            if (isSoundPlaying) stopSound();
            else {
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                isSoundPlaying = true;
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {e.printStackTrace();}
    }
    public static void stopSound() {
        if (clip != null && clip.isRunning()) clip.stop();
        isSoundPlaying = false;
    }

    public static void toggleSound() {
        if (isSoundPlaying) stopSound();
        else playSound("design/music-full.wav");
    }
    public static void playBlockSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            byte[] audioData = new byte[audioIn.available()];
            audioIn.read(audioData);
            for (int i = 0; i < audioData.length; i++) audioData[i] *= rowSoundVolume;
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
            AudioInputStream adjustedAudioIn = new AudioInputStream(bais, audioIn.getFormat(), audioData.length);
            Clip clip = AudioSystem.getClip();
            clip.open(adjustedAudioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

} 