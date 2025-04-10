import java.io.File;
import java.io.FileOutputStream;

public class CommandlistFactory {
    private FileOutputStream fos;


    public static final String SEND_MESSAGE = "sendMessage";
    public static final String FORMAT_DESC_FILE = "sn/file";
    public static final String OPEN_TCP = "openTCP";
    public static final String CLOSE_TCP = "closeTCP";
    public static final String EXIT = "exit";
    public static final String LIST_MESSAGES = "lsMessages";

    public CommandlistFactory(InfosheetParser infoSheetCommandComponents) {
        //how to: extract from infosheet and assign
    }
}
