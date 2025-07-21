package ru.mts.media.platform.umc.dao.postgres.common;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class FullExternalIdPk implements Serializable {
    private String brand;
    private String provider;
    private String externalId;
}
