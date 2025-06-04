package model;

import java.util.Date;

public class Pessoa {
    protected int id;
    protected String nome;
    protected String sobrenome;
    protected String cpf;
    protected String rg;
    protected String telefone;
    protected String email;
    protected String tipo;
    protected String senha;
    protected String endereco;
    protected Date dataNascimento;
    
    // Construtor vazio necessário para o DAO
    public Pessoa() {}
    
    // Construtor com parâmetros
    public Pessoa(int id, String nome, String sobrenome, String cpf, String telefone, 
                 String email, String tipo, String senha, String endereco, Date dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.tipo = tipo;
        this.senha = senha;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
    }
    
    // Getters e Setters para todos os campos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getRg() { return rg; }
    public void setRg(String rg) { this.rg = rg; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
}