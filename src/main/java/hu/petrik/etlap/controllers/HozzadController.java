package hu.petrik.etlap.controllers;

import hu.petrik.etlap.EtlapDb;
import hu.petrik.etlap.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class HozzadController extends Controller {
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
        String kategoria  = inputKategoria.getSelectionModel().getSelectedItem();
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
        if (kategoria == ""){
            alert("Kategória kiválasztása köztelező");
            return;
        }

        try {
            EtlapDb db = new EtlapDb();
            int pass = db.etelHozzaadasa(nev, leiras, ar, kategoria);
            if (pass == 1) {
                alert("Étel hozzáadása sikeres");
            } else {
                alert("Étel hozzáadása sikertelen");
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }
}
