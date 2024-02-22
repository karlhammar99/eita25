package projekt2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class Client {
    public static void main(String[] args) throws Exception {
        String host = null;
        int port = -1;

        for (int i = 0; i < args.length; i++) {
            System.out.println("args" + i + " =" + args[i]);
        }

      try { /* get input parameters */
        if (args.length == 1) port = Integer.parseInt(args[0]);
        else {
          host = args[0];
          port = Integer.parseInt(args[1]);
        }
      } catch (IllegalArgumentException e) {
        System.out.println("USAGE: java client [host] port");
        System.exit(-1);
      }


  
      try {
        SSLSocketFactory factory = null;
        try {
          char[] password = "password".toCharArray();
          KeyStore ks = KeyStore.getInstance("JKS");
          KeyStore ts = KeyStore.getInstance("JKS");
          KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
          TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
          SSLContext ctx = SSLContext.getInstance("TLSv1.2");
          // keystore password (storepass)
          ks.load(new FileInputStream("clientkeystore"), password);  
          // truststore password (storepass);
          ts.load(new FileInputStream("clienttruststore"), password); 
          kmf.init(ks, password); // user password (keypass)
          tmf.init(ts); // keystore can be used as truststore here
          ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
          factory = ctx.getSocketFactory();
        } catch (Exception e) {
          throw new IOException(e.getMessage());
        }
        SSLSocket socket = (SSLSocket)factory.createSocket(host, port);
        System.out.println("\nsocket before handshake:\n" + socket + "\n");
  
        /*
         * send http request
         *
         * See SSLSocketClient.java for more information about why
         * there is a forced handshake here when using PrintWriters.
         */
  
        socket.startHandshake();
        SSLSession session = socket.getSession();
        Certificate[] cert = session.getPeerCertificates();
        String subject = ((X509Certificate) cert[0]).getSubjectX500Principal().getName();
        String issuer = ((X509Certificate) cert[0]).getIssuerX500Principal().getName();
        BigInteger serialNumber = ((X509Certificate) cert[0]).getSerialNumber();
        System.out.println("certificate name (subject DN field) on certificate received from server:\n" + subject + "\n");
        System.out.println("certificate issuer (issuer DN field) on certificate received from server:\n" + issuer + "\n");
        System.out.println("certificate serial number: " + serialNumber);
        System.out.println("socket after handshake:\n" + socket + "\n");
        System.out.println("secure connection established\n\n");
  
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msg;
        for (;;) {
          System.out.print(">");
          msg = read.readLine();
          if (msg.equalsIgnoreCase("quit")) {
            break;
          }
          System.out.print("sending '" + msg + "' to server...");
          out.println(msg);
          out.flush();
          System.out.println("done");
          System.out.println("received '" + in.readLine() + "' from server\n");
        }
        in.close();
        out.close();
        read.close();
        socket.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
}