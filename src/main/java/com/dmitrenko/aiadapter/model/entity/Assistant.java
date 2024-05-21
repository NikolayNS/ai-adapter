package com.dmitrenko.aiadapter.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "assistant")
public class Assistant extends BaseEntity {

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assistant")
    private List<Conversation> conversations = new LinkedList<>();
}
