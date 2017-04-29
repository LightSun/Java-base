package com.heaven7.java.sync;

/**
 * created if occurs exception when call sync
 * @author heaven7
 *
 */
public final class SyncException extends RuntimeException{

	private static final long serialVersionUID = -849218201673258756L;

	public SyncException() {
		super();
	}

	public SyncException(String message, Throwable cause) {
		super(message, cause);
	}

	public SyncException(String message) {
		super(message);
	}

	public SyncException(Throwable cause) {
		super(cause);
	}
	

}
