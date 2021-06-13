package com.bluemoonllc.ctms.model.client.cpa;

import com.bluemoonllc.ctms.model.common.CtmsResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CpaResponse<T> {
    @Schema(example = "OK", description = "Response status")
    private String status;
    @Schema(example = "200", description = "Response status code")
    private CtmsResponseStatus responseCode;
    @Schema(example = "Requested data successfully returned.", description = "Response message")
    private String message;
    @Schema(example = "CPA", description = "Response system source")
    private String source;
    @Schema
    private T data;
}
