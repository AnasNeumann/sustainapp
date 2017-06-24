package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import java.util.GregorianCalendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.ReportCriteria;
import com.ca.sustainapp.dao.ProfileServiceDAO;
import com.ca.sustainapp.dao.ReportServiceDAO;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.entities.ReportEntity;
import com.ca.sustainapp.entities.UserAccountEntity;
import com.ca.sustainapp.pojo.SearchResult;
import com.ca.sustainapp.responses.LightProfileResponse;
import com.ca.sustainapp.responses.ReportResponse;
import com.ca.sustainapp.responses.ReportsResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.ReportValidator;

/**
 * Restfull controller for reports management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 17/02/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class ReportController extends GenericController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private ReportServiceDAO reportService;
	@Autowired
	private ProfileServiceDAO profileService;
	@Autowired
	private ReportValidator reportValidator;
	
	/**
	 * create a new report
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/report", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> create(HttpServletRequest request) {
		if(!isConnected(request) || !reportValidator.validate(request).isEmpty()){
			return super.refuse(reportValidator.validate(request));
		}
		ProfileEntity profile = super.getConnectedUser(request).getProfile();
		ReportEntity report = new ReportEntity()
				.setDocument(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG))
				.setMessage(request.getParameter("message"))
				.setTimestamps(GregorianCalendar.getInstance())
				.setDocumentType(FilesUtils.FORMAT_PNG)
				.setProfilId(profile.getId())
				.setState(0);
		reportService.createOrUpdate(report);
		badgeService.superhero(profile);
		return super.success();
	}
	
	/**
	 * get all reports
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/report/all", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> getAll(HttpServletRequest request) {
		Optional<Long> startIndex = StringsUtils.parseLongQuickly(request.getParameter("startIndex"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(!startIndex.isPresent() || null == user || !user.getIsAdmin()){
			return super.refuse();
		}
		SearchResult<ReportEntity> listResult = reportService.searchByCriteres(new ReportCriteria().setState(0), startIndex.get(), 5L);
		ReportsResponse response = new ReportsResponse();
		for(ReportEntity entity : listResult.getResults()){
			response.getReports().add(new ReportResponse(entity).setOwner(new LightProfileResponse(profileService.getById(entity.getProfilId()))));
		}
		return super.success(response);
	}
	
	/**
	 * update a report by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/report/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> update(HttpServletRequest request) {
		Optional<Long> reportId = StringsUtils.parseLongQuickly(request.getParameter("report"));
		UserAccountEntity user = super.getConnectedUser(request);
		if(!reportId.isPresent() || null == user || !user.getIsAdmin()){
			return super.refuse();
		}
		reportService.createOrUpdate(reportService.getById(reportId.get()).setState(1));
		return super.success();
	}

}