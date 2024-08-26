package org.example.figma.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.figma.entity.Attachment;
import org.example.figma.repo.AttachmentRepository;
import org.example.figma.service.AttachmentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    @Override
    public Attachment findById(UUID id) {
        return attachmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Attachment not found"));
    }

    @Override
    public Attachment savePhoto(byte[] photo) throws IOException {
        Attachment attachment = Attachment.builder()
                .fullImage(photo)
                .build();
        attachment.compressImage();
        attachmentRepository.save(attachment);
        return attachment;
    }

    @Override
    public void deleteAttachment(UUID id) {
        Attachment attachment = attachmentRepository.findById(id).get();
        attachmentRepository.delete(attachment);
    }
}
