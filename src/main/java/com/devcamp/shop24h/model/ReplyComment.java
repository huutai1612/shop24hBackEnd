package com.devcamp.shop24h.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "replies")
public class ReplyComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "reply can't be empty")
	@Column(name = "reply", nullable = false)
	private String replies;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "commentId")
	private Comments commentId;

	public ReplyComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReplyComment(int id, @NotEmpty(message = "reply can't be empty") String replies, Comments commentId) {
		super();
		this.id = id;
		this.replies = replies;
		this.commentId = commentId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the replies
	 */
	public String getReplies() {
		return replies;
	}

	/**
	 * @param replies the replies to set
	 */
	public void setReplies(String replies) {
		this.replies = replies;
	}

	/**
	 * @return the commentId
	 */
	public Comments getCommentId() {
		return commentId;
	}

	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(Comments commentId) {
		this.commentId = commentId;
	}

	
}
