package com.estoque.raphajulia.app.model;

public class Vendas {
    public String nomevend;
    private int idvenda;
    public int quantidadevend;
    public String nomeproduto;


    public Vendas(String nomevend, int quantidadevend,String nomeproduto){
        this.nomevend = nomevend;
        this.quantidadevend = quantidadevend;
        this.nomeproduto = nomeproduto;
    }
}
