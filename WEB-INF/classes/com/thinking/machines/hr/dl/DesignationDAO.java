package com.thinking.machines.hr.dl;
import com.thinking.machines.hr.dl.*;
import java.util.*;
import java.sql.*;

public class DesignationDAO
{
public void add(DesignationDTO designationDTO) throws DAOException
{
try
{
if(designationDTO==null) throw new DAOException("Argument can not be null");
Connection connection;
connection=DAOConnection.getConnection();
String title=designationDTO.getTitle();
Statement statement;
ResultSet resultSet;
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(title) as cnt from designation where title=?");
preparedStatement.setString(1,title);
resultSet=preparedStatement.executeQuery();
resultSet.next();
int cnt=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
if(cnt!=0)
{
connection.close();
throw new DAOException("Designation : "+title+" exists");
}
preparedStatement=connection.prepareStatement("insert into designation (title) values (?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
designationDTO.setCode(resultSet.getInt(1));
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public List<DesignationDTO> getAll() throws DAOException
{
try
{
DesignationDTO designationDTO;
List<DesignationDTO> designations;
designations=new LinkedList<DesignationDTO>();
Connection connection;
connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from designation order by title");
while(resultSet.next())
{
designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title").trim());
designations.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close();
return designations;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(title) as cnt from designation");
resultSet.next();
int count;
count=resultSet.getInt("cnt");
resultSet.close();
statement.close();
connection.close();
return count;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void delete(int code) throws DAOException
{
try
{
if(code<=0) throw new DAOException("Invalid designation code : "+code);
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(code) as cnt from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation code : "+code+" does not exist");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void update(DesignationDTO designationDTO) throws DAOException
{
try
{
int code=designationDTO.getCode();
String title=designationDTO.getTitle().trim();
if(code<=0) throw new DAOException("Invalid designation code : "+code);
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(title) as cnt from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation code : "+code+" does not exist");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select count(title) as cnt from designation where title=? and code!=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")!=0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation : "+title+" already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public DesignationDTO getByCode(int code) throws DAOException
{
try
{
if(code<=0) throw new DAOException("Invalid designation code : "+code);
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
DesignationDTO designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title"));
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation code : "+code+" does not exist");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public DesignationDTO getByTitle(String title) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
DesignationDTO designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(resultSet.getInt("code"));
designationDTO.setTitle(resultSet.getString("title"));
resultSet.close();
preparedStatement.close();
connection.close();
return designationDTO;
}
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Designation : "+title+" does not exist");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean codeExists(int code) throws DAOException
{
try
{
if(code<=0) throw new DAOException("Invalid designation code : "+code);
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(title) as cnt from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
int count=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
connection.close();
return count==1;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean titleExists(String title) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(title) as cnt from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
int count=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
connection.close();
return count==1;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
}
