package org.xtext.example.mydsl.idea.tests.scoping;

import com.google.common.collect.Iterators;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import java.util.Iterator;
import java.util.Set;
import junit.framework.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.linking.lazy.CrossReferenceDescription;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.PsiEObject;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.xtext.example.mydsl.myDsl.Entity;
import org.xtext.example.mydsl.myDsl.MyDslPackage;

@SuppressWarnings("all")
public class ScopeProviderTest extends LightCodeInsightFixtureTestCase {
  public void testImports() {
    final PsiFile file = this.myFixture.configureByText("import.mydsl", "import foo.bar.*");
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("foo.bar {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Person {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("String name");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("datatype String");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.myFixture.configureByText("foo.mydsl", _builder.toString());
    Language _language = file.getLanguage();
    final IXtextLanguage language = ((IXtextLanguage) _language);
    final IQualifiedNameConverter nameConverter = language.<IQualifiedNameConverter>getInstance(IQualifiedNameConverter.class);
    final CrossReferenceDescription.CrossReferenceDescriptionProvider crossReferenceDescriptionProvider = language.<CrossReferenceDescription.CrossReferenceDescriptionProvider>getInstance(CrossReferenceDescription.CrossReferenceDescriptionProvider.class);
    PsiElement _firstChild = file.getFirstChild();
    Resource _resource = ((PsiEObject) _firstChild).getResource();
    EList<EObject> _contents = _resource.getContents();
    final EObject fileRoot = IterableExtensions.<EObject>head(_contents);
    final ICrossReferenceDescription crossReferenceDescription = crossReferenceDescriptionProvider.get(fileRoot, MyDslPackage.Literals.FILE__ELEMENTS, null);
    Iterable<IEObjectDescription> _variants = crossReferenceDescription.getVariants();
    final Function1<IEObjectDescription, QualifiedName> _function = new Function1<IEObjectDescription, QualifiedName>() {
      public QualifiedName apply(final IEObjectDescription it) {
        return it.getName();
      }
    };
    Iterable<QualifiedName> _map = IterableExtensions.<IEObjectDescription, QualifiedName>map(_variants, _function);
    final Set<QualifiedName> names = IterableExtensions.<QualifiedName>toSet(_map);
    String _string = names.toString();
    int _size = names.size();
    Assert.assertEquals(_string, 5, _size);
    QualifiedName _qualifiedName = nameConverter.toQualifiedName("Person");
    boolean _contains = names.contains(_qualifiedName);
    Assert.assertTrue(_contains);
    QualifiedName _qualifiedName_1 = nameConverter.toQualifiedName("String");
    boolean _contains_1 = names.contains(_qualifiedName_1);
    Assert.assertTrue(_contains_1);
    QualifiedName _qualifiedName_2 = nameConverter.toQualifiedName("foo.bar");
    boolean _contains_2 = names.contains(_qualifiedName_2);
    Assert.assertTrue(_contains_2);
    QualifiedName _qualifiedName_3 = nameConverter.toQualifiedName("foo.bar.Person");
    boolean _contains_3 = names.contains(_qualifiedName_3);
    Assert.assertTrue(_contains_3);
    QualifiedName _qualifiedName_4 = nameConverter.toQualifiedName("foo.bar.String");
    boolean _contains_4 = names.contains(_qualifiedName_4);
    Assert.assertTrue(_contains_4);
  }
  
  public void testRelativeContext() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("stuff {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("baz {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("datatype String");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Person {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final PsiFile file = this.myFixture.configureByText("relative.mydsl", _builder.toString());
    Language _language = file.getLanguage();
    final IXtextLanguage language = ((IXtextLanguage) _language);
    final IQualifiedNameConverter nameConverter = language.<IQualifiedNameConverter>getInstance(IQualifiedNameConverter.class);
    final CrossReferenceDescription.CrossReferenceDescriptionProvider crossReferenceDescriptionProvider = language.<CrossReferenceDescription.CrossReferenceDescriptionProvider>getInstance(CrossReferenceDescription.CrossReferenceDescriptionProvider.class);
    PsiElement _firstChild = file.getFirstChild();
    Resource _resource = ((PsiEObject) _firstChild).getResource();
    TreeIterator<EObject> _allContents = _resource.getAllContents();
    Iterator<Entity> _filter = Iterators.<Entity>filter(_allContents, Entity.class);
    final Entity entity = IteratorExtensions.<Entity>head(_filter);
    final ICrossReferenceDescription crossReferenceDescription = crossReferenceDescriptionProvider.get(entity, MyDslPackage.Literals.PROPERTY__TYPE, null);
    Iterable<IEObjectDescription> _variants = crossReferenceDescription.getVariants();
    final Function1<IEObjectDescription, QualifiedName> _function = new Function1<IEObjectDescription, QualifiedName>() {
      public QualifiedName apply(final IEObjectDescription it) {
        return it.getName();
      }
    };
    Iterable<QualifiedName> _map = IterableExtensions.<IEObjectDescription, QualifiedName>map(_variants, _function);
    final Set<QualifiedName> names = IterableExtensions.<QualifiedName>toSet(_map);
    String _string = names.toString();
    int _size = names.size();
    Assert.assertEquals(_string, 4, _size);
    QualifiedName _qualifiedName = nameConverter.toQualifiedName("Person");
    boolean _contains = names.contains(_qualifiedName);
    Assert.assertTrue(_contains);
    QualifiedName _qualifiedName_1 = nameConverter.toQualifiedName("stuff.Person");
    boolean _contains_1 = names.contains(_qualifiedName_1);
    Assert.assertTrue(_contains_1);
    QualifiedName _qualifiedName_2 = nameConverter.toQualifiedName("baz.String");
    boolean _contains_2 = names.contains(_qualifiedName_2);
    Assert.assertTrue(_contains_2);
    QualifiedName _qualifiedName_3 = nameConverter.toQualifiedName("stuff.baz.String");
    boolean _contains_3 = names.contains(_qualifiedName_3);
    Assert.assertTrue(_contains_3);
  }
  
  public void testRelativePath() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("stuff {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import baz.*");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("baz {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("datatype String");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Person {}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final PsiFile file = this.myFixture.configureByText("relative.mydsl", _builder.toString());
    Language _language = file.getLanguage();
    final IXtextLanguage language = ((IXtextLanguage) _language);
    final IQualifiedNameConverter nameConverter = language.<IQualifiedNameConverter>getInstance(IQualifiedNameConverter.class);
    final CrossReferenceDescription.CrossReferenceDescriptionProvider crossReferenceDescriptionProvider = language.<CrossReferenceDescription.CrossReferenceDescriptionProvider>getInstance(CrossReferenceDescription.CrossReferenceDescriptionProvider.class);
    PsiElement _firstChild = file.getFirstChild();
    Resource _resource = ((PsiEObject) _firstChild).getResource();
    TreeIterator<EObject> _allContents = _resource.getAllContents();
    Iterator<Entity> _filter = Iterators.<Entity>filter(_allContents, Entity.class);
    final Entity entity = IteratorExtensions.<Entity>head(_filter);
    final ICrossReferenceDescription crossReferenceDescription = crossReferenceDescriptionProvider.get(entity, MyDslPackage.Literals.PROPERTY__TYPE, null);
    Iterable<IEObjectDescription> _variants = crossReferenceDescription.getVariants();
    final Function1<IEObjectDescription, QualifiedName> _function = new Function1<IEObjectDescription, QualifiedName>() {
      public QualifiedName apply(final IEObjectDescription it) {
        return it.getName();
      }
    };
    Iterable<QualifiedName> _map = IterableExtensions.<IEObjectDescription, QualifiedName>map(_variants, _function);
    final Set<QualifiedName> names = IterableExtensions.<QualifiedName>toSet(_map);
    String _string = names.toString();
    int _size = names.size();
    Assert.assertEquals(_string, 5, _size);
    QualifiedName _qualifiedName = nameConverter.toQualifiedName("Person");
    boolean _contains = names.contains(_qualifiedName);
    Assert.assertTrue(_contains);
    QualifiedName _qualifiedName_1 = nameConverter.toQualifiedName("stuff.Person");
    boolean _contains_1 = names.contains(_qualifiedName_1);
    Assert.assertTrue(_contains_1);
    QualifiedName _qualifiedName_2 = nameConverter.toQualifiedName("String");
    boolean _contains_2 = names.contains(_qualifiedName_2);
    Assert.assertTrue(_contains_2);
    QualifiedName _qualifiedName_3 = nameConverter.toQualifiedName("baz.String");
    boolean _contains_3 = names.contains(_qualifiedName_3);
    Assert.assertTrue(_contains_3);
    QualifiedName _qualifiedName_4 = nameConverter.toQualifiedName("stuff.baz.String");
    boolean _contains_4 = names.contains(_qualifiedName_4);
    Assert.assertTrue(_contains_4);
  }
}
