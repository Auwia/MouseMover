import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class ExtractAllAppInFolder {

    public static void main(String[] args) {
        Map<String, String> config = readConfigFile();

        String inDirPath = config.get("inputDirectory");
        String outDirPath = config.get("outputDirectory");
        int existingFileLimit = Integer.parseInt(config.get("existFileLimit"));
        String decVar = config.get("decisionVar");

        boolean boolRunOrNot = "TRUE".equalsIgnoreCase(decVar);
        List<String> appNamesWithKey = Arrays.asList(config.get("appNameList").split(","));

        File dir = new File(inDirPath);
        File[] filesInDir = dir.listFiles();

        if (filesInDir != null && boolRunOrNot) {
            for (File file : filesInDir) {
                if (file.isDirectory()) {
                    String fileName = file.getName();

                    if (appNamesWithKey.contains(fileName)) {
                        int existingFileCount = getFileCountInOutputDirectory(outDirPath, fileName);

                        if (existingFileCount < existingFileLimit) {
                            ExtractAppMessageClass object = new ExtractAppMessageClass(
                                    inDirPath + "/" + fileName,
                                    outDirPath + "/" + fileName,
                                    fileName,
                                    existingFileCount
                            );
                            object.extractPayloadInApplication();
                        } else {
                            System.out.println(fileName + " application has exceeded the number of log file limits. So " + fileName + " has been skipped.");
                        }
                    }
                }
            }
        }
    }

    public static int getFileCountInOutputDirectory(String outDirPath, String folderName) {
        String outFileDirectory = outDirPath + "/" + folderName;
        try (Stream<Path> files = Files.list(Paths.get(outFileDirectory))) {
            return (int) files.filter(path -> path.getFileName().toString().contains("_input")).count();
        } catch (IOException e) {
            System.err.println("Error reading directory: " + outFileDirectory);
            return 0;
        }
    }

    public static Map<String, String> readConfigFile() {
        String configFileName = "Config.properties";
        Map<String, String> configHash = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(configFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\r|\n", "").replaceAll("#.*", "").trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split("\\s*=\\s*", 2);
                    if (parts.length == 2) {
                        configHash.put(parts[0], parts[1]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Config file not found: " + configFileName);
        }

        return configHash;
    }
}
