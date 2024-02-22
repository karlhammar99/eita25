package projekt2.Individuals;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public abstract class Individual {

    String role;
    String name;
    String division;


    public Individual() {


    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getDivision(){
        return division;
    }

    public void setRole(String role){
        this.role = role;
    }
    

    public boolean isRole(String r) {

        if (r.equals(role)) {
            return true;
        } else {
            return false;
        }
    }

    public void setName(String s) {
        name = s;
    }




}
