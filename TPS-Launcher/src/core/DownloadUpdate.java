package core;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URL;
import java.security.MessageDigest;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */
public class DownloadUpdate {

    private byte[] response;
    private String link;
    private String fileName;

    public DownloadUpdate(String link, String fileName){
        this.link = link;
        this.fileName = fileName;
    }

    public void download() throws IOException{
        URL downloadLink = new URL(link);

        InputStream in = new BufferedInputStream(downloadLink.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int n = 0;
        while (-1 != (n = in.read(buffer))){
            out.write(buffer, 0, n);
        }
        out.close();
        in.close();
        response = out.toByteArray();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(response);
        fos.close();
    }

    public boolean checkDownloadedFile(String hash){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(response);
            byte[] digest = md.digest();
            String digestInHex = DatatypeConverter.printHexBinary(digest).toUpperCase();
            if(digestInHex.equals(hash)){
                return true;
            }
            else {
                System.out.println("hash not match");
                return false;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
}
