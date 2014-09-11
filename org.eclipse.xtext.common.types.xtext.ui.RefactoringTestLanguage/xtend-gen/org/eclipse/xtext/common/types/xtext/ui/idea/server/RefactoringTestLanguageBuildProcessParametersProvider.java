package org.eclipse.xtext.common.types.xtext.ui.idea.server;

import com.intellij.compiler.server.BuildProcessParametersProvider;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public class RefactoringTestLanguageBuildProcessParametersProvider extends BuildProcessParametersProvider {
  public List<String> getClassPath() {
    List<String> _xblockexpression = null;
    {
      PluginId _id = PluginId.getId("org.eclipse.xtext.common.types.xtext.ui.RefactoringTestLanguage");
      final IdeaPluginDescriptor plugin = PluginManager.getPlugin(_id);
      File _path = plugin.getPath();
      final String path = _path.getPath();
      _xblockexpression = Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList((path + "/bin"), (path + "/../org.eclipse.xtext.common.types.xtext.ui.RefactoringTestLanguage/bin")));
    }
    return _xblockexpression;
  }
}
