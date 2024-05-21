package com.dmitrenko.aiadapter.model.entity;

import com.dmitrenko.aiadapter.model.entity.action.Action;
import com.dmitrenko.aiadapter.model.entity.action.DeviceAction;
import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "device")
public class Device extends BaseEntity {

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Type(StringArrayType.class)
    @Column(name = "actions", columnDefinition = "_text", nullable = false)
    private DeviceAction[] actions = new DeviceAction[]{};

    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", columnDefinition = "device_status", nullable = false)
    private DeviceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;
}
