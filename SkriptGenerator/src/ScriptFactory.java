import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class ScriptFactory  implements WriteToFile {

    private FileWriter fileWriter;
    public ScriptFactory(File infoSheet) {
        //extract info from info sheet and assign
    }

    public ScriptFactory(InfosheetParser infosheetParser) throws IOException {
        fileWriter = new FileWriter("testSkript.sh");
    }

    @Override
    public File writeToFile(File file, FileWriter fileWriter) throws IOException {
        return null;
    }
}