package com.example.testproject.domain.post.dto;

import com.example.testproject.domain.post.entity.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponseDTO {
    String path;
    String name;

    @Builder
    public ImageResponseDTO(Image image){
        this.path = image == null ? null : image.getPath();
        this.name = image == null ? null : image.getName();
    }
}
