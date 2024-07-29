package org.example.figma.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "attachment")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    private byte[] fullImage;
    private byte[] pressedImage;

    public void compressImage() throws IOException {
        float quality=0.5f;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fullImage);
        BufferedImage image = ImageIO.read(inputStream);

        // Create a ByteArrayOutputStream for the compressed image
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Get an ImageWriter for JPEG format
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpeg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(byteArrayOutputStream);
        jpgWriter.setOutput(ios);

        // Set compression parameters
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        if (jpgWriteParam.canWriteCompressed()) {
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(quality); // Set the compression quality
        }

        // Write the image with the specified parameters
        jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);

        // Close streams
        ios.close();
        jpgWriter.dispose();

        // Convert the ByteArrayOutputStream to a byte array
        pressedImage= byteArrayOutputStream.toByteArray();
    }
}