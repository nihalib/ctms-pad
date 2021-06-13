package com.bluemoonllc.ctms.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaginatedResponse<T> {
    @Schema(example = "5", description = "Total records returned for requested page size")
    private Integer pageSize;
    @Schema(example = "10", description = "Total pages size available")
    private Integer totalPage;
    @Schema(example = "50", description = "Total records available")
    private Integer totalRecords;
    @Schema(description = "List of data returned")
    private List<T> data;
}
