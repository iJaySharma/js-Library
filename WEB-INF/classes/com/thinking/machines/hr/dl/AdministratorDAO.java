package com.thinking.machines.hr.dl;
import com.thinking.machines.hr.dl.*;
import java.util.*;
import java.sql.*;

public class AdministratorDAO
{
public AdministratorDTO getByUsername(String username) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from administrator where uname=?");
preparedStatement.setString(1,username);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
AdministratorDTO administratorDTO;
administratorDTO=new AdministratorDTO();
administratorDTO.setUsername(resultSet.getString("uname"));
administratorDTO.setPassword(resultSet.getString("pwd"));
resultSet.close();
preparedStatement.close();
connection.close();
return administratorDTO;
}
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Invalid username : "+username);
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}
