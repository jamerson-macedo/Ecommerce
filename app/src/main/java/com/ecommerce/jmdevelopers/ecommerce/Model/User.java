package com.ecommerce.jmdevelopers.ecommerce.Model;

public class User {
    String nome;
    String senha;
    String telefone;

    public User() {


    }

    public User(String nome, String senha, String telefone) {
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
