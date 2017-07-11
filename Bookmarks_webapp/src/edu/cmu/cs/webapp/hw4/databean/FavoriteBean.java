/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.databean;

//import org.genericdao.MaxSize;
import org.genericdao.PrimaryKey;



@PrimaryKey("fid")
public class FavoriteBean {
 /*
favorite id (primary key)
user id
URL
comment
click count
*/
    private int fid;
    private int id;
    private String url;
    private String comment;
    private int clickcount;
    private int    position;
    private String owner       = null;
   
    public int getFid()               { return fid;      }
    public int getId()                  { return id;         }
    public String getUrl()              { return url;        }
    public String getComment()          { return comment;    }
    public int getClickCount()          { return clickcount; }
    public int    getPosition()          { return position;     }
    public String getOwner()       { return owner;       }
   
    
    public int compareTo(FavoriteBean other) {
        // Order first by owner, then by position
        if (owner == null && other.owner != null) return -1;
        if (owner != null && other.owner == null) return 1;
        int c = owner.compareTo(other.owner);
        if (c != 0) return c;
        return position - other.position;
    }
    
    public boolean equals(Object obj) {
        if (obj instanceof FavoriteBean) {
            FavoriteBean other = (FavoriteBean) obj;
            return compareTo(other) == 0;
        }
        return false;
    }
    
    
    
    public void setFid(int i)         { fid = i;         }
    public void setId(int i)            { id = i;            }
    public void setUrl(String s)        { url = s;           }
    public void setComment(String s)    { comment = s;       }
    public void setClickCount(int s)    { clickcount = s;    }
    public void   setPosition(int i)     { position = i;        }
    public void setOwner(String email) { owner = email; }

    public String toString() {
        return "Favorite("+fid+")";
    }
}

