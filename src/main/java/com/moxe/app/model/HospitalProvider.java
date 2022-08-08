package com.moxe.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalProvider {
    private UUID id;
    private UUID hospitalId;
    private UUID providerId;
}
