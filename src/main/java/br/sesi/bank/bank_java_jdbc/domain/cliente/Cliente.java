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

    public int  hashCode(){
        return 0;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return cpf.equals(cliente.cpf);
    }
    @Override
    public String  toString(){
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
