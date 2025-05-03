import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		String filePath = null;
		CommandlistFactory commandlistFactory;
		if (args.length == 0)
			System.out.println("Usage: " + "<jar name> <Info sheet path>" + "or <peer count> <file name> <file size> <scenario index>"); //todo
		if (args.length == 1) {
			filePath = args[0];
			commandlistFactory = new CommandlistFactory(filePath);
			int peerCount = commandlistFactory.getPeerCount();
			commandlistFactory.writerLoop(peerCount);
		}
		else if (args.length > 1) {
			commandlistFactory = new CommandlistFactory();
			commandlistFactory.argsParser(args);
			int peerCount = commandlistFactory.getPeerCount();
			commandlistFactory.writerLoop(peerCount);
		}
	}
}
