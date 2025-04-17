import java.io.FileNotFoundException;

public class TestInfoSheetParser {
    public static void main(String[] args) throws FileNotFoundException {
            InfosheetParser ifp = new InfosheetParser("infoblah.txt");

        System.out.println(ifp.generateRunScenario());

        System.out.println(ifp.generateScenarioScript(3));


    }
}