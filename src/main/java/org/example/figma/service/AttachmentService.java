package org.example.figma.service;

import org.example.figma.entity.Attachment;

import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {
    Attachment findById(UUID id);

    Attachment savePhoto(byte[] bytes) throws IOException;

    void deleteAttachment(UUID currentProduct);
}
