package com.medicore.medicore.appointment.dto;

import java.time.LocalDateTime;

public record ReschedulingRequestDto(
        Long id,
        LocalDateTime newTime
) {
}
