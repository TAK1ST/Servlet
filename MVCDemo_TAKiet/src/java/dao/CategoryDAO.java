/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entities.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static utils.DBUtils.getConnection;

/**
 *
 * @author vothimaihoa
 */
public class CategoryDAO {

    public Category getById(int categoryId) {
        Category c = null;
        String sql = " SELECT id, name "
                + " from Category "
                + " where id = ?";
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex) {
            System.out.println("SQL Exception in getting category by id. Details: ");
            ex.printStackTrace();
        }
        return c;
    }

    public List<Category> list() {
        List<Category> list = null;
        String sql = " SELECT id, name "
                + " from Category ";
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                list = new ArrayList<>();
                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setName(rs.getString("name"));
                    list.add(c);
                }
            }
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("DBUtils not found.");
        } catch (SQLException ex) {
            System.out.println("SQL Exception in getting list of categories. Details: ");
            ex.printStackTrace();
        }
        return list;
    }

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM Category";

        try (Connection connection = getConnection(); 
                PreparedStatement statement = connection.prepareStatement(sql); 
                ResultSet resultSet = statement.executeQuery()) 
        {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                Category category = new Category(id, name);
                categories.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }
}
