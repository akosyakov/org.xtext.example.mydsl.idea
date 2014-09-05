package org.xtext.example.mydsl.idea.lang.psi;

import com.intellij.psi.util.PsiModificationTracker;
import org.eclipse.xtext.psi.BaseXtextCodeBlockModificationListener;
import org.xtext.example.mydsl.idea.lang.MyDslLanguage;

@SuppressWarnings("all")
public class MyDslCodeBlockModificationListener extends BaseXtextCodeBlockModificationListener {
  public MyDslCodeBlockModificationListener(final PsiModificationTracker psiModificationTracker) {
    super(MyDslLanguage.INSTANCE, psiModificationTracker);
  }
}
