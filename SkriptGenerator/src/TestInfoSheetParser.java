import java.io.FileNotFoundException;

public class TestInfoSheetParser {
    public static void main(String[] args) throws FileNotFoundException {
            InfoSheetParser ifp = new InfoSheetParser("infoblah.txt");

        System.out.println(ifp.generateRunScenario());

        System.out.println(ifp.generateScenarioScript(3));


    }
}