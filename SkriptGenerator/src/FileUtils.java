import java.io.*;

public class FileUtils {

	/**
	 * Writes the given string to a file.
	 * @param fos the file output stream
	 * @param goal the string to write
	 * @throws IOException if an I/O error occurs
	 */
	public static void writeToFile(FileOutputStream fos, String goal) throws IOException {
		fos.write(goal.getBytes());
		fos.close();
	}

	public static BufferedReader getBufferedReader(String filepath) throws FileNotFoundException {
		return new BufferedReader(new FileReader(filepath));
	}

	public static void createSpecifiedFile(long filesize, String filename) throws IOException {
		File file = new File(filename);
		try {
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
				FileOutputStream fos = new FileOutputStream(file);
				byte[] data = new byte[(int)filesize];
				fos.write(data);
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}