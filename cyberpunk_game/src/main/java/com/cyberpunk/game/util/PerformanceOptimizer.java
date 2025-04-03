package com.cyberpunk.game.util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles performance optimization for the game.
 */
public class PerformanceOptimizer {
    
    // FPS counter
    private int frameCount;
    private float frameTimer;
    private float fps;
    
    // Performance settings
    private int renderDistance;
    private boolean enableShadows;
    private boolean enableReflections;
    private boolean enableParticles;
    private int textureQuality;
    private int modelQuality;
    
    // Vertex Array Objects for batching
    private List<Integer> vaos;
    private List<Integer> vbos;
    
    /**
     * Constructor.
     */
    public PerformanceOptimizer() {
        frameCount = 0;
        frameTimer = 0;
        fps = 0;
        
        // Default settings
        renderDistance = 2; // 0-4, 0 = lowest, 4 = highest
        enableShadows = true;
        enableReflections = true;
        enableParticles = true;
        textureQuality = 2; // 0-2, 0 = low, 1 = medium, 2 = high
        modelQuality = 2; // 0-2, 0 = low, 1 = medium, 2 = high
        
        vaos = new ArrayList<>();
        vbos = new ArrayList<>();
    }
    
    /**
     * Update the FPS counter.
     * 
     * @param delta Time since last update in seconds
     */
    public void updateFPS(float delta) {
        frameCount++;
        frameTimer += delta;
        
        if (frameTimer >= 1.0f) {
            fps = frameCount / frameTimer;
            frameCount = 0;
            frameTimer = 0;
        }
    }
    
    /**
     * Get the current FPS.
     * 
     * @return Current FPS
     */
    public float getFPS() {
        return fps;
    }
    
    /**
     * Create a Vertex Array Object.
     * 
     * @return VAO ID
     */
    public int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
    
    /**
     * Create a Vertex Buffer Object.
     * 
     * @param data Float buffer containing vertex data
     * @param usage Buffer usage hint
     * @return VBO ID
     */
    public int createVBO(FloatBuffer data, int usage) {
        int vboID = GL30.glGenBuffers();
        vbos.add(vboID);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboID);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER, data, usage);
        return vboID;
    }
    
    /**
     * Create an Index Buffer Object.
     * 
     * @param indices Int buffer containing indices
     * @return IBO ID
     */
    public int createIBO(IntBuffer indices) {
        int iboID = GL30.glGenBuffers();
        vbos.add(iboID);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, iboID);
        GL30.glBufferData(GL30.GL_ELEMENT_ARRAY_BUFFER, indices, GL30.GL_STATIC_DRAW);
        return iboID;
    }
    
    /**
     * Set vertex attribute pointer.
     * 
     * @param attributeNumber Attribute number
     * @param size Number of components per vertex attribute
     * @param stride Stride between vertex attributes
     * @param offset Offset of the first component
     */
    public void setVertexAttribPointer(int attributeNumber, int size, int stride, int offset) {
        GL30.glVertexAttribPointer(attributeNumber, size, GL11.GL_FLOAT, false, stride, offset);
        GL30.glEnableVertexAttribArray(attributeNumber);
    }
    
    /**
     * Unbind the current VAO.
     */
    public void unbindVAO() {
        GL30.glBindVertexArray(0);
    }
    
    /**
     * Clean up resources.
     */
    public void cleanup() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        
        for (int vbo : vbos) {
            GL30.glDeleteBuffers(vbo);
        }
    }
    
    /**
     * Set render distance.
     * 
     * @param distance Render distance (0-4)
     */
    public void setRenderDistance(int distance) {
        renderDistance = Math.max(0, Math.min(4, distance));
    }
    
    /**
     * Set shadow rendering.
     * 
     * @param enable Whether to enable shadows
     */
    public void setEnableShadows(boolean enable) {
        enableShadows = enable;
    }
    
    /**
     * Set reflection rendering.
     * 
     * @param enable Whether to enable reflections
     */
    public void setEnableReflections(boolean enable) {
        enableReflections = enable;
    }
    
    /**
     * Set particle rendering.
     * 
     * @param enable Whether to enable particles
     */
    public void setEnableParticles(boolean enable) {
        enableParticles = enable;
    }
    
    /**
     * Set texture quality.
     * 
     * @param quality Texture quality (0-2)
     */
    public void setTextureQuality(int quality) {
        textureQuality = Math.max(0, Math.min(2, quality));
    }
    
    /**
     * Set model quality.
     * 
     * @param quality Model quality (0-2)
     */
    public void setModelQuality(int quality) {
        modelQuality = Math.max(0, Math.min(2, quality));
    }
    
    /**
     * Get render distance.
     * 
     * @return Render distance
     */
    public int getRenderDistance() {
        return renderDistance;
    }
    
    /**
     * Check if shadows are enabled.
     * 
     * @return true if shadows are enabled
     */
    public boolean isEnableShadows() {
        return enableShadows;
    }
    
    /**
     * Check if reflections are enabled.
     * 
     * @return true if reflections are enabled
     */
    public boolean isEnableReflections() {
        return enableReflections;
    }
    
    /**
     * Check if particles are enabled.
     * 
     * @return true if particles are enabled
     */
    public boolean isEnableParticles() {
        return enableParticles;
    }
    
    /**
     * Get texture quality.
     * 
     * @return Texture quality
     */
    public int getTextureQuality() {
        return textureQuality;
    }
    
    /**
     * Get model quality.
     * 
     * @return Model quality
     */
    public int getModelQuality() {
        return modelQuality;
    }
    
    /**
     * Apply performance settings based on auto-detection.
     */
    public void autoDetectSettings() {
        // This would detect system capabilities and set appropriate settings
        // For now, just use medium settings
        renderDistance = 2;
        enableShadows = true;
        enableReflections = true;
        enableParticles = true;
        textureQuality = 1;
        modelQuality = 1;
    }
    
    /**
     * Apply low performance settings.
     */
    public void applyLowSettings() {
        renderDistance = 1;
        enableShadows = false;
        enableReflections = false;
        enableParticles = false;
        textureQuality = 0;
        modelQuality = 0;
    }
    
    /**
     * Apply medium performance settings.
     */
    public void applyMediumSettings() {
        renderDistance = 2;
        enableShadows = true;
        enableReflections = false;
        enableParticles = true;
        textureQuality = 1;
        modelQuality = 1;
    }
    
    /**
     * Apply high performance settings.
     */
    public void applyHighSettings() {
        renderDistance = 3;
        enableShadows = true;
        enableReflections = true;
        enableParticles = true;
        textureQuality = 2;
        modelQuality = 2;
    }
    
    /**
     * Apply ultra performance settings.
     */
    public void applyUltraSettings() {
        renderDistance = 4;
        enableShadows = true;
        enableReflections = true;
        enableParticles = true;
        textureQuality = 2;
        modelQuality = 2;
    }
    
    /**
     * Get the actual render distance in game units.
     * 
     * @return Render distance in game units
     */
    public float getActualRenderDistance() {
        switch (renderDistance) {
            case 0:
                return 200.0f;
            case 1:
                return 400.0f;
            case 2:
                return 600.0f;
            case 3:
                return 800.0f;
            case 4:
                return 1000.0f;
            default:
                return 600.0f;
        }
    }
    
    /**
     * Get the texture size multiplier based on quality.
     * 
     * @return Texture size multiplier
     */
    public float getTextureSizeMultiplier() {
        switch (textureQuality) {
            case 0:
                return 0.5f;
            case 1:
                return 1.0f;
            case 2:
                return 2.0f;
            default:
                return 1.0f;
        }
    }
    
    /**
     * Get the model detail multiplier based on quality.
     * 
     * @return Model detail multiplier
     */
    public float getModelDetailMultiplier() {
        switch (modelQuality) {
            case 0:
                return 0.5f;
            case 1:
                return 1.0f;
            case 2:
                return 2.0f;
            default:
                return 1.0f;
        }
    }
}
