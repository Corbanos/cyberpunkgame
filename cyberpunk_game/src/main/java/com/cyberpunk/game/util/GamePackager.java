package com.cyberpunk.game.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Handles packaging and distribution of the game.
 */
public class GamePackager {
    
    // Game version
    private String gameVersion;
    
    // Output directory
    private String outputDirectory;
    
    // Files to include
    private List<String> includeFiles;
    
    // Directories to include
    private List<String> includeDirectories;
    
    /**
     * Constructor.
     * 
     * @param gameVersion Game version
     * @param outputDirectory Output directory
     */
    public GamePackager(String gameVersion, String outputDirectory) {
        this.gameVersion = gameVersion;
        this.outputDirectory = outputDirectory;
        
        includeFiles = new ArrayList<>();
        includeDirectories = new ArrayList<>();
    }
    
    /**
     * Add a file to include in the package.
     * 
     * @param filePath File path
     */
    public void addFile(String filePath) {
        includeFiles.add(filePath);
    }
    
    /**
     * Add a directory to include in the package.
     * 
     * @param directoryPath Directory path
     */
    public void addDirectory(String directoryPath) {
        includeDirectories.add(directoryPath);
    }
    
    /**
     * Create the game package.
     * 
     * @return true if successful
     */
    public boolean createPackage() {
        try {
            // Create output directory if it doesn't exist
            File outputDir = new File(outputDirectory);
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            
            // Create ZIP file
            String zipFileName = outputDirectory + "/cyberpunk-game-" + gameVersion + ".zip";
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            
            // Add files
            for (String filePath : includeFiles) {
                File file = new File(filePath);
                if (file.exists()) {
                    addToZip(file, file.getName(), zos);
                }
            }
            
            // Add directories
            for (String directoryPath : includeDirectories) {
                File directory = new File(directoryPath);
                if (directory.exists() && directory.isDirectory()) {
                    addDirectoryToZip(directory, "", zos);
                }
            }
            
            // Close ZIP file
            zos.close();
            fos.close();
            
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Add a file to the ZIP file.
     * 
     * @param file File to add
     * @param entryName Entry name in ZIP
     * @param zos ZIP output stream
     * @throws IOException If an I/O error occurs
     */
    private void addToZip(File file, String entryName, ZipOutputStream zos) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(entryName);
        zos.putNextEntry(zipEntry);
        
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        
        zos.closeEntry();
        fis.close();
    }
    
    /**
     * Add a directory to the ZIP file.
     * 
     * @param directory Directory to add
     * @param basePath Base path in ZIP
     * @param zos ZIP output stream
     * @throws IOException If an I/O error occurs
     */
    private void addDirectoryToZip(File directory, String basePath, ZipOutputStream zos) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String entryName = basePath + file.getName();
                if (file.isDirectory()) {
                    addDirectoryToZip(file, entryName + "/", zos);
                } else {
                    addToZip(file, entryName, zos);
                }
            }
        }
    }
    
    /**
     * Create a runnable JAR file.
     * 
     * @return true if successful
     */
    public boolean createJar() {
        // This would use the jar command to create a runnable JAR file
        // For now, just a placeholder
        return true;
    }
    
    /**
     * Create a Windows executable.
     * 
     * @return true if successful
     */
    public boolean createWindowsExecutable() {
        // This would use a tool like Launch4j to create a Windows executable
        // For now, just a placeholder
        return true;
    }
    
    /**
     * Create a macOS application bundle.
     * 
     * @return true if successful
     */
    public boolean createMacOSBundle() {
        // This would create a macOS application bundle
        // For now, just a placeholder
        return true;
    }
    
    /**
     * Create a Linux package.
     * 
     * @return true if successful
     */
    public boolean createLinuxPackage() {
        // This would create a Linux package
        // For now, just a placeholder
        return true;
    }
    
    /**
     * Create packages for all platforms.
     * 
     * @return true if successful
     */
    public boolean createAllPackages() {
        boolean success = true;
        
        success &= createPackage();
        success &= createJar();
        success &= createWindowsExecutable();
        success &= createMacOSBundle();
        success &= createLinuxPackage();
        
        return success;
    }
}
