import java.io.*;

public class CommandlistFactory implements WriteToFile {

    public static final String SEND_MESSAGE = "sendMessage";
    public static final String FORMAT_DESC_FILE = "sn/file";
    public static final String OPEN_TCP = "openTCP";
    public static final String CLOSE_TCP = "closeTCP";
    public static final String CONNECT_TCP = "connectTCP";
    public static final int HOST_PORT = 9999;
    public static final String EXIT = "exit";
    public static final String LIST_MESSAGES = "lsMessages";
    public static final String WAIT = "wait";

    public static final String HOST_ADDRESS = "localhost";
    private InfoSheetParser infoSheetParser;

	public CommandlistFactory(String path) throws IOException {
		this.infoSheetParser = new InfoSheetParser(path);
    }

    public CommandlistFactory() {
    }

    public int getPeerCount() {
        return infoSheetParser.getPeerCount();
    }

    public InfoSheetParser getInfoSheetParser() {
        return infoSheetParser;
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
        infoSheetParser.setScenarioIndex(Integer.parseInt(args[0]));
        infoSheetParser.setPeerCount(Integer.parseInt(args[1]));
        infoSheetParser.setFileSize(args[2]);
        infoSheetParser.setFileNameToBeSent(infoSheetParser.getFileSize() + ".txt"); //todo, geht auch eleganter...
    }

    @Override
    public void writeToFile(File file, FileOutputStream fos, String goal) throws IOException {
        fos.write(goal.getBytes());
        fos.close();
    }

    public void writerLoop(int peerCount) throws IOException {
        for (int i = 1; i <= peerCount; i++) {
            File file = new File("runScenario" + i + ".txt");
            this.writeToFile(file, new FileOutputStream(file.getName()),
                    this.getInfoSheetParser().generateScenarioScript(i));
        }
    }
}
