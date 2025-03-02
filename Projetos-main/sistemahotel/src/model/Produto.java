/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author HOTEL FENIX
 */
    // Classe Produto
    public class Produto {
        private String Nome;
        private String Cod;
        private String Quant;
        private String Preço;

    public Produto(String Nome, String Cod, String Quant, String Preço) {
        this.Nome = Nome;
        this.Cod = Cod;
        this.Quant = Quant;
        this.Preço = Preço;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getQuant() {
        return Quant;
    }

    public void setQuant(String Quant) {
        this.Quant = Quant;
    }

    public String getPreço() {
        return Preço;
    }

    public void setPreço(String Preço) {
        this.Preço = Preço;
    }
          
    }
