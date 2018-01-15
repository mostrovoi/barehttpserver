package com.schibsted.server.view.dto;

/**
 * TODO: JAVADOC
 * @author operezdo
 *
 */
public class PageResponseDTO {

	private String title;
	private String username;
    private String error;
    
    public PageResponseDTO() {    	
    }

    public PageResponseDTO(String title) {
    	this.title = title;
    }
    
    public PageResponseDTO(String title, String username) {
    	this(title);
    	this.username = username;
    }
    
    public PageResponseDTO(String title, String username, String errorDescription) {
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
