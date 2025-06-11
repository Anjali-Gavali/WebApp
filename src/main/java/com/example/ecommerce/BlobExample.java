
package com.example.ecommerce;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import java.io.*;

public class BlobExample {
    public static void main(String[] args) {
        String connectStr = "<your-connection-string>";
        String containerName = "images";
        String blobName = "logo.png";
        String downloadFilePath = "downloaded-logo.png";

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectStr).buildClient();
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(blobName);

        blobClient.downloadToFile(downloadFilePath);
        System.out.println("Downloaded blob to " + downloadFilePath);
    }
}
