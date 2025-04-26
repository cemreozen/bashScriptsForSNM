import java.io.*;

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
    public static final long DEFAULT_FILE_SIZE = 102400;
    public static final int DEFAULT_SCENARIO_INDEX = 1; //TODO

    public static final String SCENARIO_HOST_NAME = "testHost"; //oder so, todo

    private int peerCount = DEFAULT_PEER_COUNT;
    private String fileNameToBeSent = DEFAULT_FILE_NAME;
    private long fileSize = DEFAULT_FILE_SIZE;
    private int scenarioIndex = DEFAULT_SCENARIO_INDEX;

    BufferedReader bufferedReader;


    public InfoSheetParser(String fileNameInfoSheet) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(fileNameInfoSheet));
    }

    public void setFileNameToBeSent(String fileNameToBeSent) {
        if (fileNameToBeSent == null) {
            System.err.println("File name unspecified. Using default file name: " + DEFAULT_FILE_NAME);
            return;
        }
        this.fileNameToBeSent = fileNameToBeSent;
    }

    public void setFileSize(long fileSize) {
        if (fileSize <= 0) {
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
            while (bufferedReader.readLine() != null) {
                String keyValuePair = bufferedReader.readLine();
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
                        setFileSize(Long.parseLong(value));
                        break;
                    default:
                        System.err.println("Unknown key: " + key);
                        break;
                }
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

    /**
     * vielleicht ist die methode nicht in der richtigen Klasse.
     * liest parameter ein und weist die den Variablen zu.
     * @param args die eingabeparameter
     */
    public void argsParser(String[] args) {
        if (args.length == 0) {
            System.out.println("No arguments provided. Using default values.");
        }
        //todo: feste Reihenfolge -> doof
        setScenarioIndex(Integer.parseInt(args[0]));
        setPeerCount(Integer.parseInt(args[1]));
        setFileSize(Long.parseLong(args[2]));
        setFileNameToBeSent(this.fileSize + ".txt"); //todo, geht auch eleganter...
    }

    public String getFileNameToBeSent() { return fileNameToBeSent; }

    public int getPeerCount() {
        return peerCount;
    }

    public int getScenarioIndex() {
        return scenarioIndex;
    }

    public long getFileSize() {
        return fileSize;
    }
}