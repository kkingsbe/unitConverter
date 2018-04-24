package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import netscape.javascript.JSObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Controller {
    public TextField startingValueLabel;
    public TextField finalValueLabel;
    public ComboBox startingUnitBox;
    public ComboBox finalUnitBox;

    public void initialize() {
        populateComboBoxes();
    }

    private void populateComboBoxes() {
        try {
            String units[] = Files.readAllLines(Paths.get("src/sample/Units")).toString().replace(", ","").replace("]","").replace("[","").split(";");


            for(int row = 0; row < units.length; row++)
            {
                System.out.println(units[row]);
                units[row] = units[row].split(",")[0];
                startingUnitBox.getItems().addAll(units[row]);
                finalUnitBox.getItems().addAll(units[row]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] createUnitArray(String Unit) {
        try {
            String units[] = Files.readAllLines(Paths.get("src/sample/Units")).toString().replace(", ","").replace("]","").replace("[","").split(";");


            for(int row = 0; row < units.length; row++)
            {
                if (units[row] == Unit){
                    units[row] = units[row].split(",")[1];
                    String [] containedUnits = units[row].split("/");
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return containedUnits;
    }

    public void convertUnit(ActionEvent actionEvent) {

        double startingValue;
        double finalValue;
        String startingUnit;
        String finalUnit;

        if(!startingValueLabel.getText().equals("") && !startingUnitBox.getValue().toString().equals("") && !finalUnitBox.getValue().toString().equals("")) {
            startingValue = Integer.parseInt(startingValueLabel.getText());
            startingUnit = startingUnitBox.getValue().toString();
            finalUnit = startingUnitBox.getValue().toString();
            createUnitArray(startingUnit);
            System.out.println(createUnitArray(finalUnit)[0]);
        }
    }
}
