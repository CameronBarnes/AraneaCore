package com.cameronbarnes.AraneaCore.database;

import com.cameronbarnes.AraneaCore.crypto.credentials.UsernamePasswordCredential;
import com.sun.istack.internal.NotNull;
import org.bson.Document;
import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class DatabasePacket {
	
	private final UUID mUUID;
	
	private final boolean mIsRequest;
	private final PacketType mPacketType;
	private final DatabaseResponse mResponse;
	
	private String mProjectName;
	
	private UsernamePasswordCredential mCredential;
	private Document mDocument;
	
	private DatabasePacket(String projectName, boolean isRequest, PacketType type, DatabaseResponse response, UUID uuid) {
		mUUID = uuid;
		mIsRequest = isRequest;
		mPacketType = type;
		mResponse = response;
		mProjectName = projectName;
	}
	
	@NotNull
	@Contract(value = "_, _, _ -> new", pure = true)
	public static DatabasePacket newRequest(String projectName, PacketType type, UUID uuid) {
		return new DatabasePacket(projectName, true, type, null, uuid);
	}
	
	@NotNull
	@Contract(value = "_, _, _, _ -> new", pure = true)
	public static DatabasePacket newResponse(String projectName, PacketType type, DatabaseResponse response, UUID uuid) {
		return new DatabasePacket(projectName, false, type, response, uuid);
	}
	
	@NotNull
	@Contract(value = "_, _, _ -> new")
	public static DatabasePacket credentialVerificationRequest(String projectName, UsernamePasswordCredential credential, UUID uuid) {
		
		DatabasePacket packet = newRequest(projectName, PacketType.ProcessCredentials, uuid);
		packet.mCredential = credential;
		return packet;
		
	}
	
	public String getProjectName() {
		return mProjectName;
	}
	
	public Document getDocument() {
		return mDocument;
	}
	
	public void setDocument(Document document) {
		mDocument = document;
	}
	
	public UsernamePasswordCredential getCredential() {
		return mCredential;
	}
	
	public PacketType getPacketType() {
		return mPacketType;
	}
	
	public DatabaseResponse getResponse() {
		return mResponse;
	}
	
	public boolean isRequest() {
		return mIsRequest;
	}
	
	public enum PacketType {
		AddUser,
		ProcessCredentials,
		RequestUserData,
		RequestDocument,
		RequestIndex
	}
	
	public enum DatabaseResponse {
		Success,
		GenericFailure,
		NoSuchDocument,
		InvalidPermissions,
		NoSuchUser,
		AuthenticationError
	}
	
	public static class PacketResponseTypeMismatchException extends Exception {
		
		public PacketResponseTypeMismatchException(boolean expectedRequest) {
			super(expectedRequest ? "Expected request packet, received response" :
					      "Expected response packet, received request");
		}
		
	}
	
}
