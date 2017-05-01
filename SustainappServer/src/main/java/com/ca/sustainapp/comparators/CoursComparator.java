package com.ca.sustainapp.comparators;

import java.util.Comparator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ca.sustainapp.entities.CourseEntity;
import com.ca.sustainapp.entities.RankCourseEntity;

/**
 * Classe de comparaison de deux cours selon :
 * 1/ La meilleure moyenne
 * 2/ Le nombre de vote
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 28/04/2017
 * @version 1.0
 */
@Component
@Scope("singleton")
public class CoursComparator implements Comparator<CourseEntity>  {
	
	/**
	 * Redifinition de la méthode de comparaison
	 * @param coursOne
	 * @param coursTwo
	 * @return
	 */
	@Override
	public int compare(CourseEntity coursOne, CourseEntity coursTwo) {		
		if(calculateAverageRank(coursOne) > calculateAverageRank(coursTwo)){
			return -1;
		}else if(calculateAverageRank(coursOne) < calculateAverageRank(coursTwo)){
			return 1;
		}else{
			if(coursOne.getListRank().size() > coursTwo.getListRank().size()){
				return -1;
			}else if(coursOne.getListRank().size() < coursTwo.getListRank().size()){
				return 1;
			}else{
				return 0;
			}
		}	
	}
	
	/**
	 * Calculate average rank of a course
	 * @param cours
	 * @return
	 */
	private Float calculateAverageRank(CourseEntity course){
		int totalResult = 0;
		int somme = 0;
		for(RankCourseEntity rank : course.getListRank()){
			somme += rank.getScore();
			totalResult++;
		}
		if(totalResult > 0){
			return new Float(somme/totalResult);
		}
		return 0F;
	}
}
