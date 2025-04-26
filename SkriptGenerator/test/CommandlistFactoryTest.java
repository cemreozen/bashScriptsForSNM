import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class CommandlistFactoryTest {
	public static void main(String[] args) {
		try {
			InfoSheetParser infoSheetParser = new InfoSheetParser("infoblah.txt");
			CommandlistFactory commandlistFactory = new CommandlistFactory(infoSheetParser);
			File file = new File("runScenario.txt");
			FileWriter fileWriter = new FileWriter(file);
			commandlistFactory.writeToFile(file, new FileOutputStream(file.getName()), "Test command");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Command list factory test completed.");
	}
}
