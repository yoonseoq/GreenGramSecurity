package com.green.greengramver2.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Paging {
    @Schema(example = "1", description = "Selected Page")
    private int page;
    @Schema(example = "30", description = "item count per page")
    private int size;
    @JsonIgnore
    private int sIdx;
    public Paging(Integer page, Integer size) {
        this.page = (page == null || page <= 0)? 1 : page;
        this.size = (size == null || size <= 0)? 20: size;
        this.sIdx = (this.page - 1) * this.size;
    }
}
