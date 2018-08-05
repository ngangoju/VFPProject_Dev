/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tres.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "UserCategory")
@NamedQuery(name = "UserCategory.findAll",
        query = "SELECT r FROM UserCategory r order by userCatid desc")
public class UserCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "userCatid")
    private int userCatid;

    @Column(name = "usercategoryName")
    private String usercategoryName;

    public int getUserCatid() {
        return userCatid;
    }

    public void setUserCatid(int userCatid) {
        this.userCatid = userCatid;
    }

    public String getUsercategoryName() {
        return usercategoryName;
    }

    public void setUsercategoryName(String usercategoryName) {
        this.usercategoryName = usercategoryName;
    }

}
