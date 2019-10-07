package com.estoque.raphajulia.app.model;
import com.estoque.raphajulia.app.Util.Constants;
import com.estoque.raphajulia.app.Util.IDAO;

import java.sql.*;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
public class ProdutosDAO implements IDAO<Produtos> {

    private static ProdutosDAO instance = null;
    public static ProdutosDAO getInstance()
    {
        if (instance == null)
            instance = new ProdutosDAO();

        return instance;
    }

    private Connection conn;

    private ProdutosDAO()
    {
        try
        {
            conn = DriverManager.getConnection(Constants.kDBURL(Constants.kprojdb));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public boolean create(Produtos produtos)
    {
        PreparedStatement comandoSQL;

        try
        {
            comandoSQL = conn.prepareStatement("INSERT INTO produtos VALUES(null,?,?,?,?,?);");
            comandoSQL.setString(2, produtos.nomeprod);
            comandoSQL.setString(3, produtos.tipoprod);
            comandoSQL.setInt(4, produtos.preçoprod);
            comandoSQL.setInt(5, produtos.quantidade);
            comandoSQL.setString(6, produtos.fabricante);
            comandoSQL.executeUpdate();
            conn.commit();

            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public int read(String search_param) {
        Produtos produtos = null;

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM produtos WHERE search_parm LIKE '%"+ search_param+"%';");

            Produtos pk = new Produtos
                    (

                            rs.getString("nomeprod"),
                            rs.getString("tipoprod"),
                            rs.getInt("preçoprod"),
                            rs.getInt("quantidade"),
                            rs.getString("fabricante")

                    );rs.close();

            produtos = pk;
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace();
        }
        return produtos.quantidade;
    }
    public List<Produtos> readAll(String tipoprod) {
        List<Produtos> produtos = new ArrayList<>();

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM produtos WHERE tipoprod LIKE '%" + tipoprod +"%';");

            while (rs.next())
            {
                Produtos pk = new Produtos
                        (
                                rs.getString("nomeprod"),
                                rs.getString("tipoprod"),
                                rs.getInt("preçoprod"),
                                rs.getInt("quantidade"),
                                rs.getString("fabricante")



                        );

                produtos.add(pk);
            }
            rs.close();
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace();
        }

        return produtos;
    }
    public boolean update(Produtos produtos) {
        try
        {
            Statement comandoSQL = conn.createStatement();
            comandoSQL.executeUpdate("UPDATE produtos SET"+"nomeprod=\"" + produtos.nomeprod+"\","+
                    "tipoprod=\"" + produtos.tipoprod+"\","+
                    "preçoprod=\"" + produtos.preçoprod+"\","+
                    "quantidade=\"" + produtos.quantidade+"\","+
                    "fabricante=\"" + produtos.fabricante+"\","+"WHERE id=" + produtos.idprod+";");


            conn.commit();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public void deleteByName(String name) {

        try {
            Statement comandoSql = conn.createStatement();
            comandoSql.executeUpdate(
                    "DELETE FROM produtos WHERE nameprod=" + name+";"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}
