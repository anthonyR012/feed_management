package com.anthony.deltec.gestor.dao.pojos;


public class ResponseVehiculo {


    private String category;
    private String matricula;
    private String brand;
    private String year;
    private Integer id_persona;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public ResponseVehiculo() {
    }

    public ResponseVehiculo(String category, String matricula, String brand, String year, Integer id_persona) {
        this.category = category;
        this.matricula = matricula;
        this.brand = brand;
        this.year = year;
        this.id_persona = id_persona;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getIdPersona() {
        return id_persona;
    }

    public void setIdPersona(Integer id_persona) {
        this.id_persona = id_persona;
    }

}