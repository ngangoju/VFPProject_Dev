package tres.dao.interfc;

import java.util.List;

import tres.domain.Clearance;

public interface IClearance {
	public Clearance saveClearance(Clearance clearance);

	public List<Clearance> getListClearances();

	public Clearance getClearanceById(int activities, String primaryKeyclomunName);

	public Clearance UpdateClearance(Clearance clearance);
}
