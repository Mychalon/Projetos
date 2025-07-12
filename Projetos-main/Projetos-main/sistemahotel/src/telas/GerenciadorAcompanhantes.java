/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package telas;

/**
 *
 * @author HOTEL FENIX
 */
public class GerenciadorAcompanhantes {
    
    public static boolean verificarLimite(Quarto quarto, int qtdAtual, int qtdNovos) {
        int limite = calcularLimite(quarto);
        return (qtdAtual + qtdNovos) <= limite;
    }
    
    public static int calcularLimite(Quarto quarto) {
        switch(quarto.getTipo().toLowerCase()) {
            case "duplo": return 1;
            case "triplo": return 2;
            case "quadruplo": return 3;
            default: return 1;
        }
    }
    
    public static double calcularAdicional(int qtdExcedente) {
        return qtdExcedente * 50.0; // R$50 por acompanhante excedente
    }
    
    
    
    
    
}
