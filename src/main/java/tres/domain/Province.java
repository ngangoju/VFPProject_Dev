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
@Table(name = "Province")
@NamedQuery(name = "Province.findAll",
        query = "SELECT r FROM Province r order by v desc")
public class Province implements  Serializable{
       private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "provenceId")
    private int provenceId;
    
     @Column(name = "provenceName")
    private String provenceName;

    public int getProvenceId() {
        return provenceId;
    }

    public void setProvenceId(int provenceId) {
        this.provenceId = provenceId;
    }

    public String getProvenceName() {
        return provenceName;
    }

    public void setProvenceName(String provenceName) {
        this.provenceName = provenceName;
    }
     
     
}
