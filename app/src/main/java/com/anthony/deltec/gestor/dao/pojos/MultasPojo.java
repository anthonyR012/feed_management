package com.anthony.deltec.gestor.dao.pojos;


import android.widget.SpinnerAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultasPojo {

    @SerializedName("id_multa")
    @Expose
    private String idMulta;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("curso")
    @Expose
    private String curso;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("descuento")
    @Expose
    private String descuento;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("responsable")
    @Expose
    private String responsable;

    public String getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(String idMulta) {
        this.idMulta = idMulta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    public class ListaMultasPojo {

        @SerializedName("response")
        @Expose
        private List<MultasPojo> response = null;

        public List<MultasPojo> getResponse() {
            return response;
        }

        public void setResponse(List<MultasPojo> response) {
            this.response = response;
        }

    }
}