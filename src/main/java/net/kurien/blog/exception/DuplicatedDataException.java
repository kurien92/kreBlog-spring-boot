package net.kurien.blog.exception;

/**
 * 같은 값을 가진 데이터가 존재하는 경우 발생하는 예외
 * 유일한 속성을 가진 데이터가 중복되는 경우가 있어선 안된다.
 */
public class DuplicatedDataException extends Exception {
	public DuplicatedDataException() {
		super("중복된 값을 가진 데이터가 존재합니다.");
	}
	
	public DuplicatedDataException(String msg) {
		super(msg);
	}
}
