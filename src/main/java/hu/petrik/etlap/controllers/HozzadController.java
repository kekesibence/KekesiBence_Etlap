package hu.petrik.etlap.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class HozzadController {
    @FXML
    private TextField inputNev;
    @FXML
    private TextField inputLeiras;
    @FXML
    private ChoiceBox<String> inputKategoria;
    @FXML
    private Spinner<Integer> inputAr;


    @FXML
    public void onHozzadButtonClick(ActionEvent actionEvent) {
        String nev = inputNev.getText().trim();
        String leiras = inputLeiras.getText().trim();
        int ar = 0;
        String selectedKategoria  = inputKategoria.getSelectionModel().getSelectedItem();
        if (nev.isEmpty()){
            alert("Név megadása kötelező");
            return;
        }
        if (leiras.isEmpty()){
            alert("Leírás megadása kötelező");
            return;
        }
        try {
            ar = inputAr.getValue();
        } catch (NullPointerException ex){
            alert("Az ár megadása kötelező");
            return;
        } catch (Exception ex){
            System.out.println(ex);
            alert("Az árnak 1-nél nagyobbnak kell lennie");
            return;
        }
        if (ar < 1 ) {
            alert("Az árnak 1-nél nagyobbnak kell lennie");
            return;
        }
        if (selectedKategoria == ""){
            alert("Kategória kiválasztása köztelező");
            return;
        }
        System.out.println(ar);
    }

    private void alert(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(uzenet);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }
}
