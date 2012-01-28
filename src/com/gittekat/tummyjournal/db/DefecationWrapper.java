package com.gittekat.tummyjournal.db;

import java.util.Calendar;

/**
 * Wrapper for defecation database entry. Note: To avoid database performance
 * issues, do not use getters/setters here, unless necessary!
 * 
 * @author hoshmeister
 * 
 */
public class DefecationWrapper {
	public long id;
	public String type;
	public String details;
	public Calendar datetime;
}
