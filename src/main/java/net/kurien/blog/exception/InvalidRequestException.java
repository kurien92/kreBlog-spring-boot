package net.kurien.blog.exception;

/**
 * 잘못된 형태의 요청이 들어오는 경우 발생하는 예외
 */
public class InvalidRequestException extends Exception {
	public InvalidRequestException () {
		super("잘못된 요청입니다.");
	}

	public InvalidRequestException(String msg) {
		super(msg);
	}
}
