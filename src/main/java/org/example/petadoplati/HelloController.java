package org.example.petadoplati;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.stage.Stage;
import org.example.petadoplati.AdoptionRecord;
import org.example.petadoplati.AdoptionRecordsDAO;


public class HelloController {
    @FXML
    private TableView<Pet> petsTableView;

    @FXML
    private TableColumn<Pet, Integer> idColumn;
    @FXML
    private TableColumn<Pet, String> nameColumn;
    @FXML
    private TableColumn<Pet, String> breedColumn;
    @FXML
    private TableColumn<Pet, String> typeColumn;
    @FXML
    private TableColumn<Pet, Integer> ageColumn;
    @FXML
    private TableColumn<Pet, String> descriptionColumn;

    @FXML
    private ComboBox<String> typeFilterComboBox;

    @FXML
    private TextField adopterNameField;
    @FXML
    private TextField adopterContactField;

    @FXML
    private TextField addAdopterNameField;
    @FXML
    private TextField addAdopterContactField;

    @FXML
    private Button viewAdoptersButton;

    @FXML
    private ComboBox<Integer> adopterIdComboBox;

    @FXML
    private TableView<AdoptionRecord> adoptionRecordsTableView;

    @FXML
    private TableColumn<AdoptionRecord, Integer> adoptionIdColumn;
    @FXML
    private TableColumn<AdoptionRecord, String> adopterNameColumn;
    @FXML
    private TableColumn<AdoptionRecord, String> petNameColumn;
    @FXML
    private TableColumn<AdoptionRecord, String> petTypeColumn;
    @FXML
    private TableColumn<AdoptionRecord, LocalDate> adoptionDateColumn;
    @FXML
    private TableColumn<AdoptionRecord, String> adopterContactColumn;


    private final PetDAO petDAO = new PetDAO();
    private final AdoptionRecordsDAO adoptionRecordDAO = new AdoptionRecordsDAO();

    @FXML
    public void initialize() {
        loadPets();
        setupTypeFilterComboBox();
        setupTableView();
        setupAdoptionRecordsTableView();
        loadAdoptionRecords();
        loadAdopterIds();
    }

    private void setupTableView() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        breedColumn.setCellValueFactory(cellData -> cellData.getValue().breedProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        ageColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    private void setupAdoptionRecordsTableView() {
        adoptionIdColumn.setCellValueFactory(cellData -> cellData.getValue().adoptionIdProperty().asObject());
        adopterNameColumn.setCellValueFactory(cellData -> cellData.getValue().adopterNameProperty());
        petNameColumn.setCellValueFactory(cellData -> cellData.getValue().petNameProperty());
        petTypeColumn.setCellValueFactory(cellData -> cellData.getValue().petTypeProperty());
        adoptionDateColumn.setCellValueFactory(cellData -> cellData.getValue().adoptionDateProperty());
        adopterContactColumn.setCellValueFactory(cellData -> cellData.getValue().adopterContactProperty());
    }


    private void setupTypeFilterComboBox() {
        typeFilterComboBox.getItems().addAll("All", "Cat", "Dog", "Bird", "Other");
        typeFilterComboBox.setValue("All");
        typeFilterComboBox.setOnAction(event -> loadPets());
    }

    private void loadPets() {
        String selectedType = typeFilterComboBox.getValue();
        petsTableView.getItems().clear();
        for (Pet pet : petDAO.getAllPets(selectedType)) {
            petsTableView.getItems().add(pet);
        }
    }

    private void loadAdopterIds() {
        List<Integer> adopterIds = AdoptersDAO.getAllAdopterIds();
        adopterIdComboBox.getItems().addAll(adopterIds);
    }

    @FXML
    public void handleAdoption() {
        Pet selectedPet = petsTableView.getSelectionModel().getSelectedItem();
        if (selectedPet != null) {
            String adopterName = adopterNameField.getText().trim();
            String adopterContact = adopterContactField.getText().trim();


            if (!adopterName.isEmpty() && !adopterContact.isEmpty()) {
                try {
                    int adopterId = getAdopterId();
                    int petId = selectedPet.getId();
                    LocalDate adoptionDate = LocalDate.now();

                    AdoptionRecord newAdoptionRecord = new AdoptionRecord(
                            0,
                            adopterId,
                            petId,
                            adopterName,
                            adopterContact,
                            selectedPet.getName(),
                            selectedPet.getType(),
                            adoptionDate
                    );

                    adoptionRecordDAO.addAdoptionRecord(newAdoptionRecord);

                    petsTableView.getItems().remove(selectedPet);
                    petDAO.removePet(selectedPet);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Adoption Successful");
                    alert.setHeaderText(null);
                    alert.setContentText("Adoption has been successfully recorded, and the pet has been removed from the list. Thank you for adopting!");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("An error occurred while recording the adoption. Please try again.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Incomplete Information");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all adopter details.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Pet Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a pet to adopt.");
            alert.showAndWait();
        }
    }

    private void loadAdoptionRecords() {
        adoptionRecordsTableView.getItems().clear();
        List<AdoptionRecord> records = AdoptionRecordsDAO.getAllRecords();
        for (AdoptionRecord record : records) {
            adoptionRecordsTableView.getItems().add(record);
        }
    }

    private String getAdopterName(int adopterId) {
        return "1";
    }

    private int getAdopterId() {
        Integer selectedId = adopterIdComboBox.getValue();
        if (selectedId != null) {
            return selectedId;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Adopter Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an adopter from the list.");
            alert.showAndWait();
            return -1;
        }
    }


    @FXML
    public void showAddPetDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Found Pet");
        dialog.setHeaderText("Enter pet details:");

        VBox dialogVBox = new VBox(10);
        TextField nameField = new TextField();
        TextField breedField = new TextField();
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Cat", "Dog", "Bird", "Other");
        TextField ageField = new TextField();
        TextArea descriptionArea = new TextArea();

        dialogVBox.getChildren().addAll(
                new Label("Name:"), nameField,
                new Label("Breed:"), breedField,
                new Label("Type:"), typeComboBox,
                new Label("Age:"), ageField,
                new Label("Description:"), descriptionArea
        );

        dialog.getDialogPane().setContent(dialogVBox);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == okButton) {
            String name = nameField.getText().trim();
            String breed = breedField.getText().trim();
            String type = typeComboBox.getValue();
            int age = Integer.parseInt(ageField.getText().trim());
            String description = descriptionArea.getText().trim();

            if (!name.isEmpty() && !breed.isEmpty() && type != null && age > 0) {
                Pet newPet = new Pet(0, name, breed, type, age, description);
                petDAO.addPet(newPet);
                loadPets();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Pet added successfully!");
                alert.showAndWait();
            }
        }
    }


    @FXML
    private void handleViewAdopters() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdoptersView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) viewAdoptersButton).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Adopters List");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void addAdopter() {
        String adopterName = addAdopterNameField.getText().trim();
        String adopterContact = addAdopterContactField.getText().trim();

        if (!adopterName.isEmpty() && !adopterContact.isEmpty()) {
            Adopter newAdopter = new Adopter(0, adopterName, adopterContact);

            AdoptersDAO adopterDAO = new AdoptersDAO();

            int adopterId = adopterDAO.addAdopter(newAdopter);

            if (adopterId != -1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Adopter Added");
                alert.setHeaderText(null);
                alert.setContentText("The adopter has been successfully added.");
                alert.showAndWait();

                addAdopterNameField.clear();
                addAdopterContactField.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("There was an issue adding the adopter.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in the adopter's details.");
            alert.showAndWait();
        }
    }

    @FXML
    public void deleteButton(ActionEvent actionEvent) {
        Pet selectedPet = petsTableView.getSelectionModel().getSelectedItem();
        if (selectedPet != null) {
            petDAO.removePet(selectedPet);
            petsTableView.getItems().remove(selectedPet);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pet Removed");
            alert.setHeaderText(null);
            alert.setContentText("The pet has been removed from the system.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Pet Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a pet to delete.");
            alert.showAndWait();
        }
    }

}
