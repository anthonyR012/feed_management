package com.anthony.deltec.gestor.dao.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehiculoPojo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("categoria")
    @Expose
    private String categoria;
    @SerializedName("matricula")
    @Expose
    private String matricula;
    @SerializedName("marca")
    @Expose
    private String marca;
    @SerializedName("anio")
    @Expose
    private String anio;
    @SerializedName("responsable")
    @Expose
    private String responsable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    public class ListVehiculoPojo {

        @SerializedName("response")
        @Expose
        private List<VehiculoPojo> response = null;

        public List<VehiculoPojo> getResponse() {
            return response;
        }

        public void setResponse(List<VehiculoPojo> response) {
            this.response = response;
        }

    }
}