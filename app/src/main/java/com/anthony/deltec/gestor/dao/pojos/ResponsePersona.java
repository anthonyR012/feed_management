package com.anthony.deltec.gestor.dao.pojos;

public class ResponsePersona {


        private String fullName;
        private String identify;
        private String email;
        private String phone;
        private String avatar;

    public ResponsePersona(String fullName, String identify, String email, String phone, String avatar) {
        this.fullName = fullName;
        this.identify = identify;
        this.email = email;
        this.phone = phone;
        this.avatar = avatar;
    }

    public ResponsePersona() {
    }

    public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getIdentify() {
            return identify;
        }

        public void setIdentify(String identify) {
            this.identify = identify;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }


}
