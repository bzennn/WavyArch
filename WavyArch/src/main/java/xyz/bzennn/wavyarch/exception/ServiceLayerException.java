package xyz.bzennn.wavyarch.exception;

/**
 * Data Access Object layer exception
 *
 * @author bzennn
 * @version 1.0
 */
public class ServiceLayerException extends RuntimeException {

	private static final long serialVersionUID = -5412441459125684814L;

	public ServiceLayerException() {
		super();
	}

	public ServiceLayerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceLayerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceLayerException(String message) {
		super(message);
	}

	public ServiceLayerException(Throwable cause) {
		super(cause);
	}

}
