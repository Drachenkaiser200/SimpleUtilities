package me.unknown.utilities.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class UpdateChecker {
    private final JavaPlugin plugin;
    private final String repo; // Format: "username/repository"

    public UpdateChecker(JavaPlugin plugin, String repo) {
        this.plugin = plugin;
        this.repo = repo;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                String apiUrl = "https://api.github.com/repos/" + repo + "/releases/latest";
                String json = readUrl(apiUrl);

                String latestVersion = matchJson(json, "\"tag_name\"\\s*:\\s*\"([^\"]+)\"");
                String currentVersion = plugin.getDescription().getVersion();

                if (latestVersion == null) {
                    plugin.getLogger().warning("Could not determine latest version from GitHub.");
                    return;
                }

                if (!currentVersion.equalsIgnoreCase(latestVersion.replace("v", ""))) {
                    plugin.getLogger().warning("A new update is available!");
                    plugin.getLogger().warning("Current version: " + currentVersion);
                    plugin.getLogger().warning("Latest version: " + latestVersion);
                    plugin.getLogger().warning("Run /updateplugin to update automatically.");
                } else {
                    plugin.getLogger().info("You are using the latest version.");
                }

            } catch (Exception e) {
                plugin.getLogger().warning("Error checking for updates: " + e.getMessage());
            }
        });
    }

    public void update() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                plugin.getLogger().info("Checking GitHub for latest release...");

                String apiUrl = "https://api.github.com/repos/" + repo + "/releases/latest";
                String json = readUrl(apiUrl);

                String downloadUrl = matchJson(json, "\"browser_download_url\"\\s*:\\s*\"([^\"]+\\.jar)\"");
                if (downloadUrl == null) {
                    plugin.getLogger().warning("No .jar download URL found in release.");
                    return;
                }

                File currentJar = getPluginFile();
                if (currentJar == null) {
                    plugin.getLogger().warning("Unable to locate current plugin JAR.");
                    return;
                }

                plugin.getLogger().info("Downloading update from: " + downloadUrl);

                Path tempFile = Paths.get(currentJar.getParent(), "update_" + currentJar.getName());
                try (InputStream in = new URL(downloadUrl).openStream()) {
                    Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
                }

                Files.move(tempFile, currentJar.toPath(), StandardCopyOption.REPLACE_EXISTING);

                plugin.getLogger().info("Plugin updated successfully!");
                plugin.getLogger().info("Please restart the server to apply the update.");

            } catch (Exception e) {
                plugin.getLogger().warning("Update failed: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private String readUrl(String urlString) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        }
    }

    private String matchJson(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private File getPluginFile() {
        try {
            return new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (Exception e) {
            return null;
        }
    }
}