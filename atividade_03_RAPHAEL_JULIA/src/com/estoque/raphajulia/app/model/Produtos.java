package com.estoque.raphajulia.app.model;

public class Produtos {
    public int idprod;

    public String nomeprod;
    public String tipoprod;
    public int preçoprod;
    public int quantidade;
    public String fabricante;

 public Produtos(String nomeprod,String tipoprod,int preçoprod,int quantidade,String fabricante){
    this.nomeprod = nomeprod;
    this.tipoprod = tipoprod;
    this.preçoprod = preçoprod;
    this. quantidade = quantidade;
    this.fabricante = fabricante;
}

}
