package org.example.petadoplati;

import javafx.beans.property.*;

public class Pet {
    private IntegerProperty adoptionId;
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty breed;
    private final StringProperty type;
    private final IntegerProperty age;
    private final StringProperty description;

    public Pet(int id, String name, String breed, String type, int age, String description) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.breed = new SimpleStringProperty(breed);
        this.type = new SimpleStringProperty(type);
        this.age = new SimpleIntegerProperty(age);
        this.description = new SimpleStringProperty(description);
        this.adoptionId = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getBreed() {
        return breed.get();
    }

    public StringProperty breedProperty() {
        return breed;
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public int getAdoptionId() { return adoptionId.get();}

    public IntegerProperty adoptionIdProperty() { return adoptionId;}
}
