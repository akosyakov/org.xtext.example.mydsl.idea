package org.eclipse.xtext.idea

import com.intellij.openapi.progress.ProcessCanceledException
import org.eclipse.emf.common.util.WrappedException

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
		if (exception instanceof WrappedException) {
			throw exception.cause?.cause ?: exception.cause
		}
		if (exception instanceof CheckedProcessCanceledException) {
			throw exception.cause
		}
		throw exception
	}
}