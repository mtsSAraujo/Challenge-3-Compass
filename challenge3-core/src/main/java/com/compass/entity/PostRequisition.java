package com.compass.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post_requisition")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"history", "comments"})
public class PostRequisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "external_id")
    private Long externalId;

    @Column(name = "title")
    private String title;

    @Column(name = "body", length=512)
    private String body;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postRequisition")
    @OrderBy("external_id ASC") private Set<Comment> comment = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "postRequisition")
    @OrderBy("id ASC") private Set<History> history = new HashSet<>();

    public PostRequisition(Long postId){
        this.title = null;
        this.body = null;
        this.externalId = postId;
    }

}
