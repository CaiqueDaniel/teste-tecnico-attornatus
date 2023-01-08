package com.attornatus.testetecnico.dtos.responses;

import java.util.List;

public class PaginationResponse<DTO> {
    public MetaData meta;
    public List<DTO> data;

    public PaginationResponse(MetaData meta, List<DTO> data) {
        this.meta = meta;
        this.data = data;
    }
}
