package config;

import java.io.IOException;

public class DockerSetup {

    public static void startDockerGrid() throws IOException, InterruptedException {
        String[] startDocker = { "cmd", "/c", "start", "docker\\start_docker.bat" };
        ProcessBuilder processBuilder = new ProcessBuilder(startDocker);
        processBuilder.start();
        Thread.sleep(15000);
    }

    public static void stopDockerGrid() throws IOException, InterruptedException {
        String[] stopDocker = { "cmd", "/c", "start", "docker\\stop_docker.bat" };
        String[] killTask = { "taskkill", "/f", "/im", "cmd.exe" };
        ProcessBuilder processBuilder1 = new ProcessBuilder(stopDocker);
        processBuilder1.start();
        Thread.sleep(6000);

        ProcessBuilder processBuilder2 = new ProcessBuilder(killTask);
        processBuilder2.start();
    }
}