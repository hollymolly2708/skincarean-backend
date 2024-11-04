package com.skincarean.skincarean.model.user.response;

import com.skincarean.skincarean.model.PagingResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebResponse <T>{
    private T data;
    private String errors;
    private PagingResponse paging;
    private Boolean isSuccess;
}
