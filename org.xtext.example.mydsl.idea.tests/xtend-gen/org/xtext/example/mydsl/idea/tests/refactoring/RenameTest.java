package org.xtext.example.mydsl.idea.tests.refactoring;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiReferenceEObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.xtext.example.mydsl.myDsl.MyDslPackage;

@SuppressWarnings("all")
public class RenameTest extends LightCodeInsightFixtureTestCase {
  public void testRenameQualifiedName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.myFixture.configureByText("Foo.mydsl", _builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("entity Bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("foo.Fo<caret>o foo");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.myFixture.configureByText("Bar.mydsl", _builder_1.toString());
    this.myFixture.renameElementAtCaret("Foo2");
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("entity Bar {");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("foo.Foo2 foo");
    _builder_2.newLine();
    _builder_2.append("}");
    _builder_2.newLine();
    this.myFixture.checkResult(_builder_2.toString());
  }
  
  public void testRenameWithSeveralCandidates() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("foo {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("import bar.*");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bar {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("datatype String");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("String abc");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("datatype Strin<caret>g2");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.myFixture.configureByText("Foo.mydsl", _builder.toString());
    this.myFixture.renameElementAtCaret("String");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("foo {");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("import bar.*");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("bar {");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("datatype String");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("entity Foo {");
    _builder_1.newLine();
    _builder_1.append("        ");
    _builder_1.append("String abc");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("}");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("datatype String");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.myFixture.checkResult(_builder_1.toString());
  }
  
  public PsiReferenceEObject getType(final PsiElement it) {
    Iterable<PsiReferenceEObject> _psiEReferences = this.getPsiEReferences(it, MyDslPackage.Literals.PROPERTY__TYPE);
    return IterableExtensions.<PsiReferenceEObject>head(_psiEReferences);
  }
  
  public Iterable<PsiNamedEObject> getNamespaces(final PsiElement it) {
    return this.getPsiNamedEObjects(it, MyDslPackage.Literals.NAMESPACE);
  }
  
  public Iterable<PsiNamedEObject> getEntities(final PsiElement it) {
    return this.getPsiNamedEObjects(it, MyDslPackage.Literals.ENTITY);
  }
  
  public Iterable<PsiNamedEObject> getDatatypes(final PsiElement it) {
    return this.getPsiNamedEObjects(it, MyDslPackage.Literals.DATATYPE);
  }
  
  public Iterable<PsiNamedEObject> getProperties(final PsiElement it) {
    return this.getPsiNamedEObjects(it, MyDslPackage.Literals.PROPERTY);
  }
  
  public Iterable<PsiNamedEObject> getPsiNamedEObjects(final PsiElement it, final EClass eClass) {
    PsiElement[] _children = it.getChildren();
    Iterable<PsiNamedEObject> _filter = Iterables.<PsiNamedEObject>filter(((Iterable<?>)Conversions.doWrapArray(_children)), PsiNamedEObject.class);
    final Function1<PsiNamedEObject, Boolean> _function = new Function1<PsiNamedEObject, Boolean>() {
      public Boolean apply(final PsiNamedEObject it) {
        EClass _eClass = it.getEClass();
        return Boolean.valueOf(eClass.isSuperTypeOf(_eClass));
      }
    };
    return IterableExtensions.<PsiNamedEObject>filter(_filter, _function);
  }
  
  public Iterable<PsiReferenceEObject> getPsiEReferences(final PsiElement it, final EReference eReference) {
    PsiElement[] _children = it.getChildren();
    Iterable<PsiReferenceEObject> _filter = Iterables.<PsiReferenceEObject>filter(((Iterable<?>)Conversions.doWrapArray(_children)), PsiReferenceEObject.class);
    final Function1<PsiReferenceEObject, Boolean> _function = new Function1<PsiReferenceEObject, Boolean>() {
      public Boolean apply(final PsiReferenceEObject it) {
        EReference _eReference = it.getEReference();
        return Boolean.valueOf(Objects.equal(_eReference, eReference));
      }
    };
    return IterableExtensions.<PsiReferenceEObject>filter(_filter, _function);
  }
}
