package com.ca.sustainapp.controllers;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.dao.BadgeServiceDAO;
import com.ca.sustainapp.entities.BadgeEntity;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.JsonUtils;

/**
 * Restfull controller for profiles
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class EssaiUploadController extends GenericController {
	
	/**
	 * Service
	 */
	@Autowired
	private BadgeServiceDAO service;

	/**
	 * Upload d'image dans la table image
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile/upload", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
	public String upload(HttpServletRequest request){
		String base64file = request.getParameter("file");
		byte[] bytes = decodeBase64(base64file) ;
		BadgeEntity badge = new BadgeEntity()
				.setName("essai")
				.setScore(1)
				.setTimestamps(GregorianCalendar.getInstance())
				.setIcon(FilesUtils.compressImage(bytes, FilesUtils.FORMAT_JPG));
		return JsonUtils.objectTojsonQuietly(service.createOrUpdate(badge),Long.class);
	}
}
