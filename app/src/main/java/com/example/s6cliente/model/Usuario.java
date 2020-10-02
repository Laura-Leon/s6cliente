package com.example.s6cliente.model;

public class Usuario {


        private String id;
        private String name;
        private String pass;

        public Usuario(String id, String name, String pass) {
            super();
            this.id = id;
            this.name = name;
            this.pass = pass;

        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPass() {
            return pass;
        }
        public void setPass(String pass) {
            this.pass = pass;
        }




    }


