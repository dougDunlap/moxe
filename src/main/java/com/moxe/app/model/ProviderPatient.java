package com.moxe.app.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ProviderPatient {
    private UUID id;
    private UUID patientId;
    private UUID hospitalProviderId;
    private boolean active;
}
