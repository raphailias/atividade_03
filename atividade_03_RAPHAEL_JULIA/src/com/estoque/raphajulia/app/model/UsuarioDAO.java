package com.estoque.raphajulia.app.model;
import com.estoque.raphajulia.app.Util.Constants;
import com.estoque.raphajulia.app.Util.IDAO;

import java.sql.*;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
public class UsuarioDAO implements IDAO<Usuario> {
    boolean delete(String search_param);
    private static UsuarioDAO instance = null;
    public static UsuarioDAO getInstance()
    {
        if (instance == null)
            instance = new UsuarioDAO();

        return instance;
    }

    private Connection conn;

    private UsuarioDAO()
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
    public boolean create(Usuario usuario)
    {
        PreparedStatement comandoSQL;

        try
        {
            comandoSQL = conn.prepareStatement(kInsertUsuario);
            comandoSQL.setString(2, usuario.name);
            comandoSQL.setString(3, usuario.senha);
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
    public Usuario read(String search_param) {
        Usuario usuario = null;

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(kSelectName(search_param));

            Usuario pk = new Usuario
                    (
                            rs.getString("nome"),
                            rs.getString("senha")


                    );

            usuario = pk;
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace();
        }
        return usuario;
    }
    public List<Usuario> readAll() {
        List<Usuario> usuario = new ArrayList<>();

        try
        {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(kSelectAllSql);

            while (rs.next())
            {
                Usuario pk = new Usuario
                        (
                                rs.getString("nome"),
                                rs.getString("senha")



                        );

                usuario.add(pk);
            }
        }
        catch (SQLException sqlE)
        {
            sqlE.printStackTrace();
        }

        return usuario;
    }
    public boolean update(String search_param, Usuario usuario) {
        try
        {
            Statement comandoSQL = conn.createStatement();
            comandoSQL.executeUpdate(kUpdateUsuario(usuario, "nome", search_param));

            conn.commit();
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    private final String kTableName = "usuarios";
    private final String kSelectAllSql = "SELECT * FROM " + kTableName + ";";
    private final String kSelectName(String name) {
        return "SELECT * FROM " + kTableName + " WHERE nome = '" + name + "';";
    }
    private final String kInsertUsuario = "INSERT INTO " + kTableName + " VALUES (?,?,?);";
    private final String kUpdateUsuario(Usuario pk, String search_param_col, String search_param){
        return "UPDATE " + kTableName +
                " SET nome = \"" + pk.name + "\", type = \"" + pk.typeString + "\", location = \"" + pk.location + "\", height = " + pk.height + ", weight = " + pk.weight + " "
                + "WHERE " + search_param_col + " = \"" + search_param + "\";";
    }




}
