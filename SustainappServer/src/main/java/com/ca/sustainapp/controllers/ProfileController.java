package com.ca.sustainapp.controllers;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.BadgeHttpRESTfullResponse;
import com.ca.sustainapp.responses.ProfileHttpRESTfullResponse;
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
public class ProfileController {
	
	/**
	 * Service
	 */
	@Autowired
	private BadgeServiceDAO service;
	
	/**
	 * get all profiles
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String get(HttpServletRequest request) {
		String query = request.getParameter("query");
		if(null == query){
			return SustainappConstantes.SUCCES_JSON;
		} else {
			return JsonUtils.objectTojsonQuietly(query,String.class);
		}      
    }
	
	/**
	 * get all profiles
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/profile/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
    public String getAll(HttpServletRequest request) {
		ProfileEntity p1 = new ProfileEntity().setId(1L).setFirstName("Anas").setLastName("Neumann");
		ProfileEntity p2 = new ProfileEntity().setId(2L).setFirstName("Hedi").setLastName("Mabrouk");
		ProfileEntity p3 = new ProfileEntity().setId(3L).setFirstName("Moustapha").setLastName("Roty");
		List<ProfileEntity> data = new SustainappList<ProfileEntity>().put(p1).put(p2).put(p3);
		return new ProfileHttpRESTfullResponse().setData(data).buildJson();
    }

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
	
	/**
	 * get all badge
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/badge/all", method = RequestMethod.GET, produces = SustainappConstantes.MIME_JSON)
	public String getAllBadge(HttpServletRequest request){
		return new BadgeHttpRESTfullResponse().setData(service.getAll()).buildJson(); 
	}
	
}
