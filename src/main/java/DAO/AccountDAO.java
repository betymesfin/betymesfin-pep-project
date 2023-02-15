package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    
    public Account insertnewAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            
            String sql = "insert into account (username, password) values (?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            


            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account checkAccount(String username,String password){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            
            String sql = "select * from account where username=? and password=? ;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            
             
             preparedStatement.setString(1,username);
             preparedStatement.setString(2,password);
 
 
             ResultSet rs = preparedStatement.executeQuery();
             while(rs.next()){
                 Account acc= new Account(rs.getInt("account_id"), rs.getString("username"),
                         rs.getString("password"));
                  return acc;
             }
         }catch(SQLException e){
             System.out.println(e.getMessage());
         }
         return null;
        }
        public Account getAccountById(int id){
            Connection connection = ConnectionUtil.getConnection();
            try {
                
                String sql = "select username from account where account_id = ?";
                
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
    
                
                preparedStatement.setInt(1,id);
    
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                    rs.getString("password"));
                    return account;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
    }
    

   

