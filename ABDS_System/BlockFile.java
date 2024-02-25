package ABDS_System;

public class BlockFile {

    private String encryptedData;


    BlockFile(String encryptedData) {
        setEncryptedData(encryptedData);
    }

    public void setIdBlock(int idBlock) {
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}
