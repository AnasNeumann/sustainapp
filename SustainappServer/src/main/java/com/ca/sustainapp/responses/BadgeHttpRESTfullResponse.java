package com.ca.sustainapp.responses;

import java.util.List;

import com.ca.sustainapp.entities.BadgeEntity;
import com.ca.sustainapp.pojo.SustainappList;

/**
 * Juste pour essayer
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 *
 */
public class BadgeHttpRESTfullResponse extends HttpRESTfullResponse {
		private static final long serialVersionUID = 1L;
		private List<BadgeEntity> data = new SustainappList<BadgeEntity>();

		/**
		 * @return the data
		 */
		public List<BadgeEntity> getData() {
			return data;
		}
		
		/**
		 * @param data the data to set
		 */
		public BadgeHttpRESTfullResponse setData(List<BadgeEntity> data) {
			this.data = data;
			return this;
		}

}