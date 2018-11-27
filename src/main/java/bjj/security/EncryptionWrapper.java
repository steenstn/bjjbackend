package bjj.security;

public interface EncryptionWrapper {
    boolean checkPassword(String plainText, String hashedPassword);
    String hashPassword(String password);
}
