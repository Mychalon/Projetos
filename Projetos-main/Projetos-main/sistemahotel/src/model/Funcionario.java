package model;

import java.util.Date;

public class Funcionario extends Pessoa {
    

    // Construtor básico
    public Funcionario() {
        super();
        this.tipo = "funcionario"; // Define o tipo automaticamente
    }

    // Construtor completo
    public Funcionario(int id, String nome, String sobrenome, String cpf,
                      String telefone, String email, String senha, 
                      String endereco, Date dataNascimento) {
        super(id, nome, sobrenome, cpf, telefone, email, "funcionario", senha, endereco, dataNascimento);
    }

    // Métodos específicos (se necessário)
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
}