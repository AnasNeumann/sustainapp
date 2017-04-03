package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.PartEntity;
import com.ca.sustainapp.entities.TopicEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Json de réponse pour l'affichage complet d'un topic
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/04/2017
 * @version 1.0
 */
public class TopicResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private TopicEntity topic;
	private Boolean isOwner;
	private List<PartEntity> parts = new SustainappList<PartEntity>();
	
	/**
	 * @return the topic
	 */
	public TopicEntity getTopic() {
		return topic;
	}
	
	/**
	 * @param topic the topic to set
	 */
	public TopicResponse setTopic(TopicEntity topic) {
		this.topic = topic;
		return this;
	}
	
	/**
	 * @return the isOwner
	 */
	public Boolean getIsOwner() {
		return isOwner;
	}
	
	/**
	 * @param isOwner the isOwner to set
	 */
	public TopicResponse setIsOwner(Boolean isOwner) {
		this.isOwner = isOwner;
		return this;
	}
	
	/**
	 * @return the parts
	 */
	public List<PartEntity> getParts() {
		return parts;
	}
	
	/**
	 * @param parts the parts to set
	 */
	public TopicResponse setParts(List<PartEntity> parts) {
		this.parts = parts;
		return this;
	}
}