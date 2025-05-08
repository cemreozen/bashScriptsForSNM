import java.io.File;
import java.io.IOException;

/**
 * This class is responsible for generating the command list for the peers based on the provided info sheet.
 * It reads the command list from a file and assigns the parameters to the corresponding variables.
 * It also generates a command list for the peers to execute the test scenario.
 * Alternatively, the test variables can be passed as command line arguments.
 * @author Cemre
 */
public class Main {
	public static final String USAGE_STRING = "Usage: java -jar SkriptGenerator.jar <Info sheet path>" +
		"\nAlternatively: java -jar SkriptGenerator.jar <scenario index> <peer count> <file size> <file name>";
	public static final String FILE_NONEXISTENT = "File does not exist. Please check the file path.";

	/**
	 * Main method to run the SkriptGenerator.
	 * @param args command line arguments
	 * @throws IOException if an I/O error occurs
	 */
	public static void main(String[] args) throws IOException {
		String filePath;
		CommandlistToFile commandlistToFile;
		if (args.length == 0) {
			System.out.println(USAGE_STRING);
			return;
		}
		if (args.length == 1) {
			filePath = args[0];
			File file = new File(filePath);
			if (!file.exists()) {
				System.out.println(FILE_NONEXISTENT);
				return;
			}
			commandlistToFile = new CommandlistToFile(filePath);
			int peerCount = commandlistToFile.getPeerCount();
			commandlistToFile.writerLoop(peerCount);
		}
		if (args.length > 1) {
			commandlistToFile = new CommandlistToFile();
			commandlistToFile.argsParser(args);
			int peerCount = commandlistToFile.getPeerCount();
			commandlistToFile.writerLoop(peerCount);
		}
	}
}