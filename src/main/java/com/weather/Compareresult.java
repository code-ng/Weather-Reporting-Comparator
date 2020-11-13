package com.weather;

import java.util.Comparator;

/**
 * Compareresult - class providing the comparator logic 
 * @author Nirupam
 *
 */
public class Compareresult implements Comparator<Weather>{

	@Override
	public int compare(Weather o1, Weather o2) {
		if(o1.weather == o2.weather) {
			
			return 0;
		}
		else if(o1.weather > o2.weather) {
			
			return (int)(o1.weather - o2.weather);
			
		}
		else if (o1.weather < o2.weather) {
			return (int)( -o1.weather + o2.weather);
		}

	return -1;
	}
	}


