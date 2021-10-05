package eu.jlpc.models;

public class LocationStats {

	private String provinceState;
	private String countryRegion;
	private int latestTotalCases;
	private int diffFromPrevDay;

	public String getProvinceState() {
		return provinceState;
	}

	public void setProvinceState(String provinceState) {
		this.provinceState = provinceState;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public int getLatestTotalCases() {
		return latestTotalCases;
	}

	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}

	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}

	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}

	@Override
	public String toString() {
		return "LocationStats [provinceState=" + provinceState + ", countryRegion=" + countryRegion
				+ ", latestTotalCases=" + latestTotalCases + "]";
	}

}
