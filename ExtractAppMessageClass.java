import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ExtractAppMessageClass {

    private String inDirPath;
    private String outDirPath;
    private String appName;
    private int existNumOfFiles;

    public ExtractAppMessageClass(String inDirPath, String outDirPath, String appName, int existNumOfFiles) {
        this.inDirPath = inDirPath;
        this.outDirPath = outDirPath;
        this.appName = appName;
        this.existNumOfFiles = existNumOfFiles;
    }

    public void extractPayloadInApplication() {
        String directoryPath = this.inDirPath;
        String expectedOutputPath = this.outDirPath + "/" + this.appName + "/output";
        String inputPayloadPath = this.inDirPath + "/" + this.appName + "/input";
        String errorLogFileName = this.outDirPath + "/" + this.appName + ".log";

        File dir = new File(directoryPath);
        File[] dirFiles = dir.listFiles();

        if (dirFiles != null) {
            int fileCount = dirFiles.length;

            // Create necessary directories
            new File(inputPayloadPath).mkdirs();
            new File(expectedOutputPath).mkdirs();

            if (fileCount != 0) {
                System.out.println("\nThere are " + fileCount + " files in the " + this.appName + " directory. The script has started processing files...\n");

                int loopCounter = 0;
                int successCount = 0;
                int failedCount = 0;

                for (File file : dirFiles) {
                    if (file.isFile()) {
                        String inputFileName = directoryPath + "/" + file.getName();
                        String outputFileName = expectedOutputPath + "/" + file.getName() + ".out";

                        loopCounter++;
                        boolean extractStatus = extractToPayload(inputFileName, outputFileName, inputPayloadPath, expectedOutputPath, errorLogFileName, this.appName);

                        if (extractStatus) {
                            successCount++;
                        } else {
                            failedCount++;
                        }

                        System.out.println("Processed file: " + file.getName() + " (Success: " + successCount + ", Failed: " + failedCount + ")");
                    }
                }

                System.out.println("\nTotal files processed for " + this.appName + ": " + loopCounter);
            } else {
                System.out.println("\nThere are zero files in the " + this.appName + " directory. The script will not be able to extract from this application.");
            }
        }
    }

    public boolean extractToPayload(String inputFileName, String outputFileName, String inputMsgDirPath, String outputMsgDirPath, String errorLogFileName, String appName) {
        // Dummy payload extraction logic
        System.out.println("Extracting payload from " + inputFileName + "...");
        boolean success = true;  // Assume success for the sake of example

        if (success) {
            return true;
        } else {
            logWarningMessage("Failed to extract payload for " + inputFileName, errorLogFileName);
            return false;
        }
    }

    public void logWarningMessage(String errorMessage, String errorLogFileName) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(errorLogFileName, true));
            writer.write(errorMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Cannot open log file: " + errorLogFileName);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing log file: " + errorLogFileName);
            }
        }
    }

    public static void main(String[] args) {
        // Example instantiation and execution
        ExtractAppMessageClass extractor = new ExtractAppMessageClass("/path/to/input", "/path/to/output", "MyApp", 10);
        extractor.extractPayloadInApplication();
    }
}
