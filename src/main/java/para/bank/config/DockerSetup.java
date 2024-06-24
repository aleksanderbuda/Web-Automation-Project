package para.bank.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.interactions.Pause;

import java.io.File;
import java.io.IOException;

import static com.zebrunner.carina.utils.common.CommonUtils.pause;

public class DockerSetup {
        protected static Logger log = LogManager.getLogger();


        public static void startDockerGrid() throws IOException, InterruptedException {
            String[] startDocker = { "cmd", "/c", "start", "docker\\start_docker.bat" };
            ProcessBuilder processBuilder = new ProcessBuilder(startDocker);
            processBuilder.start();
            pause(15000);
        }

        public static void stopDockerGrid() throws IOException, InterruptedException {
            String[] stopDocker = { "cmd", "/c", "start", "docker\\stop_docker.bat" };
            String[] killTask = { "taskkill", "/f", "/im", "cmd.exe" };
            ProcessBuilder processBuilder1 = new ProcessBuilder(stopDocker);
            processBuilder1.start();
            pause(6000);

            ProcessBuilder processBuilder2 = new ProcessBuilder(killTask);
            processBuilder2.start();
        }
    }