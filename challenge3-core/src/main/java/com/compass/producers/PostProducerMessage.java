package com.compass.producers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class PostProducerMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -6849794470754667710L;

    Long postId;

}
