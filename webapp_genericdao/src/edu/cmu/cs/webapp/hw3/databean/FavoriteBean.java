/* @author HaryKrishnan Ramasubramanian.
 * 25 November 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw3.databean;

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
   
    public int getFid()               { return fid;      }
    public int getId()                  { return id;         }
    public String getUrl()              { return url;        }
    public String getComment()          { return comment;    }
    public int getClickCount()          { return clickcount; }
    public int    getPosition()          { return position;     }

   
    public void setFid(int i)         { fid = i;         }
    public void setId(int i)            { id = i;            }
    public void setUrl(String s)        { url = s;           }
    public void setComment(String s)    { comment = s;       }
    public void setClickCount(int s)    { clickcount = s;    }
    public void   setPosition(int i)     { position = i;        }
   
}



