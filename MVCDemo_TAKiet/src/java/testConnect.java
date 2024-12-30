
import dao.AccountDAO;
import entities.Account;
import java.sql.SQLException;
import static utils.DBUtils.getConnection;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author asus
 */
public class testConnect {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
        if (getConnection()!=null) 
        {System.out.println("Connect Successfully");}
        else {System.out.println("Connect failed");}
        
        AccountDAO dao = new AccountDAO();
        // Kiểm tra phương thức getById
        Account account = dao.getById(1);
        if (account != null) {
            System.out.println("Tìm thấy tài khoản với ID 1: " + account.getUsername());
        } else {
            System.out.println("Không tìm thấy tài khoản với ID 1.");
        }

        // Kiểm tra phương thức getByUsernamePassword
        Account account2 = dao.getByUsernamePassword("kiet", "123");
        if (account2 != null) {
            System.out.println("Đăng nhập thành công với username: " + account2.getUsername());
        } else {
            System.out.println("Sai thông tin đăng nhập.");
        }
    }
}
