package com.wff.file;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

public class FileUploader {
    public static void main(String[] args) throws IOException, MyException {
        ClientGlobal.init("F:\\java\\daniusp\\daniu-service\\daniu-file-service\\src\\main\\resources\\fsfd_client.conf");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        String[] strings = storageClient.upload_file("D:\\upload_file\\3.jpg", "jpg", null);
        for (String info : strings) {
            System.out.println(info);
        }
    }
}
