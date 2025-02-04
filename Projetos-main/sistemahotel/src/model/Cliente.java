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
public class Cliente extends Pessoa{
    

   private String nome;
   private String endereço;

    public Cliente(int id, String nome, char sexo, Date dataNascimento, String telefone, String endereço, String email, String rg, String cnpj) {
        super(id, nome, sexo, dataNascimento, telefone, email, rg, cnpj);
        this.nome = nome;
        this.endereço = endereço;
    }
    public Cliente(int id, String nome, String endereço) {
        super(id, nome);
        this.nome = nome;
        this.endereço = endereço;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEndereço() {
        return endereço;
    }
    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }
}
