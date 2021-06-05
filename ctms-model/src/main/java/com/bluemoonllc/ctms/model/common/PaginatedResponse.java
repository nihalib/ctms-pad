package com.bluemoonllc.ctms.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaginatedResponse<T> {
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalRecords;
    private List<T> data;
}
