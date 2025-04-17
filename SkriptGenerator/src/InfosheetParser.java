import java.io.*;
//default
public class InfosheetParser {
    public static final int DEFAULT_PEER_COUNT = 5;
    public static final String DEFAULT_FILE_NAME = "100KB.txt";
    private int peerCount = DEFAULT_PEER_COUNT;
    private String fileNametoBeSent;
    private long fileSize = 102400;
    private int scenarioIndex;

    BufferedReader bufferedReader;


    public InfosheetParser(String fileNameInfoSheet) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(fileNameInfoSheet));
    }

    public void setFileNametoBeSent(String fileNametoBeSent) {
        this.fileNametoBeSent = fileNametoBeSent;
    }

    public String getFileNametoBeSent() {
        return fileNametoBeSent;
    }

    public int getPeerCount() {
        return peerCount;
    }

    public int getScenarioIndex() {
        return scenarioIndex;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setPeerCount(int peerCount) {
        this.peerCount = peerCount;
    }

    public void setScenarioIndex(int scenarioIndex) {
        this.scenarioIndex = scenarioIndex;
    }

    public void assignCommandlistComponents() throws IOException {
        try {
            while (bufferedReader.readLine() != null) {
                String keyValuePair = bufferedReader.readLine();
                String key = keyValuePair.substring(0, keyValuePair.indexOf(":")).trim();
                String value = keyValuePair.substring(keyValuePair.indexOf(":") + 1).trim();

                switch (key) {
                    case "Peer Count":
                        setPeerCount(Integer.parseInt(value));
                        break;
                    case "Scenario index":
                        setScenarioIndex(Integer.parseInt(value));
                        break;
                    case "File name":
                        setFileNametoBeSent(value);
                        break;
                    case "File size":
                        setFileSize(Long.parseLong(value));
                        break;
                    default:
                        break;
                }
            }
            //nicht so, mit key-value Paar -> substring -> trim etc.
            //niemand wird sich daran halten: Reihenfolge der Parameter!!!!
            //delim: ":"
            //key und value werden zusammen gespeichert, dann switch...
            //vlt mit loop alles einlesen und dann pro line substrings etc rausschneiden
        } catch (FileNotFoundException e) {
            System.out.println("Infosheet: file not found. Test will be ran with default parameters.");
        }
    }
}
