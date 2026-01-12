package fr.xxathyx.mediaplayer.translation;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import fr.xxathyx.mediaplayer.Main;

/** 
* The Translater class is used in {@link Main} in order to extract plugin langage translations.
* It consists in a single method, see {@link #createTranslationFile(String)}.
* 
* @author  hwic
* @version 1.0.0
* @since   2022-07-16 
*/

public class Translater {
	
	private Main plugin = Main.getPlugin(Main.class);
	private File file;

    public void exportBundledTranslations() {
        List<String> translations = listBundledTranslations();
        if(translations.isEmpty()) {
            return;
        }
        for(String langage : translations) {
            try {
                createTranslationFile(langage);
            }catch (URISyntaxException | IOException e) {
                Bukkit.getLogger().warning("[MediaPlayer]: Failed to export translation " + langage + ".yml.");
            }
        }
    }
	
    /**
     * Export the translation-file contained within the jar-file, onto the translations
     * folder, according to a country-code.
     *
     * @param langage The langage country-code to be export.
     */
	
    public void createTranslationFile(String langage) throws URISyntaxException, IOException {
		
		file = new File(plugin.getDataFolder() + "/translations/", langage + ".yml");
	    
		if(!file.exists()) {
			file.getParentFile().mkdirs();
		}
		
		if(!file.exists()) {
			
			URL resource = Main.class.getResource("translations/" + langage + ".yml");
			if(resource == null) {
				Bukkit.getLogger().warning("[MediaPlayer]: Missing bundled translation " + langage + ".yml, skipping export.");
				return;
			}
			URI uri = resource.toURI();

			if("jar".equals(uri.getScheme())) {
			    for(FileSystemProvider provider: FileSystemProvider.installedProviders()) {
			        if(provider.getScheme().equalsIgnoreCase("jar")) {
			            try {
			                provider.getFileSystem(uri);
			            }catch (FileSystemNotFoundException e) {
			                provider.newFileSystem(uri, Collections.emptyMap());
			            }
			        }
			    }
			}
			Path source = Paths.get(uri);
			
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_GRAY + "[MediaPlayer]: " + ChatColor.GRAY + "Installing langage: " + langage + ".");
			
			Files.copy(source, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
    }	

    private List<String> listBundledTranslations() {
        List<String> translations = new ArrayList<>();
        URL translationsRoot = Main.class.getResource("translations/");
        if(translationsRoot == null) {
            return translations;
        }
        try {
            if("jar".equals(translationsRoot.getProtocol())) {
                JarURLConnection connection = (JarURLConnection) translationsRoot.openConnection();
                JarFile jarFile = connection.getJarFile();
                String prefix = "fr/xxathyx/mediaplayer/translations/";
                for(JarEntry entry : java.util.Collections.list(jarFile.entries())) {
                    String name = entry.getName();
                    if(name.startsWith(prefix) && name.endsWith(".yml")) {
                        String language = name.substring(prefix.length(), name.length() - ".yml".length());
                        translations.add(language);
                    }
                }
            }else if("file".equals(translationsRoot.getProtocol())) {
                Path dir = Paths.get(translationsRoot.toURI());
                try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.yml")) {
                    for(Path entry : stream) {
                        String filename = entry.getFileName().toString();
                        translations.add(filename.replace(".yml", ""));
                    }
                }
            }
        }catch (IOException | URISyntaxException e) {
            Bukkit.getLogger().warning("[MediaPlayer]: Failed to scan bundled translations.");
        }
        return translations;
    }
}
