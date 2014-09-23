package org.eclipse.xtext.idea.types.psi.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.impl.light.AbstractLightClass;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.descriptions.IStubGenerator;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class PsiJvmDeclaredTypeImpl extends AbstractLightClass implements PsiJvmDeclaredType {
  @Inject
  private IStubGenerator stubGenerator;
  
  @Inject
  private IQualifiedNameConverter qualifiedNameConverter;
  
  private PsiClass delegate;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final EClass type;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final String qualifiedName;
  
  private final PsiNamedEObject psiNamedEObject;
  
  private final JvmDeclaredType declaredType;
  
  public PsiJvmDeclaredTypeImpl(final JvmDeclaredType declaredType, final PsiNamedEObject psiNamedEObject) {
    super(psiNamedEObject.getManager(), psiNamedEObject.getLanguage());
    this.type = this.type;
    this.declaredType = declaredType;
    this.qualifiedName = this.qualifiedName;
    this.psiNamedEObject = psiNamedEObject;
    final Language language = this.getLanguage();
    if ((language instanceof IXtextLanguage)) {
      ((IXtextLanguage)language).injectMembers(this);
    }
  }
  
  public PsiElement copy() {
    return new PsiJvmDeclaredTypeImpl(this.declaredType, this.psiNamedEObject);
  }
  
  public PsiClass getDelegate() {
    PsiClass _xblockexpression = null;
    {
      boolean _equals = Objects.equal(this.delegate, null);
      if (_equals) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("package ");
        String _packageName = this.declaredType.getPackageName();
        _builder.append(_packageName, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("class ");
        String _simpleName = this.declaredType.getSimpleName();
        _builder.append(_simpleName, "");
        _builder.append(" {");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        {
          EList<JvmMember> _members = this.declaredType.getMembers();
          Iterable<JvmOperation> _filter = Iterables.<JvmOperation>filter(_members, JvmOperation.class);
          for(final JvmOperation method : _filter) {
            _builder.append("\t");
            _builder.append("public void ");
            String _simpleName_1 = method.getSimpleName();
            _builder.append(_simpleName_1, "\t");
            _builder.append("(r) {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
        _builder.newLine();
        _builder.append("}");
        _builder.newLine();
        final String text = _builder.toString();
        Project _project = this.getProject();
        PsiFileFactory _instance = PsiFileFactory.getInstance(_project);
        PsiFile _createFileFromText = _instance.createFileFromText("_Dummy_.java", JavaFileType.INSTANCE, text);
        final PsiFile psiFile = _createFileFromText;
        boolean _matched = false;
        if (!_matched) {
          if (psiFile instanceof PsiJavaFile) {
            _matched=true;
            PsiClass[] _classes = ((PsiJavaFile)psiFile).getClasses();
            PsiClass _head = IterableExtensions.<PsiClass>head(((Iterable<PsiClass>)Conversions.doWrapArray(_classes)));
            this.delegate = _head;
          }
        }
      }
      _xblockexpression = this.delegate;
    }
    return _xblockexpression;
  }
  
  public PsiElement getNavigationElement() {
    return this.psiNamedEObject;
  }
  
  public boolean isValid() {
    return this.psiNamedEObject.isValid();
  }
  
  public boolean isEquivalentTo(final PsiElement another) {
    boolean _xblockexpression = false;
    {
      if ((another instanceof PsiJvmDeclaredType)) {
        return this.isEquivalent(this, ((PsiJvmDeclaredType)another));
      }
      _xblockexpression = false;
    }
    return _xblockexpression;
  }
  
  protected boolean isEquivalent(final PsiJvmDeclaredType one, final PsiJvmDeclaredType another) {
    String _qualifiedName = one.getQualifiedName();
    String _qualifiedName_1 = another.getQualifiedName();
    return Objects.equal(_qualifiedName, _qualifiedName_1);
  }
  
  @Pure
  public EClass getType() {
    return this.type;
  }
  
  @Pure
  public String getQualifiedName() {
    return this.qualifiedName;
  }
}
