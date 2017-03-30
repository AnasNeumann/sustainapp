package com.ca.sustainapp.responses;

import com.ca.sustainapp.entities.TopicEntity;

/**
 * Json de réponse light d'un topic
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 30/03/2017
 * @version 1.0
 */
public class LightTopicResponse extends HttpRESTfullResponse {
	private static final long serialVersionUID = 1L;
	private Boolean done;
	private TopicEntity topic;
	
	/**
	 * @return the done
	 */
	public Boolean getDone() {
		return done;
	}
	
	/**
	 * @param done the done to set
	 */
	public LightTopicResponse setDone(Boolean done) {
		this.done = done;
		return this;
	}
	
	/**
	 * @return the topic
	 */
	public TopicEntity getTopic() {
		return topic;
	}
	
	/**
	 * @param topic the topic to set
	 */
	public LightTopicResponse setTopic(TopicEntity topic) {
		this.topic = topic;
		return this;
	}
}