package org.eclipse.xtext.psi.stubs;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.psi.PsiFile;
import com.intellij.psi.stubs.DefaultStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IFileElementType;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.psi.stubs.ExportedObject;
import org.eclipse.xtext.psi.stubs.XtextFileElementType;
import org.eclipse.xtext.psi.stubs.XtextFileStub;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class XtextStubBuilder extends DefaultStubBuilder {
  @Inject
  private IElementTypeProvider elementTypeProvider;
  
  protected StubElement createStubForFile(final PsiFile file) {
    StubElement _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (file instanceof BaseXtextFile) {
        _matched=true;
        _switchResult = this.createStubForFile(((BaseXtextFile)file));
      }
    }
    if (!_matched) {
      _switchResult = super.createStubForFile(file);
    }
    return _switchResult;
  }
  
  protected XtextFileStub<BaseXtextFile> createStubForFile(final BaseXtextFile file) {
    XtextFileStub<BaseXtextFile> _xblockexpression = null;
    {
      IFileElementType _fileType = this.elementTypeProvider.getFileType();
      final XtextFileStub<BaseXtextFile> stub = new XtextFileStub<BaseXtextFile>(file, ((XtextFileElementType<?>) _fileType));
      ArrayList<ExportedObject> _newArrayList = CollectionLiterals.<ExportedObject>newArrayList();
      stub.setExportedObjects(_newArrayList);
      final IResourceDescription resourceDescription = file.getResourceDescription();
      boolean _notEquals = (!Objects.equal(resourceDescription, null));
      if (_notEquals) {
        Iterable<IEObjectDescription> _exportedObjects = resourceDescription.getExportedObjects();
        final Function1<IEObjectDescription, ExportedObject> _function = new Function1<IEObjectDescription, ExportedObject>() {
          public ExportedObject apply(final IEObjectDescription it) {
            QualifiedName _qualifiedName = it.getQualifiedName();
            EClass _eClass = it.getEClass();
            URI _eObjectURI = it.getEObjectURI();
            return new ExportedObject(_qualifiedName, _eClass, _eObjectURI);
          }
        };
        Iterable<ExportedObject> _map = IterableExtensions.<IEObjectDescription, ExportedObject>map(_exportedObjects, _function);
        for (final ExportedObject exportedObject : _map) {
          List<ExportedObject> _exportedObjects_1 = stub.getExportedObjects();
          _exportedObjects_1.add(exportedObject);
        }
      }
      _xblockexpression = stub;
    }
    return _xblockexpression;
  }
}
