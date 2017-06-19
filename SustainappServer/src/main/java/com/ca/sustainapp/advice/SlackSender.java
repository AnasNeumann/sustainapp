package com.ca.sustainapp.advice;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

/**
 * Bean singleton pour les interractions avec Slack.
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 19/06/2017
 * @version 1.0
 */
@Component
public class SlackSender {
	private static final Logger LOGGER = LogManager.getLogger(SlackSender.class);
	private static final String SLACK_ERROR_LOG_MSG = "Erreur à l'envoie du slack...";
	private String slackAuthToken;
	private String slackChannel;

	/**
	 * Envoyer un message de prod.
	 * @param message
	 */
	public void sendProdMessage(String message) {
		sendMessageQuietly(slackAuthToken, slackChannel, message);
	}

	/**
	 * Envoyer un message sur slack
	 * @param token
	 * @param channel
	 * @param message
	 */
	public static void sendMessageQuietly(String token, String channel, String message) {
		if (isBlank(token) || isBlank(channel)) {
			LOGGER.warn("Token or channel is blank : nothing to do");
			return;
		}
		try {
			SlackSession session = SlackSessionFactory.createWebSocketSlackSession(token);
			session.connect();
			session.sendMessage(session.findChannelByName(channel), message);
			session.disconnect();
		} catch (Exception e) {
			LOGGER.error(SLACK_ERROR_LOG_MSG, e);
		}
	}

	/**
	 * @return the slackAuthToken
	 */
	public String getSlackAuthToken() {
		return slackAuthToken;
	}

	/**
	 * @param slackAuthToken
	 *            the slackAuthToken to set
	 */
	public void setSlackAuthToken(String slackAuthToken) {
		this.slackAuthToken = slackAuthToken;
	}

	/**
	 * @return the slackChannel
	 */
	public String getSlackChannel() {
		return slackChannel;
	}

	/**
	 * @param slackChannel
	 *            the slackChannel to set
	 */
	public void setSlackChannel(String slackChannel) {
		this.slackChannel = slackChannel;
	}
}

