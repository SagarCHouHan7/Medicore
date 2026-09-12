package com.medicore.medicore.clinic.dto;

import java.time.LocalDateTime;

public record ReschedulingRequestDto(
        Long id,
        LocalDateTime newTime
) {
}
