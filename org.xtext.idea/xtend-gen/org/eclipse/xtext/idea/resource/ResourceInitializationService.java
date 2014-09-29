package org.eclipse.xtext.idea.resource;

import com.google.inject.Singleton;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.psi.PsiEObject;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.ISynchronizable;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.xbase.lib.Exceptions;

@Singleton
@SuppressWarnings("all")
public class ResourceInitializationService {
  public Resource ensureFullyInitialized(final Object context) {
    try {
      Resource _xtrycatchfinallyexpression = null;
      try {
        Resource _switchResult = null;
        boolean _matched = false;
        if (!_matched) {
          if (context instanceof PsiEObject) {
            _matched=true;
            Resource _resource = ((PsiEObject)context).getResource();
            _switchResult = this.ensureFullyInitialized(_resource);
          }
        }
        if (!_matched) {
          if (context instanceof BaseXtextFile) {
            _matched=true;
            Resource _resource = ((BaseXtextFile)context).getResource();
            _switchResult = this.ensureFullyInitialized(_resource);
          }
        }
        if (!_matched) {
          if (context instanceof DerivedStateAwareResource) {
            _matched=true;
            Resource _xifexpression = null;
            if ((context instanceof ISynchronizable<?>)) {
              final IUnitOfWork<Resource, Object> _function = new IUnitOfWork<Resource, Object>() {
                public Resource exec(final Object it) throws Exception {
                  return ResourceInitializationService.this.doEnsureFullyInitialized(((DerivedStateAwareResource)context));
                }
              };
              _xifexpression = ((ISynchronizable<?>)context).<Resource>execute(_function);
            } else {
              _xifexpression = this.doEnsureFullyInitialized(((DerivedStateAwareResource)context));
            }
            _switchResult = _xifexpression;
          }
        }
        if (!_matched) {
          _switchResult = null;
        }
        _xtrycatchfinallyexpression = _switchResult;
      } catch (final Throwable _t) {
        if (_t instanceof OperationCanceledError) {
          final OperationCanceledError e = (OperationCanceledError)_t;
          throw e.getWrapped();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
      return _xtrycatchfinallyexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected Resource doEnsureFullyInitialized(final DerivedStateAwareResource context) {
    DerivedStateAwareResource _xblockexpression = null;
    {
      final boolean deliver = context.eDeliver();
      try {
        context.eSetDeliver(false);
        context.installDerivedState(false);
      } finally {
        context.eSetDeliver(deliver);
      }
      _xblockexpression = context;
    }
    return _xblockexpression;
  }
}
