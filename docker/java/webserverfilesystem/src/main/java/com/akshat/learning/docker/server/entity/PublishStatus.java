package com.akshat.learning.docker.server.entity;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PublishStatus {
    @Setter
    private String status;
    @Setter
    private String errorMessage;
}
