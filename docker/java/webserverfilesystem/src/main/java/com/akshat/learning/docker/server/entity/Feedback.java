package com.akshat.learning.docker.server.entity;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Feedback {
    @Setter
    @CsvBindByPosition(position = 0)
    private String user;
    @Setter
    @CsvBindByPosition(position = 1)
    private String content;
    @Setter
    @CsvBindByPosition(position = 2)
    private LocalDateTime time;
}
