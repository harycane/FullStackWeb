/* @author HaryKrishnan Ramasubramanian.
 * 25 November 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw3.databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class UserBean {
 /*
user id (primary key)
e-mail address
user's first name
user's last name
user's password
*/
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    public int getId()                  { return id; }
    public String getEmail()            { return email; }
    public String getFirstName()        { return firstName; }
    public String getLastName()         { return lastName; }
    public String getPassword()         { return password; }
   

    public void setId(int i)            { id = i;  }
    public void setEmail(String s)      { email = s;    }
    public void setFirstName(String s)  { firstName = s;    }
    public void setLastName(String s)   { lastName = s;    }
    public void setPassword(String s)   { password = s;    }
   
}
