package me.yeon.thread.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NewChat {

	private String content;
	private String createdAt;
	private String senderSessionCode;

}
