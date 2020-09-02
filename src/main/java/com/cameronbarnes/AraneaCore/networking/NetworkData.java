package com.cameronbarnes.AraneaCore.networking;

import com.cameronbarnes.AraneaCore.crypto.credentials.UsernamePasswordCredential;
import com.cameronbarnes.AraneaCore.database.DatabasePacket;
import com.cameronbarnes.AraneaCore.database.DatabasePacket.DatabaseResponse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.Key;

import java.security.PublicKey;
import java.util.UUID;

public class NetworkData implements Serializable {
	
	private UUID mUUID;
	private DataType mDataType;
	
	//Data types begin here
	private byte[] byteData = null;
	private String stringData = null;
	private String mProjectName;
	private int intData = 0;
	private PublicKey mPublicKey;
	private SecretKey mSecretKey;
	private Key mKey;
	
	private UsernamePasswordCredential mCredential;
	
	private static final Logger log = LoggerFactory.getLogger(NetworkData.class.getName());
	
	public NetworkData(DataType type, UUID uuid) {
		
		mUUID = uuid;
		mDataType = type;
		
	}
	
	public UUID getUUID() {
		return mUUID;
	}
	
	public void setUUID(UUID uuid) {
		mUUID = uuid;
	}
	
	public DataType getDataType() {
		return mDataType;
	}
	
	public byte[] getByteData() {
		return byteData;
	}
	
	public void setByteData(byte[] byteData) {
		this.byteData = byteData;
	}
	
	public String getStringData() {
		return stringData;
	}
	
	public void setStringData(String stringData) {
		this.stringData = stringData;
	}
	
	public String getProjectName() {
		
		return mProjectName;
	}
	
	public void setProjectName(String projectName) {
		
		mProjectName = projectName;
	}
	
	public int getIntData() {
		return intData;
	}
	
	public void setIntData(int intData) {
		this.intData = intData;
	}
	
	public SecretKey getSecretKey() {
		return mSecretKey;
	}
	
	public void setSecretKey(SecretKey secretKey) {
		mSecretKey = secretKey;
	}
	
	public PublicKey getPublicKey() {
		return mPublicKey;
	}
	
	public void setPublicKey(PublicKey publicKey) {
		mPublicKey = publicKey;
	}
	
	public Key getKey() {
		return mKey;
	}
	
	public void setKey(Key key) {
		mKey = key;
	}
	
	public UsernamePasswordCredential getCredential() {
		return mCredential;
	}
	
	public void setCredential(UsernamePasswordCredential credential) {
		if (mCredential != null) {
			mCredential.clear();
		}
		mCredential = credential;
	}
	
	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static NetworkData RSAKeyRequest(PublicKey localPublicKey) {
		
		NetworkData data = new NetworkData(DataType.RSAKeyRequest, null);
		data.setPublicKey(localPublicKey);
		return data;
		
	}
	
	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static NetworkData RSAKeyResponse(PublicKey localPublicKey) {
		
		NetworkData data = new NetworkData(DataType.RSAKeyResponse, null);
		data.setPublicKey(localPublicKey);
		return data;
		
	}
	
	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static NetworkData invalidKeyError(Key key) {
		NetworkData data = new NetworkData(DataType.InvalidKeyError, null);
		data.setKey(key);
		return data;
	}
	
	@Contract(value = "_-> new", pure = true)
	public static NetworkData newError(@NotNull DatabasePacket.DatabaseResponse type) {
		
		NetworkData data = null;
	
		switch (type) {
			
			case GenericFailure:
			case NoSuchDocument:
			case InvalidPermissions:
			case NoSuchUser:
			case AuthenticationError:
				data = new NetworkData(DataType.DatabaseResponseError, null);
				data.setStringData(type.toString());
				break;
			case Success:
				log.error("Success is not a valid error type");
			default:
				data = new NetworkData(DataType.ErrorError, null);
				break;
		}
		return data;
	
	}
	
	@NotNull
	@Contract(value = "_ -> new", pure = true)
	public static NetworkData issueAESKey(SecretKey AESKey) {
		
		NetworkData data = new NetworkData(DataType.IssueAESKey, null);
		data.setSecretKey(AESKey);
		return data;
		
	}
	
	@NotNull
	@Contract(value = " -> new", pure = true)
	public static NetworkData AESKeyResponse() {
		return new NetworkData(DataType.AESKeyResponse, null);
	}
	
	public byte[] serializeToBytes() {
		
		byte[] bytes = null;
		
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			
			out = new ObjectOutputStream(bOut);
			out.writeObject(this);
			out.flush();
			
			bytes = bOut.toByteArray();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (out != null) out.close();
				bOut.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return bytes;
	
	}
	
	public static NetworkData deserializeFromBytes(byte[] bytes) {
		
		NetworkData data = null;
		
		ByteArrayInputStream bIn = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		try {
			
			in = new ObjectInputStream(bIn);
			data = (NetworkData) in.readObject();
			
		}
		catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (in != null) in.close();
				bIn.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return data;
	
	}
	
	public enum DataType {
		RSAKeyRequest,
		RSAKeyResponse,
		IssueAESKey,
		AESKeyResponse,
		CredentialRequest,
		CredentialResponse,
		EncryptedNetworkDataRSA,
		EncryptedNetworkDataAES,
		DocumentRequest,
		DocumentResponse,
		DocumentUpdateRequest,
		DocumentUpdateResponse,
		
		//Errors
		InvalidKeyError,
		InvalidUUIDError,
		InvalidCredentialError,
		ExpectedEncryptedError,
		DatabaseResponseError,
		ErrorError
	}
	
}
