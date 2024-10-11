package br.sesi.bank.bank_java_jdbc.domain.cliente;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;

    public Cliente(DadosCadastroCliente dados){
        this.cpf = dados.nome;
        this.email = dados.email;
        this.nome = dados.nome;

    }
    public String getNome(){
        return nome;
    }
    public String getCpf(){
        return cpf;
    }
    public String getEmail(){
        return email;
    }
    public boolean equals(Object o){
        return true;
    }
    public int  hashCode(){
        return 0;
    }
}