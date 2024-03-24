package com.smartvoucher.webEcommercesmartvoucher.payload;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOutput {
    private int page;
    private int totalItem;
    private int totalPage;
    private Object data;
}
