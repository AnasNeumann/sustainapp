package com.ca.sustainapp.controllers;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ca.sustainapp.boot.SustainappConstantes;
import com.ca.sustainapp.criteria.PartCriteria;
import com.ca.sustainapp.entities.PartEntity;
import com.ca.sustainapp.entities.TopicEntity;
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
    public ResponseEntity<String> create(HttpServletRequest request) {
		TopicEntity topic = super.getTopicIfOwner(request);
		if(null == topic || !validator.validate(request).isEmpty()){
			return super.refuse(validator.validate(request));
		}
		List<PartEntity> allParts = getService.cascadeGet(new PartCriteria().setTopicId(topic.getId()));
		Integer numero = (null != allParts)? allParts.size() : 0;
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
				part.setDocument(FilesUtils.compressImage(decodeBase64(request.getParameter("file")), FilesUtils.FORMAT_PNG))
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
		return super.success(new PartResponse().setContent((part.getType() == 3 || part.getType() == 4)? part.getContent() : null).setId(idPart));
	}
	
	/**
	 * update a part informations by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part/update", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> update(HttpServletRequest request) {
		PartEntity part = super.getPartIfOwner(request);
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		if(null == part || isEmpty(content)){
			return super.refuse();
		}
		part.setContent(content);
		if(part.getType().equals(1)){
			part.setTitle(title);
		}
		partService.createOrUpdate(part);
		return super.success();
	}
	
	/**
	 * move a part by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part/move", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> move(HttpServletRequest request) {
		PartEntity part = super.getPartIfOwner(request);
		Boolean sens = new Boolean(request.getParameter("sens"));
		if(null == part){
			return super.refuse();
		}
		int i = 1;
		if(!sens){
			i = -1;
		}
		List<PartEntity> parts = getService.cascadeGet(new PartCriteria().setTopicId(part.getTopicId()).setNumero(part.getNumero()-i));
		for(PartEntity p : parts){
			partService.createOrUpdate(p.setNumero(p.getNumero()+i));
		}
		partService.createOrUpdate(part.setNumero(part.getNumero()-i));
		return super.success();
	}
	
	/**
	 * delete a part by id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/part/delete", method = RequestMethod.POST, produces = SustainappConstantes.MIME_JSON)
    public ResponseEntity<String> delete(HttpServletRequest request) {
		PartEntity part = super.getPartIfOwner(request);
		if(null == part){
			return super.refuse();
		}
		List<PartEntity> parts = getService.cascadeGet(new PartCriteria().setTopicId(part.getTopicId()));
		for(PartEntity p : parts){
			if(p.getNumero() > part.getNumero()){
				partService.createOrUpdate(p.setNumero(p.getNumero()-1));
			}
		}
		deleteService.cascadeDelete(part);
		return super.success();
	}

}