package org.xtext.example.mydsl.validation;

import org.eclipse.xtext.validation.Check;
import org.xtext.example.mydsl.myDsl.Entity;
import org.xtext.example.mydsl.myDsl.MyDslPackage;
import org.xtext.example.mydsl.validation.AbstractMyDslValidator;

/**
 * Custom validation rules.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
@SuppressWarnings("all")
public class MyDslValidator extends AbstractMyDslValidator {
  public final static String INVALID_NAME = "invalidName";
  
  @Check
  public void checkGreetingStartsWithCapital(final Entity entity) {
    String _name = entity.getName();
    char _charAt = _name.charAt(0);
    boolean _isUpperCase = Character.isUpperCase(_charAt);
    boolean _not = (!_isUpperCase);
    if (_not) {
      this.warning("Name should start with a capital", MyDslPackage.Literals.ENTITY__NAME, MyDslValidator.INVALID_NAME);
    }
  }
}
