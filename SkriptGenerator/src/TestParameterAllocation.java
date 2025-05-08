import java.io.*;

import static java.lang.Long.parseLong;

/**
 * This class is responsible for parsing the info sheet and generating the command list for the peers.
 * It reads the command list from a file and assigns the parameters to the corresponding variables.
 * It also generates a command list for the peers to execute the test scenario.
 * Alternatively, the test variables can be passed as command line arguments.
 * @author Cemre
 */
public class TestParameterAllocation {
    public static final int DEFAULT_PEER_COUNT = 5;
    public static final String DEFAULT_FILE_NAME = "default.txt";
    public static final String DEFAULT_FILE_SIZE = "1024";
    public static final String DEFAULT_FILE_SIZE_UNIT = "kB";
    public static final int DEFAULT_SCENARIO_INDEX = Scenarios.TCP_KETTE_FILE_TRANSFER.ordinal();

    public static final String SCENARIO_HOST_NAME = "testHost"; //oder so

    private int peerCount = DEFAULT_PEER_COUNT;
    private String fileNameToBeSent = DEFAULT_FILE_NAME;
    private long fileSize = parseLong(DEFAULT_FILE_SIZE);
    private String fileSizeUnit = DEFAULT_FILE_SIZE_UNIT;
    private int scenarioIndex = DEFAULT_SCENARIO_INDEX;
    /**
     * Constructor that initializes the InfoSheetParser with the given file path.
     *
     * @param pathInfoSheet the path to the info sheet file
     */
    public TestParameterAllocation(String pathInfoSheet) throws NullPointerException {
        try (BufferedReader bufferedReader = FileUtils.getBufferedReader(pathInfoSheet)) {
            assignCommandListComponents(bufferedReader);
        } catch (IOException e) {
            System.err.println("Cannot read the info sheet: " + e.getMessage());
        }
    }

    /**
     * Default constructor that initializes the InfoSheetParser with default values.
     * This constructor should be used for command line argument parsing.
     */
    public TestParameterAllocation() {
    }

    /**
     * Sets the file name to be sent.
     *
     * @param fileNameToBeSent the name of the file to be sent
     */
    public void setFileNameToBeSent(String fileNameToBeSent) {
        if (fileNameToBeSent == null || fileNameToBeSent.isEmpty()) {
            System.err.println("File name unspecified. Using default file name: " + DEFAULT_FILE_NAME);
            return;
        }
        this.fileNameToBeSent = fileNameToBeSent;
    }

    /**
     * Sets the file size.
     *
     * @param fileSize the size of the file
     */
    public void setFileSize(String fileSize, String fileSizeUnit) {
        if (fileSize == null || fileSize.isEmpty()) {
            System.err.println("File size unspecified. Using default file size: " + DEFAULT_FILE_SIZE);
            return;
        }
        if (fileSizeUnit == null || fileSizeUnit.isEmpty()) {
            System.err.println("File size unit unspecified. Using default file size unit: " + DEFAULT_FILE_SIZE_UNIT);
            return;
        }
        if (parseLong(fileSize) <= 0) {
            System.err.println("File size invalid. Using default file size: " + DEFAULT_FILE_SIZE);
            return;
        }
        setFileSizeUnit(fileSizeUnit);
        this.fileSize = calculateFileSize(parseLong(fileSize));

    }

    /**
     * Sets the peer count.
     *
     * @param peerCount the number of peers
     */
    public void setPeerCount(int peerCount) {
        if (peerCount <= 0) {
            System.err.println("Peer count invalid. Using default peer count: " + DEFAULT_PEER_COUNT);
            return;
        }
        this.peerCount = peerCount;
    }

    /**
     * Sets the scenario index.
     *
     * @param scenarioIndex the index of the scenario
     */
    public void setScenarioIndex(int scenarioIndex) {
        if (scenarioIndex < 0) {
            System.err.println("Scenario index invalid. Using default scenario index: " + DEFAULT_SCENARIO_INDEX);
            return;
        }
        this.scenarioIndex = scenarioIndex;
    }

    /**
     * Reads the command list from the given file and assigns the parameters to the corresponding variables.
     *
     * @throws IOException if the file cannot be read
     */
    public void assignCommandListComponents(BufferedReader bufferedReader) throws IOException {
        try {
            String nextLine = bufferedReader.readLine();
            while (nextLine != null) {
                String keyValuePair = nextLine;
                String key = keyValuePair.substring(0, keyValuePair.indexOf(":")).trim();
                String value = keyValuePair.substring(keyValuePair.indexOf(":") + 1).trim();

                switch (key.toLowerCase()) {
                    case "peer count":
                        setPeerCount(Integer.parseInt(value));
                        break;
                    case "scenario index":
                        setScenarioIndex(Integer.parseInt(value));
                        break;
                    case "file name":
                        setFileNameToBeSent(value);
                        break;
                    case "file size":
                        String unit = value.substring(value.length() - 2);
                        String size = value.substring(0, value.indexOf(unit)).trim();
                        setFileSize(size, unit);
                        break;
                    default:
                        System.err.println("Unknown key: " + key);
                        break;
                }
                nextLine = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Infosheet: file not found. Test will run with default parameters.");
        }
    }

    private void setFileSizeUnit(String value) {
        if (value == null || value.isEmpty()) {
            System.err.println("File size unit unspecified. Using default file size unit: " + DEFAULT_FILE_SIZE_UNIT);
            return;
        }
        this.fileSizeUnit = value;
    }

    /**
     * Generates a commandlist for the peers to execute the test scenario.
     *
     * @param peerIndex the index of the peer
     * @return the commandlist as a string
     */
    public String generateRunScenarioCommandsFileTransfer(int peerIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder scenarioScript = stringBuilder
                .append(CommandlistToFile.WAIT + ' ')
                .append(100 * peerCount)
                .append(" \n")
                .append(CommandlistToFile.SEND_MESSAGE).append(" snm_P")
                .append(peerIndex)
                .append(".txt ")
                .append(CommandlistToFile.FORMAT_DESC_FILE)
                .append('\n')
                .append(CommandlistToFile.SEND_MESSAGE)
                .append(" asapLogsP")
                .append(peerIndex)
                .append(".txt ")
                .append(CommandlistToFile.FORMAT_DESC_FILE)
                .append('\n')
                .append(CommandlistToFile.CONNECT_TCP + ' ')
                .append(CommandlistToFile.HOST_ADDRESS + ' ')
                .append(CommandlistToFile.HOST_PORT)
                .append('\n')
                .append(CommandlistToFile.WAIT + ' ' + 1000)
                .append('\n')
                .append(CommandlistToFile.EXIT);
        return scenarioScript.toString();
    }

    /**
     * Generates a test scenario to be distributed to all peers by the test host.
     *
     * @return the scenario as a string
     */
    public String generateReceiveCommands() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder runScenario = stringBuilder.append(CommandlistToFile.CONNECT_TCP + " " + CommandlistToFile.HOST_ADDRESS + ' ' + CommandlistToFile.HOST_PORT)
                .append('\n')
                .append(CommandlistToFile.WAIT + ' ' + 100 + " ")
                .append('\n')
                .append(CommandlistToFile.LIST_MESSAGES)
                .append('\n')
                .append(CommandlistToFile.EXIT);
        return runScenario.toString();
    }

    /**
     * Returns the scenario index.
     *
     * @return file name to be sent
     */
    public String getFileNameToBeSent() {
        return fileNameToBeSent;
    }

    /**
     * Returns the file size.
     *
     * @return file size
     */
    public int getPeerCount() {
        return peerCount;
    }

    /**
     * Returns the scenario index.
     *
     * @return scenario index
     */
    public int getScenarioIndex() {
        return scenarioIndex;
    }

    /**
     * Returns the file name to be sent.
     *
     * @return file name to be sent
     */
    public String getFileSize() {
        return String.valueOf(fileSize) + " " + DEFAULT_FILE_SIZE_UNIT;
    }

    public long calculateFileSize(long fileSize) {
        if (fileSize <= 0)
            return parseLong(DEFAULT_FILE_SIZE);
        switch (fileSizeUnit.toUpperCase()) {
            case "KB":
                fileSize *= 1024;
                break;
            case "MB":
                fileSize *= 1024 * 1024;
                break;
            case "GB":
                fileSize *= 1024 * 1024 * 1024;
                break;
            default:
                System.err.println("Invalid file size unit. Using " + DEFAULT_FILE_SIZE_UNIT);
        }
        return fileSize;
    }
        /**
         * Creates a file to be sent.
         *
         * @throws IOException if an I/O error occurs
         */
    public void createFileToSend() throws IOException {
        FileUtils.createSpecifiedFile(fileSize, fileNameToBeSent);
    }
}