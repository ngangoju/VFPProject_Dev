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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "District")
@NamedQuery(name = "District.findAll",
query = "SELECT r FROM District r order by v desc")
public class District implements  Serializable{
    
 private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "districtId")
    private int districtId;
    
    @Column(name = "districtName")
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "province")
    private Province province;

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
      
      
    
}
