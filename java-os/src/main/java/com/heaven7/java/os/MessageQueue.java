package com.heaven7.java.os;

import java.util.ArrayList;

/*public*/ class MessageQueue {

	private static final String TAG = "MessageQueue";
	private static final boolean DEBUG = true;

	private final ArrayList<IdleHandler> mIdleHandlers = new ArrayList<IdleHandler>();
	private final boolean mQuitAllowed;
	private IdleHandler[] mPendingIdleHandlers;
	private Printer mLogging;

	private Message mMessages;
	private boolean mQuitting;

	// Indicates whether next() is blocked waiting in pollOnce() with a non-zero
	// timeout.
	private boolean mBlocked;
	private int mNextBarrierToken;

	public MessageQueue(boolean quitAllowed, Printer printer) {
		this.mQuitAllowed = quitAllowed;
		this.mLogging = printer;
	}

	/*public*/ void setMessageLogging(Printer printer) {
		mLogging = printer;
	}

	/**
	 * Returns true if the looper has no pending messages which are due to be
	 * processed.
	 *
	 * <p>
	 * This method is safe to call from any thread.
	 *
	 * @return True if the looper is idle.
	 */
	public boolean isIdle() {
		synchronized (this) {
			final long now = System.currentTimeMillis();
			return mMessages == null || now < mMessages.when;
		}
	}

	/**
	 * Add a new {@link IdleHandler} to this message queue. This may be removed
	 * automatically for you by returning false from
	 * {@link IdleHandler#queueIdle IdleHandler.queueIdle()} when it is invoked,
	 * or explicitly removing it with {@link #removeIdleHandler}.
	 *
	 * <p>
	 * This method is safe to call from any thread.
	 *
	 * @param handler
	 *            The IdleHandler to be added. @NonNull
	 */
	public void addIdleHandler(IdleHandler handler) {
		if (handler == null) {
			throw new NullPointerException("Can't add a null IdleHandler");
		}
		synchronized (this) {
			mIdleHandlers.add(handler);
		}
	}

	/**
	 * Remove an {@link IdleHandler} from the queue that was previously added
	 * with {@link #addIdleHandler}. If the given object is not currently in the
	 * idle list, nothing is done.
	 *
	 * <p>
	 * This method is safe to call from any thread.
	 *
	 * @param handler
	 *            The IdleHandler to be removed.@NonNull
	 */
	public void removeIdleHandler(IdleHandler handler) {
		synchronized (this) {
			mIdleHandlers.remove(handler);
		}
	}

	/**
	 * Returns whether this looper's thread is currently polling for more work
	 * to do. This is a good signal that the loop is still alive rather than
	 * being stuck handling a callback. Note that this method is intrinsically
	 * racy, since the state of the loop can change before you get the result
	 * back.
	 *
	 * <p>
	 * This method is safe to call from any thread.
	 *
	 * @return True if the looper is currently polling for events.
	 * @hide
	 */
	public boolean isPolling() {
		synchronized (this) {
			return isPollingLocked();
		}
	}

	private boolean isPollingLocked() {
		// If the loop is quitting then it must not be idling.
		return !mQuitting;
	}

	public Message next() {
		// TODO Auto-generated method stub
		int pendingIdleHandlerCount = -1; // -1 only during first iteration
		int nextPollTimeoutMillis = 0;

		for (;;) {
			synchronized (this) {
				// Try to retrieve the next message. Return if found.
				final long now = System.currentTimeMillis();
				Message prevMsg = null;
				Message msg = mMessages;
				if (msg != null && msg.target == null) {
					// Stalled by a barrier. Find the next asynchronous message
					// in the queue.
					do {
						prevMsg = msg;
						msg = msg.next;
					} while (msg != null && !msg.isAsynchronous());
				}
				if (msg != null) {
					if (now < msg.when) {
						// Next message is not ready. Set a timeout to wake up
						// when it is ready.
						nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
					} else {
						// Got a message.
						mBlocked = false;
						if (prevMsg != null) {
							prevMsg.next = msg.next;
						} else {
							mMessages = msg.next;
						}
						msg.next = null;
						mLogging.info(TAG, "next", "Returning message: " + msg);
						msg.markInUse();
						return msg;
					}
				} else {
					// No more messages.
					nextPollTimeoutMillis = -1;
				}

				// Process the quit message now that all pending messages have
				// been handled.
				if (mQuitting) {
					//dispose();
					return null;
				}

				// If first time idle, then get the number of idlers to run.
				// Idle handles only run if the queue is empty or if the first
				// message
				// in the queue (possibly a barrier) is due to be handled in the
				// future.
				if (pendingIdleHandlerCount < 0 && (mMessages == null || now < mMessages.when)) {
					pendingIdleHandlerCount = mIdleHandlers.size();
				}
				if (pendingIdleHandlerCount <= 0) {
					// No idle handlers to run. Loop and wait some more.
					mBlocked = true;
					continue;
				}

				if (mPendingIdleHandlers == null) {
					mPendingIdleHandlers = new IdleHandler[Math.max(pendingIdleHandlerCount, 4)];
				}
				mPendingIdleHandlers = mIdleHandlers.toArray(mPendingIdleHandlers);
			}

			// Run the idle handlers.
			// We only ever reach this code block during the first iteration.
			for (int i = 0; i < pendingIdleHandlerCount; i++) {
				final IdleHandler idler = mPendingIdleHandlers[i];
				mPendingIdleHandlers[i] = null; // release the reference to the
												// handler

				boolean keep = false;
				try {
					keep = idler.queueIdle();
				} catch (Throwable t) {
					mLogging.warn(TAG, "IdleHandler threw exception", t);
				}

				if (!keep) {
					synchronized (this) {
						mIdleHandlers.remove(idler);
					}
				}
			}

			// Reset the idle handler count to 0 so we do not run them again.
			pendingIdleHandlerCount = 0;

			// While calling an idle handler, a new message could have been
			// delivered
			// so go back and look again for a pending message without waiting.
			nextPollTimeoutMillis = 0;
		}
	}

	/* public */ void removeMessages(Handler h, int what, Object object) {
		if (h == null) {
			return;
		}

		synchronized (this) {
			Message p = mMessages;

			// Remove all messages at front.
			while (p != null && p.target == h && p.what == what && (object == null || p.obj == object)) {
				Message n = p.next;
				mMessages = n;
				p.recycleUnchecked();
				p = n;
			}

			// Remove all messages after front.
			while (p != null) {
				Message n = p.next;
				if (n != null) {
					if (n.target == h && n.what == what && (object == null || n.obj == object)) {
						Message nn = n.next;
						n.recycleUnchecked();
						p.next = nn;
						continue;
					}
				}
				p = n;
			}
		}
	}

	/* public */ void removeMessages(Handler h, Runnable r, Object object) {
		if (h == null || r == null) {
			return;
		}

		synchronized (this) {
			Message p = mMessages;

			// Remove all messages at front.
			while (p != null && p.target == h && p.callback == r && (object == null || p.obj == object)) {
				Message n = p.next;
				mMessages = n;
				p.recycleUnchecked();
				p = n;
			}

			// Remove all messages after front.
			while (p != null) {
				Message n = p.next;
				if (n != null) {
					if (n.target == h && n.callback == r && (object == null || n.obj == object)) {
						Message nn = n.next;
						n.recycleUnchecked();
						p.next = nn;
						continue;
					}
				}
				p = n;
			}
		}
	}

	/* public */ void removeCallbacksAndMessages(Handler h, Object object) {
		if (h == null) {
			return;
		}

		synchronized (this) {
			Message p = mMessages;

			// Remove all messages at front.
			while (p != null && p.target == h && (object == null || p.obj == object)) {
				Message n = p.next;
				mMessages = n;
				p.recycleUnchecked();
				p = n;
			}

			// Remove all messages after front.
			while (p != null) {
				Message n = p.next;
				if (n != null) {
					if (n.target == h && (object == null || n.obj == object)) {
						Message nn = n.next;
						n.recycleUnchecked();
						p.next = nn;
						continue;
					}
				}
				p = n;
			}
		}
	}

	/**
	 * Posts a synchronization barrier to the Looper's message queue.
	 *
	 * Message processing occurs as usual until the message queue encounters the
	 * synchronization barrier that has been posted. When the barrier is
	 * encountered, later synchronous messages in the queue are stalled
	 * (prevented from being executed) until the barrier is released by calling
	 * {@link #removeSyncBarrier} and specifying the token that identifies the
	 * synchronization barrier.
	 *
	 * This method is used to immediately postpone execution of all subsequently
	 * posted synchronous messages until a condition is met that releases the
	 * barrier. Asynchronous messages (see {@link Message#isAsynchronous} are
	 * exempt from the barrier and continue to be processed as usual.
	 *
	 * This call must be always matched by a call to {@link #removeSyncBarrier}
	 * with the same token to ensure that the message queue resumes normal
	 * operation. Otherwise the application will probably hang!
	 *
	 * @return A token that uniquely identifies the barrier. This token must be
	 *         passed to {@link #removeSyncBarrier} to release the barrier.
	 *
	 * @hide
	 */
	public int postSyncBarrier() {
		return postSyncBarrier(System.currentTimeMillis());
	}

	private int postSyncBarrier(long when) {
		// Enqueue a new sync barrier token.
		// We don't need to wake the queue because the purpose of a barrier is
		// to stall it.
		synchronized (this) {
			final int token = mNextBarrierToken++;
			final Message msg = Message.obtain();
			msg.markInUse();
			msg.when = when;
			msg.arg1 = token;

			Message prev = null;
			Message p = mMessages;
			if (when != 0) {
				while (p != null && p.when <= when) {
					prev = p;
					p = p.next;
				}
			}
			if (prev != null) { // invariant: p == prev.next
				msg.next = p;
				prev.next = msg;
			} else {
				msg.next = p;
				mMessages = msg;
			}
			return token;
		}
	}

	/**
	 * Removes a synchronization barrier.
	 *
	 * @param token
	 *            The synchronization barrier token that was returned by
	 *            {@link #postSyncBarrier}.
	 *
	 * @throws IllegalStateException
	 *             if the barrier was not found.
	 *
	 * @hide
	 */
	public void removeSyncBarrier(int token) {
		// Remove a sync barrier token from the queue.
		// If the queue is no longer stalled by a barrier then wake it.
		synchronized (this) {
			Message prev = null;
			Message p = mMessages;
			while (p != null && (p.target != null || p.arg1 != token)) {
				prev = p;
				p = p.next;
			}
			if (p == null) {
				throw new IllegalStateException("The specified message queue synchronization "
						+ " barrier token has not been posted or has already been removed.");
			}
			final boolean needWake;
			if (prev != null) {
				prev.next = p.next;
				needWake = false;
			} else {
				mMessages = p.next;
				needWake = mMessages == null || mMessages.target != null;
			}
			p.recycleUnchecked();

			// If the loop is quitting then it is already awake.
			// We can assume mPtr != 0 when mQuitting is false.
			/*
			 * if (needWake && !mQuitting) { nativeWake(mPtr); }
			 */
		}
	}

	/* public */ void quit(boolean safe) {
		if (!mQuitAllowed) {
			throw new IllegalStateException("Main thread not allowed to quit.");
		}

		synchronized (this) {
			if (mQuitting) {
				return;
			}
			mQuitting = true;

			if (safe) {
				removeAllFutureMessagesLocked();
			} else {
				removeAllMessagesLocked();
			}
		}
	}

	private void removeAllMessagesLocked() {
		Message p = mMessages;
		for (; p != null;) {
			Message n = p.next;
			p.recycleUnchecked();
			p = n;
		}
		mMessages = null;
	}

	private void removeAllFutureMessagesLocked() {
		final long now = System.currentTimeMillis();
		Message p = mMessages;
		if (p != null) {
			if (p.when > now) {
				removeAllMessagesLocked();
			} else {
				Message n;
				for (;;) {
					n = p.next;
					if (n == null) {
						return;
					}
					if (n.when > now) {
						break;
					}
					p = n;
				}
				p.next = null;
				do {
					p = n;
					n = p.next;
					p.recycleUnchecked();
				} while (n != null);
			}
		}
	}

	boolean enqueueMessage(Message msg, long when) {
		if (msg.target == null) {
			throw new IllegalArgumentException("Message must have a target.");
		}
		if (msg.isInUse()) {
			throw new IllegalStateException(msg + " This message is already in use.");
		}

		synchronized (this) {
			if (mQuitting) {
				IllegalStateException e = new IllegalStateException(
						msg.target + " sending message to a Handler on a dead thread");
				mLogging.warn(TAG, e.getMessage(), e);
				msg.recycle();
				return false;
			}

			msg.markInUse();
			msg.when = when;
			Message p = mMessages;
			boolean needWake;
			if (p == null || when == 0 || when < p.when) {
				// New head, wake up the event queue if blocked.
				msg.next = p;
				mMessages = msg;
				needWake = mBlocked;
			} else {
				// Inserted within the middle of the queue. Usually we don't
				// have to wake
				// up the event queue unless there is a barrier at the head of
				// the queue
				// and the message is the earliest asynchronous message in the
				// queue.
				needWake = mBlocked && p.target == null && msg.isAsynchronous();
				Message prev;
				for (;;) {
					prev = p;
					p = p.next;
					if (p == null || when < p.when) {
						break;
					}
					if (needWake && p.isAsynchronous()) {
						needWake = false;
					}
				}
				msg.next = p; // invariant: p == prev.next
				prev.next = msg;
			}

			// We can assume mPtr != 0 because mQuitting is false.
			/*if (needWake) {
				nativeWake(mPtr);
			}*/
		}
		return true;
	}

	boolean hasMessages(Handler h, int what, Object object) {
		if (h == null) {
			return false;
		}

		synchronized (this) {
			Message p = mMessages;
			while (p != null) {
				if (p.target == h && p.what == what && (object == null || p.obj == object)) {
					return true;
				}
				p = p.next;
			}
			return false;
		}
	}

	boolean hasMessages(Handler h, Runnable r, Object object) {
		if (h == null) {
			return false;
		}

		synchronized (this) {
			Message p = mMessages;
			while (p != null) {
				if (p.target == h && p.callback == r && (object == null || p.obj == object)) {
					return true;
				}
				p = p.next;
			}
			return false;
		}
	}

	/* public */ void dump(Printer pw, String prefix) {
		synchronized (this) {
			long now = System.currentTimeMillis();
			int n = 0;
			for (Message msg = mMessages; msg != null; msg = msg.next) {
				pw.println(prefix + "Message " + n + ": " + msg.toString(now));
				n++;
			}
			pw.println(prefix + "(Total messages: " + n + ", polling=" + isPollingLocked() + ", quitting=" + mQuitting
					+ ")");
		}
	}

	/**
	 * Callback interface for discovering when a thread is going to block
	 * waiting for more messages.
	 */
	public static interface IdleHandler {
		/**
		 * Called when the message queue has run out of messages and will now
		 * wait for more. Return true to keep your idle handler active, false to
		 * have it removed. This may be called if there are still messages
		 * pending in the queue, but they are all scheduled to be dispatched
		 * after the current time.
		 */
		boolean queueIdle();
	}

}
