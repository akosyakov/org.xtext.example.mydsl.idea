package org.xtext.example.mydsl.idea.server;

import java.util.Arrays;
import java.util.List;

import com.intellij.compiler.server.BuildProcessParametersProvider;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;

public class MyDslBuildProcessParametersProvider extends BuildProcessParametersProvider {

	@Override
	public List<String> getClassPath() {
		IdeaPluginDescriptor plugin = PluginManager.getPlugin(PluginId.getId("org.xtext.example.mydsl.idea"));
		String path = plugin.getPath().getPath();
		
		return Arrays.asList(
				path + "/bin",
				path + "/../org.xtext.example.mydsl/bin"
		);
	}

}
