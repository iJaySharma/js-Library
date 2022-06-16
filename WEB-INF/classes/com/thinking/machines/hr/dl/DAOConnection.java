package com.thinking.machines.hr.dl;
import java.sql.*;
import com.thinking.machines.hr.dl.DAOException;

public class DAOConnection
{
private DAOConnection(){}
public static Connection getConnection() throws DAOException
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/tmdb","tmdbuser","tmdbuser");
return connection;
}catch(Throwable throwable)
{
throw new DAOException(throwable.getMessage());
}
}
}
