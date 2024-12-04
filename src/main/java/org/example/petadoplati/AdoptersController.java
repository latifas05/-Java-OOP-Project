package org.example.petadoplati;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class AdoptersController {

    @FXML
    private TableView<Adopter> adoptersTableView;

    @FXML
    private TableColumn<Adopter, Integer> idColumn;
    @FXML
    private TableColumn<Adopter, String> nameColumn;
    @FXML
    private TableColumn<Adopter, String> contactColumn;

    @FXML
    private Button goToHomeButton;

    private ObservableList<Adopter> adoptersList;

    private AdoptersDAO adoptersDAO;

    @FXML
    public void initialize() {
        adoptersList = FXCollections.observableArrayList();
        adoptersDAO = new AdoptersDAO();
        loadAdopters();

        adoptersTableView.setItems(adoptersList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        contactColumn.setCellValueFactory(cellData -> cellData.getValue().contactProperty());
    }

    private void loadAdopters() {
        List<Adopter> adopters = adoptersDAO.getAllAdopters();
        adoptersList.addAll(adopters);
    }

    @FXML
    private void handleGoToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) goToHomeButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}