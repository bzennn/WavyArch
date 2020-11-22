package xyz.bzennn.wavyarch.form.model;

import javax.validation.constraints.Size;

public class AuthorAndRole {
	
	@Size(min = 1, max = 60, message = "{field.authorName.size}")
	private String author;
	
	@Size(min = 0, max = 20, message = "{field.authorRole.size}")
	private String role;

	public AuthorAndRole(String author, String role) {
		this.author = author;
		this.role = role;
	}

	public String getAuthor() {
		return author;
	}

	public String getRole() {
		return role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorAndRole other = (AuthorAndRole) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuthorRole [author=" + author + ", role=" + role + "]";
	}

}