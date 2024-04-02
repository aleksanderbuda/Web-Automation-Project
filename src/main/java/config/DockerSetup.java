package config;

import java.io.File;
import java.io.IOException;

public class DockerSetup {

    public static void startDockerGrid() throws IOException, InterruptedException {
        String projectDir = new File("C:\\Users\\budaa\\Desktop\\project java\\Para_Bank_Framework\\docker").getAbsolutePath();
        String startDockerPath = projectDir + "\\start-docker.bat";  // Adjust the path separator for Windows
        String[] startDocker = { "cmd", "/c", "start", startDockerPath };
        ProcessBuilder processBuilder = new ProcessBuilder(startDocker);
        processBuilder.directory(new File(projectDir)); // Set the working directory
        processBuilder.start();
        Thread.sleep(15000);
    }

    public static void stopDockerGrid() throws IOException, InterruptedException {
        String projectDir = new File("").getAbsolutePath();
        String stopDockerPath = projectDir + "\\docker\\stop-docker.bat"; // Adjust the path separator for Windows
        String[] stopDocker = { "cmd", "/c", "start", stopDockerPath };
        String[] killTask = { "taskkill", "/f", "/im", "cmd.exe" };
        ProcessBuilder processBuilder1 = new ProcessBuilder(stopDocker);
        processBuilder1.directory(new File(projectDir)); // Set the working directory
        processBuilder1.start();
        Thread.sleep(6000);

        ProcessBuilder processBuilder2 = new ProcessBuilder(killTask);
        processBuilder2.start();
    }
}