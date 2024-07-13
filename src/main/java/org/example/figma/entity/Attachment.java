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

    public byte[] compressImage(MultipartFile photo) throws IOException {
        float quality=0.5f;
        // Чтение изображения из MultipartFile
        BufferedImage image = ImageIO.read(photo.getInputStream());

        // Создание выходного потока для сжатого изображения
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Получение ImageWriter для JPEG
        ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(byteArrayOutputStream);
        jpgWriter.setOutput(ios);

        // Настройка параметров сжатия
        ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        if (jpgWriteParam.canWriteCompressed()) {
            jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpgWriteParam.setCompressionQuality(quality); // Установка качества сжатия
        }

        // Запись изображения с заданными параметрами
        jpgWriter.write(null, new IIOImage(image, null, null), jpgWriteParam);

        // Закрытие потоков
        ios.close();
        jpgWriter.dispose();

        // Преобразование выходного потока в массив байт
        return byteArrayOutputStream.toByteArray();
    }

}