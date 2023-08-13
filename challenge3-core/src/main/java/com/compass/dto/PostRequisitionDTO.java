package com.compass.dto;


import com.compass.entity.Comment;
import com.compass.entity.History;
import com.compass.entity.PostRequisition;

import java.util.Set;

public record PostRequisitionDTO(

    Long id,

    Long externalId,

    String title,
    String body,

    Set<Comment> comments,

    Set<History> history

){

    public PostRequisitionDTO(PostRequisition postRequisition){
        this(postRequisition.getId(),postRequisition.getExternalId(), postRequisition.getTitle(), postRequisition.getBody(),
                postRequisition.getComment(), postRequisition.getHistory());
    }
}

