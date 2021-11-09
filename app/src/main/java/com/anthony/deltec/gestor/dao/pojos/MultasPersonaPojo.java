package com.anthony.deltec.gestor.dao.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultasPersonaPojo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("identificacion")
    @Expose
    private String identificacion;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("multas")
    @Expose
    private List<Multa> multas = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Multa> getMultas() {
        return multas;
    }

    public void setMultas(List<Multa> multas) {
        this.multas = multas;
    }

    public class Multa {

        @SerializedName("idMulta")
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
        @SerializedName("categoria_vehiculo")
        @Expose
        private String categoriaVehiculo;
        @SerializedName("matricula_vehiculo")
        @Expose
        private String matriculaVehiculo;
        @SerializedName("marca_vehiculo")
        @Expose
        private String marcaVehiculo;

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

        public String getCategoriaVehiculo() {
            return categoriaVehiculo;
        }

        public void setCategoriaVehiculo(String categoriaVehiculo) {
            this.categoriaVehiculo = categoriaVehiculo;
        }

        public String getMatriculaVehiculo() {
            return matriculaVehiculo;
        }

        public void setMatriculaVehiculo(String matriculaVehiculo) {
            this.matriculaVehiculo = matriculaVehiculo;
        }

        public String getMarcaVehiculo() {
            return marcaVehiculo;
        }

        public void setMarcaVehiculo(String marcaVehiculo) {
            this.marcaVehiculo = marcaVehiculo;
        }

    }
    public class MultasPersonasPojoList {

        @SerializedName("response")
        @Expose
        private List<MultasPersonaPojo> response = null;

        public List<MultasPersonaPojo> getResponse() {
            return response;
        }

        public void setResponse(List<MultasPersonaPojo> response) {
            this.response = response;
        }

    }

}
