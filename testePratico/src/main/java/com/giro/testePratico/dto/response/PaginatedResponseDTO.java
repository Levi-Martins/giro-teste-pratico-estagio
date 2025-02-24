package com.giro.testePratico.dto.response;

import java.util.List;

public record PaginatedResponseDTO<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
