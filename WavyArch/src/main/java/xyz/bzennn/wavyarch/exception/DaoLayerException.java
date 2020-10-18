package xyz.bzennn.wavyarch.exception;

public class DaoLayerException extends RuntimeException {

	private static final long serialVersionUID = -5412441459125684814L;

	public DaoLayerException() {
		super();
	}

	public DaoLayerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DaoLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoLayerException(String message) {
		super(message);
	}

	public DaoLayerException(Throwable cause) {
		super(cause);
	}

}
