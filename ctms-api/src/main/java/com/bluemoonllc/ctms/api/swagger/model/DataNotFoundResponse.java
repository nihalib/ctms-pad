package com.bluemoonllc.ctms.api.swagger.model;

import com.bluemoonllc.ctms.model.common.CtmsResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataNotFoundResponse {
    @Schema(example = "ERROR", description = "Response status")
    private String status;
    @Schema(example = "404", description = "Response status code")
    private CtmsResponseStatus responseCode;
    @Schema(example = "Requested data not found.", description = "Response message")
    private String message;
    @Schema(example = "CTMS", description = "Response system source")
    private String source;
    @Schema(nullable = true)
    private Object data;
}
