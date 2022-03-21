package hu.petrik.etlap.controllers;

import hu.petrik.etlap.Controller;
import hu.petrik.etlap.Etlap;
import hu.petrik.etlap.EtlapApp;
import hu.petrik.etlap.EtlapDb;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class MainController extends Controller {
    @FXML
    private Spinner<Integer> szazalekSpinner;
    @FXML
    private Spinner<Integer> forintSpinner;
    @FXML
    private TableView<Etlap> etlapTable;
    @FXML
    private TableColumn<Etlap, String> nev;
    @FXML
    private TableColumn<Etlap, String> kategoria;
    @FXML
    private TableColumn<Etlap, Integer> ar;
    @FXML
    private TableColumn<Etlap, String> leiras;

    private EtlapDb db;

    public void initialize(){
        nev.setCellValueFactory(new PropertyValueFactory<>("nev"));
        kategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        ar.setCellValueFactory(new PropertyValueFactory<>("ar"));
        leiras.setCellValueFactory(new PropertyValueFactory<>("leiras"));

        try {
            db = new EtlapDb();
            etlapListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onTorlesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = etlapTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("A törléshez előbb válasszon ki egy elemet a táblázatból");
            return;
        }
        Etlap torlendoEtel = etlapTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos hogy törölni szeretné az alábbi ételt: "+torlendoEtel.getNev())){
            return;
        }
        try {
            db.etelTorlese(torlendoEtel.getId());
            alert("Sikeres törlés");
            etlapListaFeltolt();
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    @FXML
    public void onHozzadasButtonClick(ActionEvent actionEvent) {
        try {
            Controller hozzadas = ujAblak("hozzaad-view.fxml", "Étel hozzáadása",
                    320, 400);
            hozzadas.getStage().setOnCloseRequest(event -> etlapListaFeltolt());
            hozzadas.getStage().show();
        } catch (Exception e) {
            hibaKiir(e);
        }
    }

    private void etlapListaFeltolt(){
        try {
            List<Etlap> etlapList = db.getEtlap();
            etlapTable.getItems().clear();
            for(Etlap etel: etlapList){
                etlapTable.getItems().add(etel);
            }
        } catch (SQLException e) {
            hibaKiir(e);
        }
    }

    public void forintOnClick(ActionEvent actionEvent) {
        double emelendoOsszeg = forintSpinner.getValue();

        if (etlapTable.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Biztos szeretné növelni az összes étel árát?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType resultType = result.orElse(ButtonType.CANCEL);

            if (resultType == ButtonType.OK) {
                try {
                    if (new EtlapDb().novelesOsszes(emelendoOsszeg) == -1) {
                        alert("Sikertelen növelés");
                    } else {
                        alert("Sikeres növelés");
                    }
                } catch (SQLException e) {
                    hibaKiir(e);
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String nev = etlapTable.getSelectionModel().getSelectedItem().getNev();
            alert.setHeaderText("Biztos szeretné növelni a "+nev+" árát?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType resultType = result.orElse(ButtonType.CANCEL);

            if (resultType == ButtonType.OK) {
                int id = etlapTable.getSelectionModel().getSelectedItem().getId();

                try {
                    if (new EtlapDb().noveles(emelendoOsszeg, id) == -1) {
                        alert("Sikertelen növelés");
                    } else {
                        alert("Sikeres növelés");
                    }
                } catch (SQLException e) {
                    hibaKiir(e);
                    e.printStackTrace();
                }
            }
        }

        etlapListaFeltolt();
    }

    public void szazalekOnClick(ActionEvent actionEvent) {
        double emmelendoOsszeg = 1 + szazalekSpinner.getValue() / 100d;

        if (etlapTable.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Biztos szeretné növelni az összes étel árát?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType resultType = result.orElse(ButtonType.CANCEL);

            if (resultType == ButtonType.OK) {
                try {
                    if (new EtlapDb().novelesOsszes(emmelendoOsszeg) == -1) {
                        alert("Sikertelen növelés");
                    } else {
                        alert("Sikeres növelés");
                    }
                } catch (SQLException e) {
                    hibaKiir(e);
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            String name = etlapTable.getSelectionModel().getSelectedItem().getNev();
            alert.setHeaderText("Biztos szeretné növelni a "+name+" árát?");
            Optional<ButtonType> result = alert.showAndWait();
            ButtonType resultType = result.orElse(ButtonType.CANCEL);

            if (resultType == ButtonType.OK) {
                int id = etlapTable.getSelectionModel().getSelectedItem().getId();

                try {
                    if (new EtlapDb().noveles(emmelendoOsszeg, id) == -1) {
                        alert("Sikertelen növelés");
                    } else {
                        alert("Sikeres növelés");
                    }
                } catch (SQLException e) {
                    hibaKiir(e);
                    e.printStackTrace();
                }
            }
        }

        etlapListaFeltolt();
    }
}