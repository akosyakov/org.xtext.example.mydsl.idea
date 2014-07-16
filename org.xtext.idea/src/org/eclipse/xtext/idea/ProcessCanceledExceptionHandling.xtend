package org.eclipse.xtext.idea

import com.intellij.openapi.progress.ProcessCanceledException

class ProcessCanceledExceptionHandling {
	static class CheckedProcessCanceledException extends Exception {
		new(ProcessCanceledException cause) {
			super(cause)
		}
	}
	
	def static RuntimeException wrappedReThrow(ProcessCanceledException exception) {
		throw new CheckedProcessCanceledException(exception)
	}
	
	def static RuntimeException unWrappedReThrow(Exception exception) {
		if (exception instanceof CheckedProcessCanceledException) {
			throw exception.cause
		}
		throw exception
	}
}