import java.io.*;
//default
public class InfosheetParser {
    public static final int DEFAULT_PEER_COUNT = 5;
    public static final String DEFAULT_FILE_
    private int peerCount = DEFAULT_PEER_COUNT;
    private String fileName;
    private long fileSize;
    private int scenarioIndex;

    BufferedReader bufferedReader;


    public InfosheetParser(String fileNameInfoSheet) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(fileNameInfoSheet));
    }


    private void createKeyAndValuePairs(InfosheetParser isp) throws IOException {
        int keyIndex = 0;
        int valueIndex = 0;
        while (this.bufferedReader.readLine() != null) {
            String line = this.bufferedReader.readLine();
            //nicht so
        }

    }
    public void assignCommandlistComponents() throws IOException {
        try { //nicht so, mit key-value Paar -> substring -> trim etc.
            //niemand wird sich daran halten: Reihenfolge der Parameter!!!!
            //delim: ":"
            //key und value werden zusammen gespeichert, dann switch...
            //vlt mit loop alles einlesen und dann pro line substrings etc rausschneiden
/*
            String peerCount = bufferedReader.readLine();
            String fileNameToSend = bufferedReader.readLine();
            String fileSize = bufferedReader.readLine();
 */
        } catch (FileNotFoundException e) {
            System.out.println("bleep blooop");
        }
    }

    public void assignScriptComponents() {
        FileReader fileReader;
    }
}
