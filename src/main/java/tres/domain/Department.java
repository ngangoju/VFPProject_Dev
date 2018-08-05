/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Department")
@NamedQuery(name = "Department.findAll",
query = "SELECT r FROM Department r order by v desc")
public class Department {
  private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "departmentId")
    private int departmentId;
    
    @Column(name = "departmentName")
    private String departmentName;
    
    
      @ManyToOne
    @JoinColumn(name = "headOfBy")
    private Users headOfBy;
    

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Users getHeadOfBy() {
        return headOfBy;
    }

    public void setHeadOfBy(Users headOfBy) {
        this.headOfBy = headOfBy;
    }
 
    
}
