package com.ca.sustainapp.controllers;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.ReportServiceDAO;
import com.ca.sustainapp.entities.ReportEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.utils.FilesUtils;
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
	private ReportValidator reportValidator;
	
	/**
	 * get all reports
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/report", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * create a new report
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/report", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		if(!isConnected(request) || !reportValidator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(reportValidator.validate(request)).buildJson();
		}
		ReportEntity report = new ReportEntity()
				.setDocument(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG))
				.setMessage(request.getParameter("message"))
				.setTimestamps(GregorianCalendar.getInstance())
				.setDocumentType(FilesUtils.FORMAT_JPG)
				.setProfilId(super.getConnectedUser(request).getProfile().getId())
				.setState(0);
		reportService.createOrUpdate(report);
		return new HttpRESTfullResponse().setCode(1).buildJson();
	}
	
	/**
	 * update a report by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/report", method = RequestMethod.PUT, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a report by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/report", method = RequestMethod.DELETE, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}
	
}