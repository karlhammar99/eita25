package projekt2;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import projekt2.Individuals.Individual;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Server implements Runnable {
  private ServerSocket serverSocket = null;
  private static int numConnectedClients = 0;
  IndividualPermissions ip;
  
  public Server(ServerSocket ss) throws IOException {
    serverSocket = ss;
    newListener();
    ip = new IndividualPermissions();
  }

  public void run() {
    Individual user = null;
    try {
      SSLSocket socket=(SSLSocket)serverSocket.accept();
      newListener();
      SSLSession session = socket.getSession();
      Certificate[] cert = session.getPeerCertificates();
      String subject = ((X509Certificate) cert[0]).getSubjectX500Principal().getName();
      String issuer = ((X509Certificate) cert[0]).getIssuerX500Principal().getName();
      BigInteger serialNumber = ((X509Certificate) cert[0]).getSerialNumber();
      numConnectedClients++;


      System.out.println(subject);
      String [] s = subject.split("=",6);
      String [] s1 = s[1].split(",",6);


      System.out.println(s1[0] + "SUBJECT");
    subject=s1[0];
      
      String users = "projekt2/testfiles/users.txt";
      ip.loadIndividuals(users);
      for(Individual other : ip.getIndividuals()){
        if(other.getName().equals(subject)){
            user = other;
            break;
        }
      }

      //Kommentera tillbaka detta!
      if(user == null){
        socket.close();
        System.out.println("User not found. Closing.");
        return;
      }

      //user = ip.getIndividuals().get(0); //Alice
      //user = ip.getIndividuals().get(1); //Bob
      //user = ip.getIndividuals().get(2); //Syster1
     // user = ip.getIndividuals().get(7); //Government
      //user = ip.getIndividuals().get(4); //Kurt
      //user = ip.getIndividuals().get(6); //Rasmus
      


      //Individual finns i users
      System.out.println("client connected");
      System.out.println("client name (cert subject DN field): " + subject);
      System.out.println("client issuer (cert issuer DN field): " + issuer);
      System.out.println("certificate serial number: " + serialNumber);
      System.out.println(numConnectedClients + " concurrent connection(s)\n");

      PrintWriter out = null;
      BufferedReader in = null;
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      //out.println("Welcome " + user.getRole() + " " + user.getName() + ".");
      //out.flush();

      String clientMsg = null;
      while ((clientMsg = in.readLine()) != null) {
        String answer = ip.answer(user, clientMsg);
        System.out.println("received '" + clientMsg + "' from client");
        System.out.print("sending '" + answer + "' to client...");
        out.println(answer);
        out.flush();
        System.out.println("done\n");
      }
      in.close();
      out.close();
      socket.close();
      numConnectedClients--;
      System.out.println("client disconnected");
      System.out.println(numConnectedClients + " concurrent connection(s)\n");
    } catch (IOException e) {
      System.out.println("Client died: " + e.getMessage());
      e.printStackTrace();
      return;
    }
  }
  
  private void newListener() { (new Thread(this)).start(); } // calls run()
  public static void main(String args[]) {
    System.out.println("\nServer Started\n");
    int port = -1;
    if (args.length >= 1) {
      port = Integer.parseInt(args[0]);
    }
    String type = "TLSv1.2";
    try {
      ServerSocketFactory ssf = getServerSocketFactory(type);
      ServerSocket ss = ssf.createServerSocket(port, 0, InetAddress.getByName(null));
      ((SSLServerSocket)ss).setNeedClientAuth(true); // enables client authentication
      new Server(ss);
    } catch (IOException e) {
      System.out.println("Unable to start Server: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static ServerSocketFactory getServerSocketFactory(String type) {
    if (type.equals("TLSv1.2")) {
      SSLServerSocketFactory ssf = null;
      try { // set up key manager to perform server authentication
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        KeyStore ks = KeyStore.getInstance("JKS");
        KeyStore ts = KeyStore.getInstance("JKS");
        char[] password = "password".toCharArray();
        // keystore password (storepass)
        ks.load(new FileInputStream("serverkeystore"), password);  
        // truststore password (storepass)
        ts.load(new FileInputStream("servertruststore"), password); 
        kmf.init(ks, password); // certificate password (keypass)
        tmf.init(ts);  // possible to use keystore as truststore here
        ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        ssf = ctx.getServerSocketFactory();
        return ssf;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      return ServerSocketFactory.getDefault();
    }
    return null;
  }






  
}
