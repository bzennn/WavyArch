package xyz.bzennn.wavyarch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import xyz.bzennn.wavyarch.controller.FileController;

/**
 * Not Found exception for {@link FileController}
 *
 * @author bzennn
 * @version 1.0
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "File not found")
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5412441459125684814L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

}
