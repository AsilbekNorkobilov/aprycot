package org.example.figma.service.impl;

import org.example.figma.entity.Attachment;
import org.example.figma.repo.AttachmentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AttachmentServiceImplTest {

    private AttachmentServiceImpl attachmentService;
    private AttachmentRepository attachmentRepository;

    @BeforeEach
    public void before(){
        this.attachmentRepository = Mockito.mock(AttachmentRepository.class);
        this.attachmentService=new AttachmentServiceImpl(attachmentRepository);

    }

    @Test
    void findById() {
        Attachment attachment=new Attachment();
        UUID id=UUID.randomUUID();
        attachment.setId(id);
        Mockito.when(attachmentRepository.findById(id)).thenReturn(Optional.of(Attachment.builder()
                        .id(id)
                .build()));
        Attachment byId = attachmentService.findById(id);
        Assertions.assertEquals(attachment.getId(),byId.getId());
    }

    @Test
    void attachmentNotFound(){
        UUID id=UUID.randomUUID();
        Mockito.when(attachmentRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class,()->{
            attachmentService.findById(id);
        });
    }

    @Test
    void savePhoto() throws IOException {
        Attachment attachment=new Attachment();
        UUID id=UUID.randomUUID();
        byte[] photo = {};
        attachment.setFullImage(photo);
        attachment.setId(id);
        Mockito.when(attachmentRepository.save(attachment)).thenReturn(Attachment.builder()
                        .id(id)
                        .fullImage(photo)
                .build());
        Attachment saved = attachmentService.savePhoto(photo);
        Assertions.assertEquals(attachment.getFullImage(),saved.getFullImage());
    }
}