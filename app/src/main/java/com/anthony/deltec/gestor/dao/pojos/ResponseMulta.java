package com.anthony.deltec.gestor.dao.pojos;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


 public class ResponseMulta {


        private String state;
        private Integer type;
        private String curso;
        private String description;
        private Integer discount;
        private Integer total;
        private Integer id_persona;
        private Integer id_multa;

     public Integer getId_multa() {
         return id_multa;
     }

     public void setId_multa(Integer id_multa) {
         this.id_multa = id_multa;
     }

     public String getState() {
            return state;
        }

     public ResponseMulta() {
     }

     public ResponseMulta(String state, Integer type, String curso, String description, Integer discount, Integer total, Integer id_persona) {
         this.state = state;
         this.type = type;
         this.curso = curso;
         this.description = description;
         this.discount = discount;
         this.total = total;
         this.id_persona = id_persona;
     }

     public void setState(String state) {
            this.state = state;
        }

        public int getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getCurso() {
            return curso;
        }

        public void setCurso(String curso) {
            this.curso = curso;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public int getIdPersona() {
            return id_persona;
        }

        public void setIdPersona(Integer idPersona) {
            this.id_persona = idPersona;
        }

    }