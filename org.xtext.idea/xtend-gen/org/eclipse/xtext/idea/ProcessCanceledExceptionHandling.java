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
      if ((exception instanceof WrappedException)) {
        Throwable _elvis = null;
        Throwable _cause = ((WrappedException)exception).getCause();
        Throwable _cause_1 = null;
        if (_cause!=null) {
          _cause_1=_cause.getCause();
        }
        if (_cause_1 != null) {
          _elvis = _cause_1;
        } else {
          Throwable _cause_2 = ((WrappedException)exception).getCause();
          _elvis = _cause_2;
        }
        throw _elvis;
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
