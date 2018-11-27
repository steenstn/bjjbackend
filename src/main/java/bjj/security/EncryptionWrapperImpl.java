package bjj.security;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class EncryptionWrapperImpl implements EncryptionWrapper {

    public boolean checkPassword(String plainText, String hashedPassword) {
        return BCrypt.checkpw(plainText, hashedPassword);
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
