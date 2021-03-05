package net.kurien.blog.module.autosave.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class AutosaveDto {
    private Integer no;
    private String jsonData;
    private Date time;
    private Date expireTime;
}
