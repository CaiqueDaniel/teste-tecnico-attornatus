package com.attornatus.testetecnico.dtos.responses;

import java.util.Optional;

public class MetaData {
    public Long total;
    public int limit, page, pages;
    public String first, last;
    public Optional<String> prev, next;

    public MetaData(String pathname, int page, int limit, Long total) {
        int totalPages = (int) Math.ceil((float) total / (float) limit);

        this.page = page;
        this.pages = totalPages;
        this.limit = limit;
        this.total = total;

        this.first = pathname + "?pagina=1";
        this.last = pathname + "?pagina=" + totalPages;
        this.next = (page + 1) > totalPages ? Optional.empty() : Optional.of(pathname + "?pagina=" + (page + 1));
        this.prev = (page - 1) <= 0 ? Optional.empty() : Optional.of(pathname + "?pagina=" + (page - 1));
    }
}
