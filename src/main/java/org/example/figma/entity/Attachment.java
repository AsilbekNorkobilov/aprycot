package org.example.figma.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
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

    // Метод для сжатия изображения
    public byte[] compressImage(MultipartFile photo) throws IOException {
        float quality=0.5f;
        // Чтение изображения из MultipartFile
        BufferedImage image = ImageIO.read(photo.getInputStream());

        // Создание выходного потока для сжатого изображения
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Получение ImageWriter для JPEG
        ImageIO.write(compressToJpeg(image, quality), "jpeg", byteArrayOutputStream);

        // Преобразование выходного потока в массив байт
        byte[] compressedImageBytes = byteArrayOutputStream.toByteArray();

        // Закрытие потока
        byteArrayOutputStream.close();

        return compressedImageBytes;
    }

    // Вспомогательный метод для сжатия изображения до формата JPEG с заданным качеством
    private BufferedImage compressToJpeg(BufferedImage image, float quality) throws IOException {
        // Создание выходного потока для сжатого изображения
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Запись изображения в выходной поток с заданным качеством
        ImageIO.write(image, "jpeg", byteArrayOutputStream);

        // Чтение сжатого изображения из выходного потока
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        BufferedImage compressedImage = ImageIO.read(byteArrayInputStream);

        // Закрытие потоков
        byteArrayOutputStream.close();
        byteArrayInputStream.close();

        return compressedImage;
    }

}