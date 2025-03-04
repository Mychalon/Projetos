/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author HOTEL FENIX
 */
public class Funcionario extends Pessoa{
    
    protected String nome;
    protected String senha;

 
    public Funcionario(String nome, String senha, int id) {
        super(id, nome);
        this.nome = nome;
        this.senha = senha;
    }

    public Funcionario( String senha, int id, String nome, char sexo, Date dataNascimento, String telefone, String email, String rg, String cnpj) {
        super(id, nome, sexo, dataNascimento, telefone, email, rg, cnpj);
        this.nome = nome;
        this.senha = senha;
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

    
    
    
}
