/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tres.dao.interfc;

import java.util.List;
import tres.domain.Sector;

public interface ISector {
    public Sector saveSector(Sector sector);
       public List<Sector> getListSectors();
       public Sector getSectorById(int sectorId,String primaryKeyclomunName);
       public Sector UpdateSector(Sector sector);    
}
