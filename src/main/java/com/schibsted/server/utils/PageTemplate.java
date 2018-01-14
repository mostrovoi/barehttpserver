package com.schibsted.server.utils;

/**
 * TODO: JAVADOC
 * @author operezdo
 *
 */
public class PageTemplate {

	private String title;
	private String username;
    private String error;
    
    public PageTemplate() {    	
    }

    public PageTemplate(String title) {
    	this.title = title;
    }
    
    public PageTemplate(String title, String username) {
    	this(title);
    	this.username = username;
    }
    
    public PageTemplate(String title, String username, String errorDescription) {
    	this(title,username);
    	this.error = errorDescription;
    }
    
    public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
