package org.example.petadoplati;

import javafx.beans.property.*;
import java.time.LocalDate;

public class AdoptionRecord {
    private final IntegerProperty adoptionId;
    private final IntegerProperty adopterId;
    private final StringProperty adopterName;
    private final StringProperty adopterContact;
    private final IntegerProperty petId;
    private final StringProperty petName; // Pet name
    private final StringProperty petType; // Pet type
    private final ObjectProperty<LocalDate> adoptionDate;

    // Constructor for initializing all properties
    public AdoptionRecord(int adoptionId, int adopterId, int petId, String adopterName,
                          String adopterContact, String petName, String petType,
                          LocalDate adoptionDate) {
        this.adoptionId = new SimpleIntegerProperty(adoptionId);
        this.adopterId = new SimpleIntegerProperty(adopterId);
        this.petId = new SimpleIntegerProperty(petId);
        this.adopterName = new SimpleStringProperty(adopterName);
        this.adopterContact = new SimpleStringProperty(adopterContact);
        this.petName = new SimpleStringProperty(petName);
        this.petType = new SimpleStringProperty(petType);
        this.adoptionDate = new SimpleObjectProperty<>(adoptionDate);
    }

    public int getAdoptionId() {
        return adoptionId.get();
    }

    public void setAdoptionId(int adoptionId) {
        this.adoptionId.set(adoptionId);
    }

    public IntegerProperty adoptionIdProperty() {
        return adoptionId;
    }

    public int getAdopterId() {
        return adopterId.get();
    }

    public void setAdopterId(int adopterId) {
        this.adopterId.set(adopterId);
    }

    public IntegerProperty adopterIdProperty() {
        return adopterId;
    }

    public String getAdopterName() {
        return adopterName.get();
    }

    public void setAdopterName(String adopterName) {
        this.adopterName.set(adopterName);
    }

    public StringProperty adopterNameProperty() {
        return adopterName;
    }

    public String getAdopterContact() {
        return adopterContact.get();
    }

    public void setAdopterContact(String adopterContact) {
        this.adopterContact.set(adopterContact);
    }

    public StringProperty adopterContactProperty() {
        return adopterContact;
    }

    public int getPetId() {
        return petId.get();
    }

    public void setPetId(int petId) {
        this.petId.set(petId);
    }

    public IntegerProperty petIdProperty() {
        return petId;
    }

    public String getPetName() {
        return petName.get();
    }

    public void setPetName(String petName) {
        this.petName.set(petName);
    }

    public StringProperty petNameProperty() {
        return petName;
    }

    public String getPetType() {
        return petType.get();
    }

    public void setPetType(String petType) {
        this.petType.set(petType);
    }

    public StringProperty petTypeProperty() {
        return petType;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate.get();
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate.set(adoptionDate);
    }

    public ObjectProperty<LocalDate> adoptionDateProperty() {
        return adoptionDate;
    }
}