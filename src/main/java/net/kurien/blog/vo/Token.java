package net.kurien.blog.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.*;

@Data
public class Token implements Serializable {
	private String key;
	private Date expirationTime;
}
