package com.ca.sustainapp.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Json de réponse d'une liste de plusieurs signalements
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 05/05/2017
 * @version 1.0
 */
public class ReportsResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private List<ReportResponse> reports = new ArrayList<ReportResponse>();
	
	/**
	 * @return the reports
	 */
	public List<ReportResponse> getReports() {
		return reports;
	}
	
	/**
	 * @param reports the reports to set
	 */
	public ReportsResponse setReports(List<ReportResponse> reports) {
		this.reports = reports;
		return this;
	}
}