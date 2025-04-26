import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public interface WriteToFile {

	void writeToFile(File file, FileOutputStream fos, String goal) throws IOException;
	// tbc

}
