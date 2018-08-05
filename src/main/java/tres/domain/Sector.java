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
@Table(name = "Sector")
@NamedQuery(name = "Sector.findAll",
query = "SELECT r FROM Sector r order by v desc")
public class Sector implements  Serializable{
    
 private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "sectorId")
    private int sectorId;
    
    @Column(name = "sectorName")
    private String sectorName;
    
    @ManyToOne
    @JoinColumn(name = "distric")
    private District distric;

    public int getSectorId() {
        return sectorId;
    }

    public void setSectorId(int sectorId) {
        this.sectorId = sectorId;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public District getDistric() {
        return distric;
    }

    public void setDistric(District distric) {
        this.distric = distric;
    }
    
    
    
    
    
    
    
}
