package core;

import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableIntegerArray;
import javafx.concurrent.Task;

import java.io.*;
import java.net.URL;

/**
 * Created by Ahmed Ali on 23/02/2016.
 */
public class DownloadUpdate {

    private String fileName = "";
    private String strLink;
    private URL downloadLink;
    private int fileSize;
    private int downloadedSize;

    public DownloadUpdate(String link, int size){
        strLink = link;
        fileSize = size;
        downloadedSize = 0;
    }

    public void download() throws IOException {
        new Thread(
                new Task() {
                    @Override
                    protected Object call() throws Exception {

                        downloadLink = new URL(strLink);

                        InputStream in = new BufferedInputStream(downloadLink.openStream());
                        ByteArrayOutputStream out = new ByteArrayOutputStream();

                        byte[] buffer = new byte[1024];
                        int n = 0;
                        while (-1 != (n = in.read(buffer))){
                            out.write(buffer, 0, n);
                            downloadedSize = out.size();
                        }
                        out.close();
                        in.close();
                        byte[] response = out.toByteArray();

                        FileOutputStream fos = new FileOutputStream(fileName);
                        fos.write(response);
                        fos.close();

                        return null;
                    }
                }
        ).start();
    }

    public int getDownloadedSize() {
        return downloadedSize;
    }

    public int getFileSize() {
        return fileSize;
    }
}
