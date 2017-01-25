package com.ca.sustainapp.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Object to make search by crieteria
 * 
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 25/01/2017
 * @version 1.0
 */
public class SearchResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long startIndex = 0L;
	private Long maxResults = 10L;
	private Long totalResults = 0L;
	private List<? extends T> results;

	/**
	 * Default constructor
	 */
	public SearchResult() {
	}

	/**
	 * Default constructor
	 * @param startIndex
	 * @param maxResults
	 */
	public SearchResult(int startIndex, int maxResults) {
		this(Long.valueOf(startIndex), Long.valueOf(maxResults));
	}

	/**
	 * Default constructor
	 * @param startIndex
	 * @param maxResults
	 */
	public SearchResult(Long startIndex, Long maxResults) {
		this.startIndex = startIndex;
		this.maxResults = maxResults;
	}

	/**
	 * Default constructor 
	 * @param results
	 */
	public SearchResult(List<? extends T> results) {
		if (null != results) {
			this.startIndex = 0L;
			this.maxResults = Long.valueOf(results.size());
			this.totalResults = Long.valueOf(results.size());
			this.results = results;
		}
	}

	/**
	 * @return the startIndex
	 */
	public Long getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex
	 *            the startIndex to set
	 */
	public SearchResult<T> setStartIndex(Long startIndex) {
		this.startIndex = startIndex;
		return this;
	}

	/**
	 * @return the maxResults
	 */
	public Long getMaxResults() {
		return maxResults;
	}

	/**
	 * @param maxResults
	 *            the maxResults to set
	 */
	public SearchResult<T> setMaxResults(Long maxResults) {
		this.maxResults = maxResults;
		return this;
	}

	/**
	 * @return the totalResults
	 */
	public Long getTotalResults() {
		return totalResults;
	}

	/**
	 * @param totalResults
	 *            the totalResults to set
	 */
	public SearchResult<T> setTotalResults(Long totalResults) {
		this.totalResults = totalResults;
		return this;
	}

	/**
	 * @return the results
	 */
	public List<? extends T> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public SearchResult<T> setResults(List<? extends T> results) {
		this.results = results;
		return this;
	}

}
