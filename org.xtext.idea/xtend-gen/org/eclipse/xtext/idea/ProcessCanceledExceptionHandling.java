package org.eclipse.xtext.idea;

import com.intellij.openapi.progress.ProcessCanceledException;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ProcessCanceledExceptionHandling {
  public static class CheckedProcessCanceledException extends Exception {
    public CheckedProcessCanceledException(final ProcessCanceledException cause) {
      super(cause);
    }
  }
  
  public static RuntimeException wrappedReThrow(final ProcessCanceledException exception) {
    try {
      throw new ProcessCanceledExceptionHandling.CheckedProcessCanceledException(exception);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static RuntimeException unWrappedReThrow(final Exception exception) {
    try {
      boolean _and = false;
      if (!(exception instanceof WrappedException)) {
        _and = false;
      } else {
        Throwable _cause = exception.getCause();
        _and = (_cause instanceof ProcessCanceledExceptionHandling.CheckedProcessCanceledException);
      }
      if (_and) {
        Throwable _cause_1 = exception.getCause();
        throw _cause_1.getCause();
      }
      if ((exception instanceof ProcessCanceledExceptionHandling.CheckedProcessCanceledException)) {
        throw ((ProcessCanceledExceptionHandling.CheckedProcessCanceledException)exception).getCause();
      }
      throw exception;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
