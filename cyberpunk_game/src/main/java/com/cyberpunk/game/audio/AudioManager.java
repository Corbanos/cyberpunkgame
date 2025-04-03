package com.cyberpunk.game.audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Manages audio in the game.
 */
public class AudioManager {
    
    // OpenAL device and context
    private long device;
    private long context;
    
    // Sound buffers
    private Map<String, Integer> soundBuffers;
    
    // Sound sources
    private List<Integer> sources;
    
    // Music source
    private int musicSource;
    private String currentMusic;
    
    // Volume settings
    private float masterVolume;
    private float musicVolume;
    private float sfxVolume;
    
    /**
     * Constructor.
     */
    public AudioManager() {
        soundBuffers = new HashMap<>();
        sources = new ArrayList<>();
        
        masterVolume = 1.0f;
        musicVolume = 0.5f;
        sfxVolume = 0.7f;
        
        currentMusic = "";
        
        // Initialize OpenAL
        initializeOpenAL();
    }
    
    /**
     * Initialize OpenAL.
     */
    private void initializeOpenAL() {
        // Open default device
        device = alcOpenDevice((ByteBuffer) null);
        if (device == 0) {
            throw new IllegalStateException("Failed to open the default OpenAL device.");
        }
        
        // Create context
        ALCCapabilities deviceCaps = ALC.createCapabilities(device);
        IntBuffer contextAttribs = MemoryUtil.memAllocInt(16);
        contextAttribs.put(0).put(0);
        contextAttribs.flip();
        
        context = alcCreateContext(device, contextAttribs);
        MemoryUtil.memFree(contextAttribs);
        if (context == 0) {
            throw new IllegalStateException("Failed to create OpenAL context.");
        }
        
        // Make context current
        alcMakeContextCurrent(context);
        ALCapabilities alCaps = AL.createCapabilities(deviceCaps);
        
        // Create sources
        for (int i = 0; i < 16; i++) { // Create 16 sources for sound effects
            int source = alGenSources();
            sources.add(source);
        }
        
        // Create music source
        musicSource = alGenSources();
        
        // Set listener position
        alListener3f(AL_POSITION, 0, 0, 0);
        alListener3f(AL_VELOCITY, 0, 0, 0);
    }
    
    /**
     * Load a sound file.
     * 
     * @param name Sound name
     * @param filePath Sound file path
     */
    public void loadSound(String name, String filePath) {
        // Check if sound is already loaded
        if (soundBuffers.containsKey(name)) {
            return;
        }
        
        // Load sound file
        int buffer = alGenBuffers();
        
        // This would use STB to load OGG files
        // For now, just a placeholder
        
        // Add to sound buffers
        soundBuffers.put(name, buffer);
    }
    
    /**
     * Play a sound effect.
     * 
     * @param name Sound name
     * @param volume Volume (0.0 to 1.0)
     * @param pitch Pitch (0.5 to 2.0)
     * @param loop Whether to loop the sound
     * @return Source ID, or -1 if failed
     */
    public int playSound(String name, float volume, float pitch, boolean loop) {
        // Check if sound is loaded
        if (!soundBuffers.containsKey(name)) {
            return -1;
        }
        
        // Find available source
        int source = -1;
        for (int s : sources) {
            int state = alGetSourcei(s, AL_SOURCE_STATE);
            if (state != AL_PLAYING && state != AL_PAUSED) {
                source = s;
                break;
            }
        }
        
        // If no source available, return
        if (source == -1) {
            return -1;
        }
        
        // Set source properties
        alSourcei(source, AL_BUFFER, soundBuffers.get(name));
        alSourcef(source, AL_GAIN, volume * sfxVolume * masterVolume);
        alSourcef(source, AL_PITCH, pitch);
        alSourcei(source, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
        
        // Play source
        alSourcePlay(source);
        
        return source;
    }
    
    /**
     * Play music.
     * 
     * @param name Music name
     * @param loop Whether to loop the music
     */
    public void playMusic(String name, boolean loop) {
        // Check if music is already playing
        if (name.equals(currentMusic)) {
            return;
        }
        
        // Stop current music
        stopMusic();
        
        // Check if music is loaded
        if (!soundBuffers.containsKey(name)) {
            return;
        }
        
        // Set music source properties
        alSourcei(musicSource, AL_BUFFER, soundBuffers.get(name));
        alSourcef(musicSource, AL_GAIN, musicVolume * masterVolume);
        alSourcef(musicSource, AL_PITCH, 1.0f);
        alSourcei(musicSource, AL_LOOPING, loop ? AL_TRUE : AL_FALSE);
        
        // Play music
        alSourcePlay(musicSource);
        
        currentMusic = name;
    }
    
    /**
     * Stop music.
     */
    public void stopMusic() {
        alSourceStop(musicSource);
        currentMusic = "";
    }
    
    /**
     * Pause music.
     */
    public void pauseMusic() {
        alSourcePause(musicSource);
    }
    
    /**
     * Resume music.
     */
    public void resumeMusic() {
        alSourcePlay(musicSource);
    }
    
    /**
     * Stop a sound.
     * 
     * @param source Source ID
     */
    public void stopSound(int source) {
        alSourceStop(source);
    }
    
    /**
     * Set master volume.
     * 
     * @param volume Volume (0.0 to 1.0)
     */
    public void setMasterVolume(float volume) {
        masterVolume = Math.max(0.0f, Math.min(1.0f, volume));
        
        // Update music volume
        alSourcef(musicSource, AL_GAIN, musicVolume * masterVolume);
        
        // Update sound effect volumes
        for (int source : sources) {
            int state = alGetSourcei(source, AL_SOURCE_STATE);
            if (state == AL_PLAYING || state == AL_PAUSED) {
                float sourceVolume = alGetSourcef(source, AL_GAIN) / (sfxVolume * masterVolume);
                alSourcef(source, AL_GAIN, sourceVolume * sfxVolume * masterVolume);
            }
        }
    }
    
    /**
     * Set music volume.
     * 
     * @param volume Volume (0.0 to 1.0)
     */
    public void setMusicVolume(float volume) {
        musicVolume = Math.max(0.0f, Math.min(1.0f, volume));
        
        // Update music volume
        alSourcef(musicSource, AL_GAIN, musicVolume * masterVolume);
    }
    
    /**
     * Set sound effect volume.
     * 
     * @param volume Volume (0.0 to 1.0)
     */
    public void setSfxVolume(float volume) {
        sfxVolume = Math.max(0.0f, Math.min(1.0f, volume));
        
        // Update sound effect volumes
        for (int source : sources) {
            int state = alGetSourcei(source, AL_SOURCE_STATE);
            if (state == AL_PLAYING || state == AL_PAUSED) {
                float sourceVolume = alGetSourcef(source, AL_GAIN) / (sfxVolume * masterVolume);
                alSourcef(source, AL_GAIN, sourceVolume * sfxVolume * masterVolume);
            }
        }
    }
    
    /**
     * Get master volume.
     * 
     * @return Master volume
     */
    public float getMasterVolume() {
        return masterVolume;
    }
    
    /**
     * Get music volume.
     * 
     * @return Music volume
     */
    public float getMusicVolume() {
        return musicVolume;
    }
    
    /**
     * Get sound effect volume.
     * 
     * @return Sound effect volume
     */
    public float getSfxVolume() {
        return sfxVolume;
    }
    
    /**
     * Update the audio manager.
     * 
     * @param playerX Player X position
     * @param playerY Player Y position
     * @param playerZ Player Z position
     * @param lookX Look X direction
     * @param lookY Look Y direction
     * @param lookZ Look Z direction
     */
    public void update(float playerX, float playerY, float playerZ, float lookX, float lookY, float lookZ) {
        // Update listener position
        alListener3f(AL_POSITION, playerX, playerY, playerZ);
        
        // Update listener orientation
        float[] orientation = {lookX, lookY, lookZ, 0, 1, 0};
        alListenerfv(AL_ORIENTATION, orientation);
    }
    
    /**
     * Clean up resources.
     */
    public void cleanup() {
        // Delete sources
        for (int source : sources) {
            alDeleteSources(source);
        }
        alDeleteSources(musicSource);
        
        // Delete buffers
        for (int buffer : soundBuffers.values()) {
            alDeleteBuffers(buffer);
        }
        
        // Destroy context
        alcDestroyContext(context);
        
        // Close device
        alcCloseDevice(device);
    }
}
