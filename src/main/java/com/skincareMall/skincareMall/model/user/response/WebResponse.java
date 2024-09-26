package com.skincareMall.skincareMall.model.user.response;

import com.skincareMall.skincareMall.model.PagingResponse;
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
}
