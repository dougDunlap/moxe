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
public class Patient {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
}
