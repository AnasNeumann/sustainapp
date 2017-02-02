package com.ca.sustainapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.entities.ProfileEntity;
import com.ca.sustainapp.pojo.SustainappList;
import com.ca.sustainapp.responses.ProfileHttpRESTfullResponse;
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
	
}
