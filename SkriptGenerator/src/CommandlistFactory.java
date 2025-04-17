import java.io.*;

public class CommandlistFactory implements WriteToFile {

    private FileWriter fileWriter;
    public static final String SEND_MESSAGE = "sendMessage";
    public static final String FORMAT_DESC_FILE = "sn/file";
    public static final String OPEN_TCP = "openTCP";
    public static final String CLOSE_TCP = "closeTCP";
    public static final String EXIT = "exit";
    public static final String LIST_MESSAGES = "lsMessages";

    public CommandlistFactory(InfosheetParser infoSheetCommandComponents) throws IOException {
        fileWriter = new FileWriter("commandlist.txt");
    }

    @Override
    public File writeToFile(File file, FileWriter fileWriter) throws IOException {

    }
}
