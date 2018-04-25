package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public TextField startingValueLabel;
    public Label finalValueLabel;
    public ComboBox startingUnitBox;
    public ComboBox finalUnitBox;
    public Button applyButton;
    public ComboBox intermediateUnitBox;

    public void initialize() {
        populateComboBoxes();
    }

    private void populateComboBoxes() {
        try {
            String units[] = Files.readAllLines(Paths.get("src/sample/Units")).toString().replace(", ","").replace("]","").replace("[","").split(";");
            List startingUnitBoxList = new ArrayList();
            List finalUnitBoxList = new ArrayList();

            for(int row = 0; row < units.length; row++)
            {
                System.out.println(units[row]);
                units[row] = units[row].split(",")[0];
                startingUnitBoxList.add(units[row]);
                finalUnitBoxList.add(units[row]);

            }

            java.util.Collections.sort(startingUnitBoxList);
            java.util.Collections.sort(finalUnitBoxList);
            startingUnitBox.getItems().addAll(startingUnitBoxList);
            finalUnitBox.getItems().addAll(finalUnitBoxList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertUnit(ActionEvent actionEvent) {

        double startingValue;
        double finalValue;
        String startingUnit;
        String finalUnit;

        try {
            if (!startingValueLabel.getText().equals("") && !startingUnitBox.getValue().toString().equals("") && !finalUnitBox.getValue().toString().equals("")) {
                startingValue = Double.parseDouble(startingValueLabel.getText());
                startingUnit = startingUnitBox.getValue().toString();
                finalUnit = finalUnitBox.getValue().toString();
                List startingUnitArray = createUnitArray(startingUnit);
                List finalUnitArray = createUnitArray(finalUnit);
                List commonUnits = findCommonUnits(startingUnitArray,finalUnitArray);

                intermediateUnitBox.getItems().addAll(commonUnits);

                System.out.println("Units in common: " + commonUnits);
                System.out.println(startingUnitArray);
                finalValue = convertToFinalUnit(startingValue,startingUnitArray,finalUnitArray,intermediateUnitBox.getValue().toString());
                finalValueLabel.setText(Double.toString(finalValue));
            }
        } catch (java.lang.NullPointerException e){

        }
    }

    private double convertToFinalUnit(double startingValue, List startingUnitArray, List finalUnitArray, String commonUnit) {
        double conversionValue1 = 0;
        double conversionValue2 = 0;
        for (int x = 0; x <= startingUnitArray.size(); x++){
            try {
                if (startingUnitArray.get(x).toString().split(" ")[1].equals(commonUnit)) {
                    conversionValue1 = Double.parseDouble(startingUnitArray.get(x).toString().split(" ")[0]);
                }
            } catch (java.lang.IndexOutOfBoundsException e) {}
        }
        for (int x = 0; x <= finalUnitArray.size(); x++){
            try {
                if (finalUnitArray.get(x).toString().split(" ")[1].equals(commonUnit)) {
                    conversionValue2 = Double.parseDouble(finalUnitArray.get(x).toString().split(" ")[0]);
                }
            } catch (java.lang.IndexOutOfBoundsException e) {}
        }
        double finalValue = startingValue * conversionValue1 / conversionValue2;
        return finalValue;
    }

    private static List<String> createUnitArray(String Unit) {
        List<String> containedUnits =  new ArrayList<>();
        try {
            String units[] = Files.readAllLines(Paths.get("src/sample/Units")).toString().replace(", ","").replace("]","").replace("[","").split(";");

            for(int row = 0; row < units.length; row++)
            {
                if (units[row].split(",")[0].equals(Unit)){
                    units[row] = units[row].split(",")[1];

                    for (int x = 0; x <= units[row].split("/").length; x++){
                        System.out.println(x);
                        try {
                            containedUnits.add(units[row].split("/")[x]);
                        } catch (java.lang.ArrayIndexOutOfBoundsException e){

                        }
                    }
                    System.out.println(units[row]);
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return containedUnits;
    }

    private List findCommonUnits(List startingUnitArray, List finalUnitArray) {
        List commonUnits = new ArrayList();
        for (int x = 0; x < startingUnitArray.size() && x < finalUnitArray.size(); x++){
            if (startingUnitArray.get(x).toString().split(" ")[1].equals(finalUnitArray.get(x).toString().split(" ")[1])){
                commonUnits.add(startingUnitArray.get(x).toString().split(" ")[1]);
            }
        }
        return commonUnits;
    }
}
