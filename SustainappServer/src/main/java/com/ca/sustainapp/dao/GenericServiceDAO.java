package com.ca.sustainapp.dao;

import com.ca.sustainapp.pojo.SearchResult;

/**
 * generic mother class for data access object service
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2017
 * @version 1.0
 */
public class GenericServiceDAO {
	
	/**
	 * SearchReasult Object Init
	 * @param startIndex
	 * @param maxResults
	 * @return SearchResult<T>
	 */
	public static <T> SearchResult<T> initSearchResult(Long startIndex, Long maxResults) {
		SearchResult<T> rtn = new SearchResult<T>();
		rtn.setTotalResults(0L);
		rtn.setStartIndex(startIndex);
		rtn.setMaxResults(maxResults);
		return rtn;
	}
	
}