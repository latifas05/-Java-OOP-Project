package org.example.petadoplati;

import javafx.beans.property.*;

public class Adopter {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty contact;

    public Adopter(int id, String name, String contact) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.contact = new SimpleStringProperty(contact);
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

    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }
}
