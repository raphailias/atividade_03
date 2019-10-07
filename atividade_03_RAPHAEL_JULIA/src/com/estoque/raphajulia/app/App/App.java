package com.estoque.raphajulia.app.App;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.estoque.raphajulia.app.model.Produtos;
import com.estoque.raphajulia.app.model.ProdutosDAO;
import com.estoque.raphajulia.app.model.Vendas;
import com.estoque.raphajulia.app.model.VendasDAO;
import com.estoque.raphajulia.app.model.Usuario;
import com.estoque.raphajulia.app.model.UsuarioDAO;


public class App {
    private Scanner scan;
    private List<Produtos> produtoslist;
    private List<Usuario> usuarioslist;
    private List<Vendas> vendaslist;
    int quantidadevenda;
    int vendasquant;


    public App() {
        scan = new Scanner(System.in);
        produtoslist = new ArrayList<>();
        usuarioslist = new ArrayList<>();
        vendaslist = new ArrayList<>();
    }

    public void mainLoop(){
        int op;
        while (true){
            menu();
            op = scan.nextInt();
            if(op == 0)
                break;
            else
                avaliarOp(op);
        }

    }
    private void menu() {
        System.out.println("1 - Exibir produtos tipo");
        System.out.println("2 - Exibir vendas");
        System.out.println("3 - Comprar");
        System.out.println("4 - cadastrar vendedor");
        System.out.println("5 - cadastrar produto");
        System.out.println("0 - Sair");
    }
    private void avaliarOp(int op) {
        Produtos produtos = new Produtos();
        Usuario usuario = new Usuario();
        Vendas vendas = new Vendas();
        switch (op) {
            //Buscar todos
            case 0:
                break;
            case 1:
                System.out.println("Informe um Tipo:");
                produtos.tipoprod = scan.next();
                produtoslist = ProdutosDAO.getInstance().readAll(produtos.tipoprod);
                exibirProdutos();
                break;
            case 2:
                System.out.println("vendas");
                vendas = VendasDAO.getInstance().readAll();
                exibirVendas();
                break;
            case 3:
                System.out.println("informe o nome do produto e a quantidade e nome do vendedor.");
                produtos.nomeprod = scan.next();
                produtos.quantidade = scan.next();
                vendas.nomevend = scan.next();
                quantidadevenda = ProdutosDAO.getInstance().read(produtos.nomeprod);
                if (produtos.quantidade <= quantidadevenda){
                    vendasquant = produtos.quantidade;
                    produtos.quantidade = produtos.quantidade - quantidadevenda;
                    ProdutosDAO.getInstance().update(produtos.nomeprod,produtos);
                    vendas.nomeproduto = produtos.nomeprod;
                    vendas.quantidadevend = vendasquant;
                    VendasDAO.getInstance().create(vendas);}
                    else {
                    System.out.println("venda não autorizada, não temos tudo isso");
                }


                break;

            case 4:
                System.out.println("informe nome,senha");
                usuario.name = scan.next();
                usuario.senha = scan.next();
                UsuarioDAO.getInstance().create(usuario);
                break;

            case 5:
                System.out.println("Informe nome,tipo,preço,quantidade,fabricante");
                produtos.nomeprod = scan.next();
                produtos.tipoprod = scan.next();
                produtos.preçoprod = scan.next();
                produtos.quantidade = scan.next();
                produtos.fabricante = scan.next();
                ProdutosDAO.getInstance().create(produtos);
                exibirProdutos();
                break;


        }
    }
    private void exibirProdutos(){
        for (Produtos produtos : produtoslist){
            System.out.println(produtos);
        }
    }
    private void exibirUsuarios(){
        for (Usuario usuario : usuarioslist){
            System.out.println(usuario);
        }
    }
    private void exibirVendas(){
        for (Vendas vendas : vendaslist){
            System.out.println(vendas);
        }
    }
}

