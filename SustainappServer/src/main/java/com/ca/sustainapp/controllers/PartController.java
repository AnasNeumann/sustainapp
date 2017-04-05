package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.PartCriteria;
import com.ca.sustainapp.entities.PartEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.responses.HttpRESTfullResponse;
import com.ca.sustainapp.responses.IdResponse;
import com.ca.sustainapp.responses.PartResponse;
import com.ca.sustainapp.utils.FilesUtils;
import com.ca.sustainapp.utils.StringsUtils;
import com.ca.sustainapp.validators.PartValidator;

/**
 * Restfull controller for part management
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 04/04/2017
 * @version 1.0
 */
@CrossOrigin
@RestController
public class PartController extends GenericCourseController {

	/**
	 * Injection de dépendances
	 */
	@Autowired
	private PartValidator validator;
	
	/**
	 * create a new part
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part", headers = "Content-Type= multipart/form-data", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String create(HttpServletRequest request) {
		TopicEntity topic = super.getTopicIfOwner(request);
		if(null == topic || !validator.validate(request).isEmpty()){
			return new HttpRESTfullResponse().setCode(0).setErrors(validator.validate(request)).buildJson();
		}
		List<PartEntity> allParts = getService.cascadeGetPart(new PartCriteria().setTopicId(topic.getId()));
		Integer numero = 0;
		if(null != allParts){
			numero+=allParts.size();
		}
		PartEntity part = new PartEntity();
		Optional<Integer> type = StringsUtils.parseIntegerQuietly(request.getParameter("type"));
		part.setType(type.get())
			.setTopicId(topic.getId())
			.setTimestamps(GregorianCalendar.getInstance())
			.setNumero(numero);
		switch (type.get()) {
			case 1:
				part.setContent(request.getParameter("content"))
					.setTitle(request.getParameter("title"));
				break;
			case 2:
				part.setDocument(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_JPG))
					.setTitle(request.getParameter("title"));
				break;
			case 3:
				part.setContent(StringsUtils.buildYoutubeEmbed(request.getParameter("video")))
					.setTitle(request.getParameter("title"));
				break;
			case 4:
				part.setContent(StringsUtils.buildLinkString(request.getParameter("link")));
				break;
		}
		Long idPart = partService.createOrUpdate(part);
		return new PartResponse().setContent((part.getType() == 3 || part.getType() == 4)? part.getContent() : null).setId(idPart).setCode(1).buildJson();
	}
	
	/**
	 * update a part informations by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String update(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * move a part by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part/move", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String move(HttpServletRequest request) {
		return null;
	}
	
	/**
	 * delete a part by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public String delete(HttpServletRequest request) {
		return null;
	}

}