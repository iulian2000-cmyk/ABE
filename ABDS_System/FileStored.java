package ABDS_System;

import Users.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.logging.Logger;


public class FileStored {
    private static final Logger logger = Logger.getLogger(FileStored.class.getName());
    private String pathToFile;
    private User ownerFile;
    private List<BlockFile> blocksFile;
    private static SecretKeySpec secretKey;

    FileStored(String pathToFile, User ownerFile, String pathSavedFile) {
        setOwnerFile(ownerFile);
        setPathToFile(pathToFile);
        uploadFile(pathSavedFile);
    }


    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public User getOwnerFile() {
        return ownerFile;
    }

    public void setOwnerFile(User ownerFile) {
        this.ownerFile = ownerFile;
    }

    private void uploadFile(String pathSavedFile) {
        try (
                InputStream inputStream = new FileInputStream(pathToFile);
                OutputStream outputStream = new FileOutputStream(pathSavedFile)
        ) {
            int byteRead;
            blocksFile = new ArrayList<>();
            while ((byteRead = inputStream.read()) != -1) {
                // Here the encrypted block by block take place .
                BlockFile blockFile = new BlockFile(EncryptBlock(String.valueOf(byteRead), pathToFile));
                outputStream.write(blockFile.getEncryptedData().getBytes());
                blocksFile.add(blockFile);
            }
        } catch (Exception e) {
            logger.info("Error during uploading file:" + e);
        }
    }

    public void downloadFile(String uploadLocation) throws Exception {
        OutputStream outputStream = new FileOutputStream(uploadLocation);
        for (BlockFile blockFile : blocksFile) {
            outputStream.write(Integer.parseInt(Objects.requireNonNull(decryptBlock(blockFile.getEncryptedData(), pathToFile))));
        }
        outputStream.close();
    }

    public static String EncryptBlock(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            logger.info("Error during encryption block: " + e);
        }
        return null;
    }

    public static void setKey(String myKey) {
        MessageDigest sha;
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            logger.info("Error during setting the key:" + e);
        }
    }

    public static String decryptBlock(String strToDecrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            logger.info("Error during decryption of the block" + e);
        }
        return null;
    }
}
