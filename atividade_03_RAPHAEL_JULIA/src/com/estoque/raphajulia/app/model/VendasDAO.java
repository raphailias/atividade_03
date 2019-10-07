package com.estoque.raphajulia.app.model;
import com.estoque.raphajulia.app.Util.Constants;
import com.estoque.raphajulia.app.Util.IDAO;

import java.sql.*;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
public class VendasDAO implements IDAO<Vendas> {

    private static VendasDAO instance = null;
    public static VendasDAO getInstance()
    {
        if (instance == null)
            instance = new VendasDAO();

        return instance;
    }

    private Connection conn;

    private VendasDAO()
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
    public boolean create(Vendas vendas)
    {
        PreparedStatement comandoSQL;

        try
        {
            comandoSQL = conn.prepareStatement(kInsertVendas);
            comandoSQL.setString(1, vendas.nomevend);
            comandoSQL.setInt(2, vendas.quantidadevend);
            comandoSQL.setString(3, vendas.nomeproduto);
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
    public Vendas read(String search_param) {
        Vendas vendas = null;

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(kSelectName(search_param));

            Vendas pk = new Vendas
                    (
                            rs.getString("nomevend"),
                            rs.getInt("quantidadevend"),
                            rs.getString("nomeproduto")


                    );

            vendas = pk;rs.close();
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace();
        }
        return vendas;
    }
    public List<Vendas> readAll() {
        List<Vendas> vendas = new ArrayList<>();

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(kSelectAllSql);

            while (rs.next())
            {
                Vendas pk = new Vendas
                        (
                                rs.getString("nomevend"),
                                rs.getInt("quantidadevend"),
                                rs.getString("nomeproduto")


                        );

                vendas.add(pk);
            }rs.close();
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace();
        }

        return vendas;
    }
    public boolean update(String search_param, Usuario usuario) {
        try
        {
            Statement comandoSQL = conn.createStatement();
            comandoSQL.executeUpdate(kUpdateVendas(usuario, "nome", search_param));

            conn.commit();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private final String kTableName = "vendas";
    private final String kSelectAllSql = "SELECT * FROM " + kTableName + ";";
    private final String kSelectName(String name) {
        return "SELECT * FROM " + kTableName + " WHERE nome = '" + name + "';";
    }
    private final String kInsertVendas = "INSERT INTO " + kTableName + " VALUES (?,?,?);";
    private final String kUpdateVendas(Usuario pk, String search_param_col, String search_param){
        return "UPDATE " + kTableName +
                " SET nome = \"" + pk.name + "\", type = \"" + pk.typeString + "\", location = \"" + pk.location + "\", height = " + pk.height + ", weight = " + pk.weight + " "
                + "WHERE " + search_param_col + " = \"" + search_param + "\";";
    }




}
