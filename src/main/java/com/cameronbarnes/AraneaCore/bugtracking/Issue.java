package com.cameronbarnes.AraneaCore.bugtracking;

import com.cameronbarnes.AraneaCore.communication.Comment;
import com.cameronbarnes.AraneaCore.util.IDManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Issue {
	
	private static Logger log = LogManager.getLogger(Issue.class.getName());
	
	public static final String[] mTableCategories = new String[]{"issue.id",
																		"issue.severity",
																		"issue.type",
																		"issue.status",
																		"issue.createdDate",
																		"issue.updatedDate",
																		"issue.assignedDev",
																		"issue.category",
																		"issue.summary",
																		"issue.isPrivate"
	};
	
	private int ID;
	private int mAssignedDeveloperID;
	
	private Type mType;
	private Severity mSeverity;
	private Status mStatus;
	private ArrayList<String> mCategory;
	
	private boolean mPrivate = false;
	
	private LocalDateTime mCreated;
	private LocalDateTime mUpdated;
	
	private String mSummary;
	
	private ArrayList<Comment> mComments;
	
	public Issue(int id, int devID, Type type, Severity severity, Status status, ArrayList<String> category, LocalDateTime created, LocalDateTime updated, String summary, ArrayList<Comment> comments, boolean aPrivate) {
		
		ID = id;
		mAssignedDeveloperID = devID;
		mType = type;
		mSeverity = severity;
		mStatus = status;
		mPrivate = aPrivate;
		mCreated = created;
		mUpdated = updated;
		mSummary = summary;
		mComments = comments;
		mCategory = category;
		
	}
	
	public static Issue createIssue(Type type, Severity severity, ArrayList<String> category, String summary, boolean aPrivate) {
	
		return new Issue(IDManager.getNewID(), 0, type, severity, Status.New, category, LocalDateTime.now(), LocalDateTime.now(), summary, new ArrayList<Comment>(), aPrivate);
	
	}
	
	public int getID() {
		return ID;
	}
	
	public int getAssignedDeveloperID() {
		return mAssignedDeveloperID;
	}
	
	public void setAssignedDeveloperID(int assignedDeveloperID) {
		mAssignedDeveloperID = assignedDeveloperID;
		if (assignedDeveloperID != 0) {
			mStatus = Status.Assigned;
		}
	}
	
	public Type getType() {
		return mType;
	}
	
	public void setType(Type type) {
		mType = type;
	}
	
	public Severity getSeverity() {
		return mSeverity;
	}
	
	public void setSeverity(Severity severity) {
		mSeverity = severity;
	}
	
	public Status getStatus() {
		return mStatus;
	}
	
	public void setStatus(Status status) {
		if (status == Status.Assigned && mAssignedDeveloperID == 0) {
			
			log.error("Setting status to assigned but no developer has been assigned");
			
		}
		mStatus = status;
	}
	
	public ArrayList<String> getCategory() {
		return (ArrayList<String>) mCategory.clone();
	}
	
	public void setCategory(ArrayList<String> category) {
		mCategory = category;
	}
	
	public void addCategory(String s) {
		mCategory.add(s);
	}
	
	public void removeCategory(String s) {
		mCategory.remove(s);
	}
	
	public boolean isPrivate() {
		return mPrivate;
	}
	
	public void setPrivate(boolean aPrivate) {
		mPrivate = aPrivate;
	}
	
	public LocalDateTime getCreated() {
		return mCreated;
	}
	
	public LocalDateTime getUpdated() {
		return mUpdated;
	}
	
	public void setUpdated() {
		mUpdated = LocalDateTime.now();
	}
	
	public String getSummary() {
		return mSummary;
	}
	
	public void setSummary(String summary) {
		mSummary = summary;
	}
	
	public ArrayList<Comment> getComments() {
		return (ArrayList<Comment>) mComments.clone();
	}
	
	public void setComments(ArrayList<Comment> comments) {
		mComments = comments;
	}
	
	public void addComment(Comment comment) {
		mComments.add(comment);
	}
	
	public void removeComment(Comment comment) {
		mComments.remove(comment);
	}
	
	public enum Type {
		Bug,
		Feature,
		Enhancement
	}
	
	public enum Severity {
		Minor,
		Serious,
		Critical
	}
	
	public enum Status {
		New,
		Acknowledged,
		Assigned,
		Resolved
	}
	
}
