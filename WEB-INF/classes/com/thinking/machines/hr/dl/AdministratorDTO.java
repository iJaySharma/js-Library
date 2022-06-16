package com.thinking.machines.hr.dl;

public class AdministratorDTO implements java.io.Serializable,Comparable<AdministratorDTO>
{
private String username;
private String password;
public AdministratorDTO()
{
this.username="";
this.password="";
}
public void setUsername(String username)
{
this.username=username;
}
public String getUsername()
{
return this.username;
}
public void setPassword(String password)
{
this.password=password;
}
public String getPassword()
{
return this.password;
}
public boolean equals(Object object)
{
if(!(object instanceof AdministratorDTO)) return false;
AdministratorDTO administratorDTO;
administratorDTO=(AdministratorDTO) object;
return this.username.equalsIgnoreCase(administratorDTO.getUsername());
}
public int compareTo(AdministratorDTO administratorDTO)
{
return this.username.compareToIgnoreCase(administratorDTO.getUsername());
}
public int hashCode()
{
return this.username.hashCode();
}
}
