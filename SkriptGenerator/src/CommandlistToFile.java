import java.io.*;

import static java.lang.Long.parseLong;

public class CommandlistToFile {

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
    private final TestParameterAllocation testParameterAllocation;

    public static final String[] SCENARIOS  = {"TCP Filetransfer"};

    /**
     * @param path the path to the info sheet
     * @throws IOException if an I/O error occurs
     */
    public CommandlistToFile(String path) throws IOException {
		this.testParameterAllocation = new TestParameterAllocation(path);
    }

    /**
     * Default constructor.
     */
    public CommandlistToFile() {
            this.testParameterAllocation = new TestParameterAllocation();
    }

    /**
     * Returns the scenario index.
     * @return the scenario index
     */
    public int getPeerCount() {
        return testParameterAllocation.getPeerCount();
    }

    /**
     * Returns the object that stores the test parameters and their assigning methods.
     */
    public TestParameterAllocation getTestParameterStorageAndAllocation() {
        return testParameterAllocation;
    }

    /**
     * vielleicht ist die methode nicht in der richtigen Klasse.
     * liest parameter ein und weist die den Variablen zu.
     * @param args die eingabeparameter
     */
    public void argsParser(String[] args) throws IOException {
        if (args.length == 2) {
            testParameterAllocation.setScenarioIndex(Integer.parseInt(args[0]));
            testParameterAllocation.setPeerCount(Integer.parseInt(args[1]));
        }
        if (args.length == 5) {
            testParameterAllocation.setScenarioIndex(Integer.parseInt(args[0]));
            testParameterAllocation.setPeerCount(Integer.parseInt(args[1]));
            testParameterAllocation.setFileSize(args[2], args[3]);
            long size = testParameterAllocation.calculateFileSize(parseLong(args[2]));
            FileUtils.createSpecifiedFile(size, args[4]);
        }
    }

    /**
     * Writes the commandlist to a file.
     * @param peerCount the number of peers
     * @throws IOException if an I/O error occurs
     */
    public void writerLoop(int peerCount) throws IOException {
        if (this.getTestParameterStorageAndAllocation().getScenarioIndex() == 0) {
            for (int i = 1; i <= peerCount; i++) {
                File file = new File("runScenario" + i + ".txt");

                FileUtils.writeToFile(new FileOutputStream(file.getName()),
                        this.getTestParameterStorageAndAllocation().generateRunScenarioCommandsFileTransfer(i));

                System.out.println("File created: " + file.getName());
            }
        }
    }
}
