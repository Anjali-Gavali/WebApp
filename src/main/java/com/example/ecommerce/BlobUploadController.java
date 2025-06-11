package com.example.ecommerce;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;

@RestController
@RequestMapping("/api/blob")
public class BlobUploadController {

    private final String connectionString = "sp=r&st=2025-06-04T05:34:57Z&se=2025-06-04T13:34:57Z&spr=https&sv=2024-11-04&sr=c&sig=ttaT%2FDor9fuGJGdW0xpjrOy9nQgvoQhWFD2Kjc87Krk%3D"; // Secure via config

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Create a blob service client
            BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();

            // Get a reference to the container (must exist)
            BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("images");

            // Get a reference to the blob
            BlobClient blobClient = containerClient.getBlobClient(file.getOriginalFilename());

            // Upload the file
            InputStream dataStream = file.getInputStream();
            blobClient.upload(dataStream, file.getSize(), true);

            String blobUrl = blobClient.getBlobUrl();
            return ResponseEntity.ok("Uploaded to: " + blobUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}
