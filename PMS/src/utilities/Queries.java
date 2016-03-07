package utilities;

public enum Queries {
	getSupervisors("getSupervisors"),
	getCompanies("getCompanies"),
	getApplications("getApplications"),
	getServer("getServer"),
	insertApplication("insertApplication"),
	insertMonitoringError("insertMonitoringError"),
	getMonitoringOverview("getMonitoringOverview"),
	getTaskByServerName("getTaskByServerName"),
	insertHistory("insertHistory"),
	updateMonitoringErrorById("updateMonitoringErrorById"),
	updateMonitoringErrorSetAck("updateMonitoringErrorSetAck"),
	updateHistorySetEndDate("updateHistorySetEndDate"),
	updateServer("updateServer");
	
	private String query;
	private Queries(String query){
		this.query = query;
	}
	
	@Override
	public String toString(){
		return this.query;
	}
}
