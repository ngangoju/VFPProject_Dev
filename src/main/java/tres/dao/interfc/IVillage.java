package tres.dao.interfc;

import java.util.List;

import tres.domain.Village;
/**
*
* @author Emmanuel
*/
public interface IVillage {
	 public Village saveVillage(Village village);
	    public List<Village> getListVillages();
	    public Village getVillageById(int villageId,String primaryKeyclomunName);
	    public Village UpdateVillage(Village village);  
}
