package org.eclipse.xtext.psi.stubs;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intellij.lang.Language;
import com.intellij.psi.StubBuilder;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubIndexKey;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.psi.tree.IStubFileElementType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.psi.stubindex.ExportedObjectQualifiedNameIndex;
import org.eclipse.xtext.psi.stubs.ExportedObject;
import org.eclipse.xtext.psi.stubs.XtextFileStub;
import org.eclipse.xtext.psi.stubs.XtextStubBuilder;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class XtextFileElementType<T extends XtextFileStub<?>> extends IStubFileElementType<T> {
  @Inject
  private Provider<XtextStubBuilder> xtextStubBuilderProvider;
  
  @Inject
  private ExportedObjectQualifiedNameIndex exportedObjectQualifiedNameIndex;
  
  @Inject
  private JvmDeclaredTypeShortNameIndex jvmDeclaredTypeShortNameIndex;
  
  public XtextFileElementType(final Language language) {
    super(language);
    if ((language instanceof IXtextLanguage)) {
      ((IXtextLanguage)language).injectMembers(this);
    }
  }
  
  public String getExternalId() {
    return "xtext.file";
  }
  
  public StubBuilder getBuilder() {
    return this.xtextStubBuilderProvider.get();
  }
  
  public void serialize(final T stub, final StubOutputStream it) throws IOException {
    List<ExportedObject> _exportedObjects = stub.getExportedObjects();
    this.writeExportedObjects(it, _exportedObjects);
  }
  
  protected void writeExportedObjects(final StubOutputStream it, final List<ExportedObject> exportedObjects) throws IOException {
    int _size = exportedObjects.size();
    it.writeInt(_size);
    for (final ExportedObject exportedObject : exportedObjects) {
      {
        String _name = exportedObject.getName();
        it.writeUTF(_name);
        String _qualifiedName = exportedObject.getQualifiedName();
        it.writeUTF(_qualifiedName);
        EClass _type = exportedObject.getType();
        this.writeEClass(it, _type);
      }
    }
  }
  
  protected void writeEClass(final StubOutputStream it, final EClass type) {
    try {
      EPackage _ePackage = type.getEPackage();
      String _nsURI = _ePackage.getNsURI();
      it.writeUTF(_nsURI);
      String _name = type.getName();
      it.writeUTF(_name);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public T deserialize(final StubInputStream it, final StubElement parentStub) throws IOException {
    T _xblockexpression = null;
    {
      final XtextFileStub<BaseXtextFile> stub = new XtextFileStub<BaseXtextFile>(null, this);
      ArrayList<ExportedObject> _readExportedObjects = this.readExportedObjects(it);
      stub.setExportedObjects(_readExportedObjects);
      _xblockexpression = ((T) stub);
    }
    return _xblockexpression;
  }
  
  protected ArrayList<ExportedObject> readExportedObjects(final StubInputStream it) throws IOException {
    ArrayList<ExportedObject> _xblockexpression = null;
    {
      final ArrayList<ExportedObject> exportedObjects = CollectionLiterals.<ExportedObject>newArrayList();
      final int count = it.readInt();
      for (int i = 0; (i < count); i++) {
        {
          final String name = it.readUTF();
          final String qualifiedName = it.readUTF();
          final EClass type = this.readEClass(it);
          ExportedObject _exportedObject = new ExportedObject(name, qualifiedName, type);
          exportedObjects.add(_exportedObject);
        }
      }
      _xblockexpression = exportedObjects;
    }
    return _xblockexpression;
  }
  
  protected EClass readEClass(final StubInputStream it) {
    try {
      EClass _xblockexpression = null;
      {
        final String packageURI = it.readUTF();
        final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageURI);
        String _readUTF = it.readUTF();
        EClassifier _eClassifier = ePackage.getEClassifier(_readUTF);
        _xblockexpression = ((EClass) _eClassifier);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void indexStub(final PsiFileStub stub, @Extension final IndexSink sink) {
    if ((stub instanceof XtextFileStub<?>)) {
      List<ExportedObject> _exportedObjects = ((XtextFileStub<?>)stub).getExportedObjects();
      for (final ExportedObject exportedObject : _exportedObjects) {
        {
          StubIndexKey<String, BaseXtextFile> _key = this.exportedObjectQualifiedNameIndex.getKey();
          String _qualifiedName = exportedObject.getQualifiedName();
          sink.<BaseXtextFile, String>occurrence(_key, _qualifiedName);
          EClass _type = exportedObject.getType();
          boolean _isAssignableFrom = EcoreUtil2.isAssignableFrom(TypesPackage.Literals.JVM_DECLARED_TYPE, _type);
          if (_isAssignableFrom) {
            StubIndexKey<String, BaseXtextFile> _key_1 = this.jvmDeclaredTypeShortNameIndex.getKey();
            String _name = exportedObject.getName();
            sink.<BaseXtextFile, String>occurrence(_key_1, _name);
          }
        }
      }
    }
  }
  
  public void indexStub(final T stub, final IndexSink sink) {
    this.indexStub(((PsiFileStub<?>) stub), sink);
  }
}
