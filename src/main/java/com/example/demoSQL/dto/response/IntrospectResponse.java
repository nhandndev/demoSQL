package com.example.demoSQL.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IntrospectResponse {
    boolean valid;
}
