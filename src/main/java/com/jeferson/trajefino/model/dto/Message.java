package com.jeferson.trajefino.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Message {
    private String title;
    private String description;
    private LocalDateTime timestamp;

}
