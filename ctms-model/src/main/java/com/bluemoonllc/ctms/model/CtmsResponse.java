package com.bluemoonllc.ctms.model;

import com.bluemoonllc.ctms.model.common.CtmsResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CtmsResponse<T> {
    private String status;
    private CtmsResponseStatus responseCode;
    private String message;
    private String source;
    private T data;
}
