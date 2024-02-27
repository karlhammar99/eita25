package projekt2.Individuals;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import projekt2.IndividualPermissions.Role;

public abstract class Individual {

    Role role;
    String name;
    String division;


    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getDivision(){
        return division;
    }


}
