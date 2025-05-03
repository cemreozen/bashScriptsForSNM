import java.io.*;
import java.util.Objects;

/**
 * This class is responsible for parsing the info sheet and generating the command list for the peers.
 * It reads the command list from a file and assigns the parameters to the corresponding variables.
 * It also generates a command list for the peers to execute the test scenario.
 * Alternatively, the test variables can be passed as command line arguments.
 * @author Cemre
 */
public class InfoSheetParser {
    public static final int DEFAULT_PEER_COUNT = 5;
    public static final String DEFAULT_FILE_NAME = "100KB.txt";
    public static final String DEFAULT_FILE_SIZE = "102400";
    public static final int DEFAULT_SCENARIO_INDEX = 1; //TODO

    public static final String SCENARIO_HOST_NAME = "testHost"; //oder so, todo

    private int peerCount = DEFAULT_PEER_COUNT;
    private String fileNameToBeSent = DEFAULT_FILE_NAME;
    private String fileSize = DEFAULT_FILE_SIZE;
    private int scenarioIndex = DEFAULT_SCENARIO_INDEX;

    BufferedReader bufferedReader;


    public InfoSheetParser(String pathInfoSheet) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(pathInfoSheet));
        try {
            assignCommandListComponents();
        } catch (IOException e) {
            System.err.println("Error reading the info sheet: " + e.getMessage());
        }
    }

    public void setFileNameToBeSent(String fileNameToBeSent) {
        if (fileNameToBeSent == null) {
            System.err.println("File name unspecified. Using default file name: " + DEFAULT_FILE_NAME);
            return;
        }
        this.fileNameToBeSent = fileNameToBeSent;
    }

    public void setFileSize(String fileSize) {
        long fileSizeNumerical = Long.parseLong(fileSize.substring(0, fileSize.length()-2).trim());
        if (fileSizeNumerical <= 0) {
            System.err.println("File size invalid. Using default file size: " + DEFAULT_FILE_SIZE);
            return;
        }
        this.fileSize = fileSize;
    }

    public void setPeerCount(int peerCount) {
        if (peerCount <= 0) {
            System.err.println("Peer count invalid. Using default peer count: " + DEFAULT_PEER_COUNT);
            return;
        }
        this.peerCount = peerCount;
    }

    public void setScenarioIndex(int scenarioIndex) {
        if (scenarioIndex <= 0) {
            System.err.println("Scenario index invalid. Using default scenario index: " + DEFAULT_SCENARIO_INDEX);
            return;
        }
        this.scenarioIndex = scenarioIndex;
    }

    /**
     * Reads the command list from the given file and assigns the parameters to the corresponding variables.
     * @throws IOException
     */
    public void assignCommandListComponents() throws IOException {
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
                            setFileSize(value);
                            break;
                        default:
                            System.err.println("Unknown key: " + key);
                            break;
                    }
                    nextLine = bufferedReader.readLine();
            }

            //nicht so, mit key-value Paar -> substring -> trim etc.
            //niemand wird sich daran halten: Reihenfolge der Parameter!!!!
            //delim: ":"
            //key und value werden zusammen gespeichert, dann switch...
            //vlt mit loop alles einlesen und dann pro line substrings etc rausschneiden
        } catch (FileNotFoundException e) {
            System.out.println("Infosheet: file not found. Test will run with default parameters.");
        }
    }

    /**
     * Generates a commandlist for the peers to execute the test scenario.
     * @param peerIndex the index of the peer
     * @return the commandlist as a string
     */
    public String generateScenarioScript(int peerIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder scenarioScript = stringBuilder.append(CommandlistFactory.WAIT + ' ' + 100 + " \n")
                .append(CommandlistFactory.SEND_MESSAGE).append(" snm_P")
                .append(peerIndex)
                .append(".txt ")
                .append(CommandlistFactory.FORMAT_DESC_FILE)
                .append('\n')
                .append(CommandlistFactory.SEND_MESSAGE)
                .append(" asapLogsP")
                .append(peerIndex)
                .append(".txt ")
                .append(CommandlistFactory.FORMAT_DESC_FILE)
                .append('\n')
                .append(CommandlistFactory.CONNECT_TCP + ' ')
                .append(CommandlistFactory.HOST_ADDRESS + ' ')
                .append(CommandlistFactory.HOST_PORT)
                .append('\n')
                .append(CommandlistFactory.WAIT + ' ' + 1000)
                .append('\n')
                .append(CommandlistFactory.EXIT);
        return scenarioScript.toString();
    }

    /**
     * Generates a test scenario to be distributed to all peers by the test host.
     * @return the scenario as a string
     */
    public String generateRunScenario() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder runScenario = stringBuilder.append(CommandlistFactory.CONNECT_TCP + " " + CommandlistFactory.HOST_ADDRESS + ' ' + CommandlistFactory.HOST_PORT)
                .append('\n')
                .append(CommandlistFactory.WAIT + ' ' + 100 + " ")
                .append('\n')
                .append(CommandlistFactory.LIST_MESSAGES)
                .append('\n')
                .append(CommandlistFactory.EXIT);
        return runScenario.toString();
    }

    public String getFileNameToBeSent() { return fileNameToBeSent; }

    public int getPeerCount() {
        return peerCount;
    }

    public int getScenarioIndex() {
        return scenarioIndex;
    }

    public String getFileSize() {
        return fileSize;
    }
}