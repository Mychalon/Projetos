/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;

/**
 *
 * @author HOTEL FENIX
 */
    // Classe Produto
    public class Produto {
    private final String codigo;
    private final String nomeProduto;
    private final int quantidade;
    private final double preco;

    // Construtor
    public Produto(String codigo, String nomeProduto, int quantidade, double preco) {
        this.codigo = codigo;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }
}


