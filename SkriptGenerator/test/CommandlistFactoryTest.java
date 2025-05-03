import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class CommandlistFactoryTest {

	String path;
	public static void main(String[] args) {
		try {
			CommandlistFactory commandlistFactory = new CommandlistFactory("SkriptGenerator/test/infoblah.txt");
			for (int i = 1; i <= commandlistFactory.getPeerCount(); i++) {
				File file = new File("runScenario" + i + ".txt");
				System.out.println(file.getAbsolutePath());
				commandlistFactory.writeToFile(file, new FileOutputStream(file.getName()), commandlistFactory.getInfoSheetParser().generateScenarioScript(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Command list factory test completed.");
	}
}
