package com.compass.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
@Entity
@JsonIgnoreProperties("postRequisition")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "external_id")
    private Long externalId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "body", length=512)
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_requisition_id", nullable = false)
    private PostRequisition postRequisition;

    public Comment(String body, PostRequisition postRequisition){
        this.body= body;
        this.postRequisition = postRequisition;
    }

}
