package br.sesi.bank.bank_java_jdbc.service;

import br.sesi.bank.bank_java_jdbc.config.ConnectionFactory;
import br.sesi.bank.bank_java_jdbc.domain.cliente.Cliente;
import br.sesi.bank.bank_java_jdbc.domain.cliente.ContaDAO;
import br.sesi.bank.bank_java_jdbc.domain.conta.Conta;
import br.sesi.bank.bank_java_jdbc.domain.conta.DadosAberturaConta;
import br.sesi.bank.bank_java_jdbc.exceptions.RegraDeNegocioException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

public class ContaService {
    private ConnectionFactory connection;

    public ContaService() {
        this.connection = new ConnectionFactory();
    }

    public Set<Conta> listarContasAbertas() {
        Connection conn = connection.recuperarConexao();
        return new ContaDAO(conn).listar();
    }

    public BigDecimal consultarSaldo(Integer numeroDaConta) {
        var conta = buscarContaPorNumero(numeroDaConta);
        return conta.getSaldo();
    }

    public void abrir(DadosAberturaConta dadosDaConta) {
        Connection conn = connection.recuperarConexao();
        new ContaDAO(conn).salvar(dadosDaConta);
    }

    public void realizarDeposito(Integer numeroDaConta, BigDecimal valor) {
        var conta = buscarContaPorNumero(numeroDaConta);

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor de depósito deve ser superior a zero");
        }
        if (!conta.isAtivo()) {
            throw new RegraDeNegocioException("Conta não esta ativa");
        }
        BigDecimal novoValor = conta.getSaldo().add(valor);
        alterar(conta, valor);
    }
    public void realizarSaque(Integer numeroDaConta, BigDecimal valor){
        Conta conta = buscarContaPorNumero(numeroDaConta);

        if (conta.getSaldo().compareTo(valor) <= 0){
            throw new RegraDeNegocioException("Valor do saque deve ser superor a zero!");
        }
        if (valor.compareTo(conta.getSaldo()) > 0){
            throw new RegraDeNegocioException("Saldo insuficiente");
       }
        if (!conta.isAtivo()) {
            throw new RegraDeNegocioException("Conta não esta ativa");
        }
        BigDecimal novoValor = conta.getSaldo().subtract(valor);
        alterar(conta, novoValor);
    }

    public void  realizarTransferencia(Integer numeroDaContaOrigem, Integer numeroDaContaDestino, BigDecimal valor){

        this.realizarSaque(numeroDaContaOrigem, valor);
        this.realizarSaque(numeroDaContaDestino, valor);
    }

     private Conta buscarContaPorNumero(Integer numero) {
         Connection conn = connection.recuperarConexao();
         Conta conta = new ContaDAO(conn).listarPorNumero(numero);
         if (conta != null) {
             return conta;
         } else {
             throw new RegraDeNegocioException("Não existe conta cadastrada com esse número!");
         }
     }
    private void alterar (Conta conta, BigDecimal valor){
        Connection conn = connection.recuperarConexao();
        new ContaDAO(conn).alterarSaldo(conta.getNumero(), valor);
    }
    public void encerrar (Integer numeroDaConta){
        var conta = buscarContaPorNumero(numeroDaConta);
        if (conta.possuiSaldo()) {
            throw new RegraDeNegocioException("Conta ão pode sder encerrada pois ainda possui saldo!");
        }
        Connection conn = connection.recuperarConexao();

        new ContaDAO(conn).deletar(numeroDaConta);
    }
}