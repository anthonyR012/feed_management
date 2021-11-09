package com.anthony.deltec.gestor.ui.home;

public class OrderDataImplement {
    private String namePerson;
    private String img;
    private String total;
    private String tipo;
    private String state;
    private String description;

    public OrderDataImplement(String namePerson, String img, String total, String tipo, String state, String description) {
        this.namePerson = namePerson;
        this.img = img;
        this.total = total;
        this.tipo = tipo;
        this.state = state;
        this.description = description;
    }

    public String getNamePerson() {
        return namePerson;
    }

    public String getImg() {
        return img;
    }

    public String getTotal() {
        return total;
    }

    public String getTipo() {
        return tipo;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }
}
