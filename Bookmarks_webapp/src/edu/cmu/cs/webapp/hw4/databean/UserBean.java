/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.databean;

import org.genericdao.PrimaryKey;

//import databeans.User;

//import databean.UserBean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

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
    private String  hashedPassword = "*";
    private int     salt           = 0;
    
    public boolean checkPassword(String password) {
        return hashedPassword.equals(hash(password));
    }
    
    public void encodePassword(String s) {
        salt = newSalt();
        hashedPassword = hash(s);
    }
    
    public int getId()                  { return id; }
    public String getEmail()            { return email; }
    public String getFirstName()        { return firstName; }
    public String getLastName()         { return lastName; }
    public String  getHashedPassword() { return hashedPassword; }
    public int     getSalt()           { return salt;           }

    public int     hashCode()          { return email.hashCode(); }
    
    public String toString() {
        return "Email("+getEmail()+")";
    }
    
    public int compareTo(UserBean other) {
        // Order first by lastName and then by firstName
        int c = lastName.compareTo(other.lastName);
        if (c != 0) return c;
        c = firstName.compareTo(other.firstName);
        if (c != 0) return c;
        return email.compareTo(other.email);
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof UserBean) {
            UserBean other = (UserBean) obj;
            return email.equals(other.email);
        }
        return false;
    }

    public void setId(int i)            { id = i;  }
    public void setEmail(String s)      { email = s;    }
    public void setFirstName(String s)  { firstName = s;    }
    public void setLastName(String s)   { lastName = s;    }
    public void setHashedPassword(String x)  { hashedPassword = x; }
    public void setSalt(int x)               { salt = x;           }

    private String hash(String clearPassword) {
        if (salt == 0) return null;

        MessageDigest md = null;
        try {
          md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
          throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
        }

        String saltString = String.valueOf(salt);
        
        md.update(saltString.getBytes());
        md.update(clearPassword.getBytes());
        byte[] digestBytes = md.digest();

        // Format the digest as a String
        StringBuffer digestSB = new StringBuffer();
        for (int i=0; i<digestBytes.length; i++) {
          int lowNibble = digestBytes[i] & 0x0f;
          int highNibble = (digestBytes[i]>>4) & 0x0f;
          digestSB.append(Integer.toHexString(highNibble));
          digestSB.append(Integer.toHexString(lowNibble));
        }
        String digestStr = digestSB.toString();

        return digestStr;
    }

    private int newSalt() {
        Random random = new Random();
        return random.nextInt(8192)+1;  // salt cannot be zero
    }

}

