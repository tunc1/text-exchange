package app.service;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;
import java.util.logging.Level;
import app.dto.EncryptedText;
import app.exception.EncryptionException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EncryptionService
{
	private final Logger logger=Logger.getLogger(getClass().getName());
	private final SecureRandom secureRandom=new SecureRandom();
	private final String ALGORITHM="AES";
	private final String TRANSFORMATION="AES/CBC/PKCS5Padding";
	private final String ENCODING="UTF-8";
	@Value("${encryption.salt}")
	private String salt="salt";
	@Value("${encryption.iteration-count}")
	private int iterationCount;
	@Value("${encryption.key-length}")
	private int keyLength;
	private SecretKeyFactory secretKeyFactory;

	public EncryptedText encrypt(String text,String password)
	{
		try
		{
			byte[] iv=new byte[16];
			secureRandom.nextBytes(iv);
			IvParameterSpec ivParameterSpec=new IvParameterSpec(iv);

			SecretKeySpec secretKeySpec=getSecretKeySpec(password);

			Cipher cipher=Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
			byte[] encryptedByte=cipher.doFinal(text.getBytes(ENCODING));

			String encryptedText=Base64.getEncoder().encodeToString(encryptedByte);
			String vectorString=Base64.getEncoder().encodeToString(iv);

			return new EncryptedText(encryptedText,vectorString);
		}
		catch(Exception e)
		{
			logger.log(Level.SEVERE,"encrypt",e);
			throw new EncryptionException();
		}
	}
	public String decrypt(String encryptedText,String vector,String password)
	{
		try
		{
			byte[] iv=Base64.getDecoder().decode(vector);
			IvParameterSpec ivParameterSpec=new IvParameterSpec(iv);

			SecretKeySpec secretKeySpec=getSecretKeySpec(password);

			Cipher cipher=Cipher.getInstance(TRANSFORMATION);
			cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
			byte[] decryptedByte=cipher.doFinal(Base64.getDecoder().decode(encryptedText));

			return new String(decryptedByte,ENCODING);
		}
		catch(Exception e)
		{
			logger.log(Level.SEVERE,"decrypt",e);
			throw new EncryptionException();
		}
	}
	private SecretKeyFactory getSecretKeyFactory() throws NoSuchAlgorithmException
	{
		if(secretKeyFactory==null)
			secretKeyFactory=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		return secretKeyFactory;
	}
	private SecretKeySpec getSecretKeySpec(String password) throws NoSuchAlgorithmException,InvalidKeySpecException
	{
		KeySpec keySpec=new PBEKeySpec(password.toCharArray(),salt.getBytes(),iterationCount,keyLength);
		SecretKey secretKey=getSecretKeyFactory().generateSecret(keySpec);
		return new SecretKeySpec(secretKey.getEncoded(),ALGORITHM);
	}
}