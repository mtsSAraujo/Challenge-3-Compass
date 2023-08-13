package com.compass.entity;

import com.compass.entity.historyStatus.HistoryStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="history")
@Entity
@JsonIgnoreProperties("postRequisition")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant date;

    @Enumerated(EnumType.STRING)
    private HistoryStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="post_requisition_id", nullable = false)
    private PostRequisition postRequisition;

    public History(Instant instant, String created, PostRequisition postRequisition){
        this.date = instant;
        this.status = HistoryStatus.valueOf(created);
        this.postRequisition = postRequisition;
    }



}
