package com.thinking.machines.hr.dl;
import com.thinking.machines.hr.dl.*;
import java.util.*;
import java.math.*;
import java.text.*;
import java.sql.*;

public class EmployeeDAO
{
public void add(EmployeeDTO employeeDTO) throws DAOException
{
try
{
if(employeeDTO==null) throw new DAOException("Argument can not be null");
int designationCode=employeeDTO.getDesignationCode();
String panNumber=employeeDTO.getPANNumber();
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(!(new DesignationDAO().codeExists(designationCode))) throw new DAOException("Invalid designation code : "+designationCode);
Connection connection;
connection=DAOConnection.getConnection();
Statement statement;
ResultSet resultSet;
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(pan_number) as cnt from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
resultSet.next();
int cnt=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
boolean foundPANNumber=false;
if(cnt!=0) foundPANNumber=true;
preparedStatement=connection.prepareStatement("select count(aadhar_card_number) as cnt from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
resultSet.next();
cnt=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
boolean foundAadharCardNumber=false;
if(cnt!=0) foundAadharCardNumber=true;
if(foundAadharCardNumber && foundPANNumber)
{
connection.close();
throw new DAOException("Employee PAN number : "+panNumber+" and Aadhar card number : "+aadharCardNumber+" exists");
}
if(foundAadharCardNumber)
{
connection.close();
throw new DAOException("Employee Aadhar card number : "+aadharCardNumber+" exists");
}
if(foundPANNumber)
{
connection.close();
throw new DAOException("Employee PAN Number : "+panNumber+" exists");
}
preparedStatement=connection.prepareStatement("insert into employee (name,designation_code,date_of_birth,basic_salary,is_indian,gender,pan_number,aadhar_card_number) values(?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,employeeDTO.getName());
preparedStatement.setInt(2,designationCode);
java.util.Date date=employeeDTO.getDateOfBirth();
preparedStatement.setDate(3,new java.sql.Date(date.getYear(),date.getMonth(),date.getDate()));
preparedStatement.setBigDecimal(4,employeeDTO.getBasicSalary());
preparedStatement.setBoolean(5,employeeDTO.isIndian());
if(employeeDTO.getGender().equalsIgnoreCase("Male")) preparedStatement.setString(6,"M");
else preparedStatement.setString(6,"F");
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int employeeId=resultSet.getInt(1);
employeeDTO.setEmployeeId("EMP"+employeeId);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public List<EmployeeDTO> getAll() throws DAOException
{
try
{
EmployeeDTO employeeDTO;
List<EmployeeDTO> employees;
employees=new LinkedList<EmployeeDTO>();
Connection connection;
connection=DAOConnection.getConnection();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select employee_id,name,designation_code,date_of_birth,basic_salary,is_indian,gender,pan_number,aadhar_card_number from employee");
int employeeId;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
while(resultSet.next())
{
employeeDTO=new EmployeeDTO();
employeeId=resultSet.getInt("employee_id");
employeeDTO.setEmployeeId("EMP"+employeeId);
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(simpleDateFormat.parse(resultSet.getDate("date_of_birth").toString().trim()));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.isIndian(resultSet.getBoolean("is_indian"));
if(resultSet.getString("gender").equals("M")) employeeDTO.setGender(EmployeeDTO.MALE);
else employeeDTO.setGender(EmployeeDTO.FEMALE);
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
employees.add(employeeDTO);
}
resultSet.close();
statement.close();
connection.close();
return employees;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
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
resultSet=statement.executeQuery("select count(name) as cnt from employee");
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
public void delete(String employeeId) throws DAOException
{
try
{
if(employeeId.length()<=3) throw new DAOException("Invalid Employee Id. "+employeeId);
int employeeIdNumericPart=Integer.parseInt(employeeId.substring(3));
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(name) as cnt from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdNumericPart);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Employee Id : "+employeeId+" does not exist");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdNumericPart);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(NumberFormatException numberFormateException)
{
throw new DAOException("Invalid Employee Id");
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public void update(EmployeeDTO employeeDTO) throws DAOException
{
try
{
String employeeId=employeeDTO.getEmployeeId();
if(employeeId.length()<=3) throw new DAOException("Invalid Employee Id. "+employeeId);
int employeeIdNumericPart=Integer.parseInt(employeeId.substring(3));
int designationCode=employeeDTO.getDesignationCode();
String panNumber=employeeDTO.getPANNumber();
String aadharCardNumber=employeeDTO.getAadharCardNumber();
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(name) as cnt from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdNumericPart);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Employee Id : "+employeeId+" does not exist");
}
resultSet.close();
preparedStatement.close();
if(!(new DesignationDAO().codeExists(designationCode)))
{
connection.close();
throw new DAOException("Invalid designation code : "+designationCode);
}
preparedStatement=connection.prepareStatement("select count(pan_number) as cnt from employee where pan_number=? and employee_id!=?");
preparedStatement.setString(1,panNumber);
preparedStatement.setInt(2,employeeIdNumericPart);
resultSet=preparedStatement.executeQuery();
resultSet.next();
int cnt=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
boolean foundPANNumber=false;
if(cnt!=0) foundPANNumber=true;
preparedStatement=connection.prepareStatement("select count(aadhar_card_number) as cnt from employee where aadhar_card_number=? and employee_id!=?");
preparedStatement.setString(1,aadharCardNumber);
preparedStatement.setInt(2,employeeIdNumericPart);
resultSet=preparedStatement.executeQuery();
resultSet.next();
cnt=resultSet.getInt("cnt");
resultSet.close();
preparedStatement.close();
boolean foundAadharCardNumber=false;
if(cnt!=0) foundAadharCardNumber=true;
if(foundAadharCardNumber && foundPANNumber)
{
connection.close();
throw new DAOException("Employee PAN number : "+panNumber+" and Aadhar card number : "+aadharCardNumber+" exists");
}
if(foundAadharCardNumber)
{
connection.close();
throw new DAOException("Employee Aadhar card number : "+aadharCardNumber+" exists");
}
if(foundPANNumber)
{
connection.close();
throw new DAOException("Employee PAN Number : "+panNumber+" exists");
}
preparedStatement=connection.prepareStatement("update employee set name=?, designation_code=?, date_of_birth=?, basic_salary=?, is_indian=?, gender=?, pan_number=?, aadhar_card_number=? where employee_id=?");
preparedStatement.setString(1,employeeDTO.getName());
preparedStatement.setInt(2,designationCode);
java.util.Date date=employeeDTO.getDateOfBirth();
preparedStatement.setDate(3,new java.sql.Date(date.getYear(),date.getMonth(),date.getDate()));
preparedStatement.setBigDecimal(4,employeeDTO.getBasicSalary());
preparedStatement.setBoolean(5,employeeDTO.isIndian());
if(employeeDTO.getGender().equalsIgnoreCase("Male")) preparedStatement.setString(6,"M");
else preparedStatement.setString(6,"F");
preparedStatement.setString(7,panNumber);
preparedStatement.setString(8,aadharCardNumber);
preparedStatement.setInt(9,employeeIdNumericPart);
preparedStatement.executeUpdate();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public EmployeeDTO getByEmployeeId(String employeeId) throws DAOException
{
try
{
if(employeeId.length()<=3) throw new DAOException("Invalid Employee Id. "+employeeId);
int employeeIdNumericPart=Integer.parseInt(employeeId.substring(3));
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(name) as cnt from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdNumericPart);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Employee Id : "+employeeId+" does not exist");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select employee_id,name,designation_code,date_of_birth,basic_salary,is_indian,gender,pan_number,aadhar_card_number from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdNumericPart);
resultSet=preparedStatement.executeQuery();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
resultSet.next();
EmployeeDTO employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(simpleDateFormat.parse(resultSet.getDate("date_of_birth").toString().trim()));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.isIndian(resultSet.getBoolean("is_indian"));
if(resultSet.getString("gender").equals("M")) employeeDTO.setGender(EmployeeDTO.MALE);
else employeeDTO.setGender(EmployeeDTO.FEMALE);
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
}
}
public EmployeeDTO getByPanNumber(String panNumber) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(name) as cnt from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("PAN number : "+panNumber+" does not exist");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select employee_id,name,designation_code,date_of_birth,basic_salary,is_indian,gender,pan_number,aadhar_card_number from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
resultSet=preparedStatement.executeQuery();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
resultSet.next();
EmployeeDTO employeeDTO;
employeeDTO=new EmployeeDTO();
int employeeId=resultSet.getInt("employee_id");
employeeDTO.setEmployeeId("EMP"+employeeId);
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(simpleDateFormat.parse(resultSet.getDate("date_of_birth").toString().trim()));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.isIndian(resultSet.getBoolean("is_indian"));
if(resultSet.getString("gender").equals("M")) employeeDTO.setGender(EmployeeDTO.MALE);
else employeeDTO.setGender(EmployeeDTO.FEMALE);
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
}
}
public EmployeeDTO getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(name) as cnt from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Aadhar card number : "+aadharCardNumber+" does not exist");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select employee_id,name,designation_code,date_of_birth,basic_salary,is_indian,gender,pan_number,aadhar_card_number from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
resultSet=preparedStatement.executeQuery();
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
resultSet.next();
EmployeeDTO employeeDTO;
employeeDTO=new EmployeeDTO();
int employeeId=resultSet.getInt("employee_id");
employeeDTO.setEmployeeId("EMP"+employeeId);
employeeDTO.setName(resultSet.getString("name"));
employeeDTO.setDesignationCode(resultSet.getInt("designation_code"));
employeeDTO.setDateOfBirth(simpleDateFormat.parse(resultSet.getDate("date_of_birth").toString().trim()));
employeeDTO.setBasicSalary(resultSet.getBigDecimal("basic_salary"));
employeeDTO.isIndian(resultSet.getBoolean("is_indian"));
if(resultSet.getString("gender").equals("M")) employeeDTO.setGender(EmployeeDTO.MALE);
else employeeDTO.setGender(EmployeeDTO.FEMALE);
employeeDTO.setPANNumber(resultSet.getString("pan_number"));
employeeDTO.setAadharCardNumber(resultSet.getString("aadhar_card_number"));
resultSet.close();
preparedStatement.close();
connection.close();
return employeeDTO;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}catch(ParseException parseException)
{
throw new DAOException(parseException.getMessage());
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
try
{
if(employeeId.length()<=3) throw new DAOException("Invalid Employee Id. "+employeeId);
int employeeIdNumericPart=Integer.parseInt(employeeId.substring(3));
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(name) as cnt from employee where employee_id=?");
preparedStatement.setInt(1,employeeIdNumericPart);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
connection.close();
return true;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(name) as cnt from employee where pan_number=?");
preparedStatement.setString(1,panNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
return true;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
try
{
Connection connection;
connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select count(name) as cnt from employee where aadhar_card_number=?");
preparedStatement.setString(1,aadharCardNumber);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
resultSet.next();
if(resultSet.getInt("cnt")==0)
{
resultSet.close();
preparedStatement.close();
connection.close();
return false;
}
resultSet.close();
preparedStatement.close();
return true;
}catch(SQLException sqlException)
{
throw new DAOException(sqlException.getMessage());
}
}
public boolean designationCodeExists(int designationCode) throws DAOException
{
return new DesignationDAO().codeExists(designationCode);
}
}
