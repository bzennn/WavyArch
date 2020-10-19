package xyz.bzennn.wavyarch.exception;

/**
 * File Upload exception
 *
 * @author bzennn
 * @version 1.0
 */
public class FileUploadException extends RuntimeException {

	private static final long serialVersionUID = -5412441459125684814L;

	public FileUploadException() {
		super();
	}

	public FileUploadException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileUploadException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileUploadException(String message) {
		super(message);
	}

	public FileUploadException(Throwable cause) {
		super(cause);
	}

}
