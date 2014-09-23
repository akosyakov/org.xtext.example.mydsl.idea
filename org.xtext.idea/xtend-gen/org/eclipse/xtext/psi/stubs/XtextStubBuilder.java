package org.eclipse.xtext.psi.stubs;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.psi.PsiFile;
import com.intellij.psi.stubs.DefaultStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IFileElementType;
import java.util.ArrayList;
import java.util.List;
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
        for (final IEObjectDescription exportedObject : _exportedObjects) {
          {
            final QualifiedName qualifiedName = exportedObject.getQualifiedName();
            List<ExportedObject> _exportedObjects_1 = stub.getExportedObjects();
            String _lastSegment = qualifiedName.getLastSegment();
            String _string = qualifiedName.toString();
            EClass _eClass = exportedObject.getEClass();
            ExportedObject _exportedObject = new ExportedObject(_lastSegment, _string, _eClass);
            _exportedObjects_1.add(_exportedObject);
          }
        }
      }
      _xblockexpression = stub;
    }
    return _xblockexpression;
  }
}
