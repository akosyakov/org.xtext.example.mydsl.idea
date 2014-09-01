package org.xtext.example.domainmodel.idea.server

import com.intellij.compiler.server.BuildProcessParametersProvider
import com.intellij.ide.plugins.PluginManager
import com.intellij.openapi.extensions.PluginId

class DomainmodelBuildProcessParametersProvider extends BuildProcessParametersProvider {

	override getClassPath() {
		val plugin = PluginManager.getPlugin(PluginId.getId("org.xtext.example.domainmodel.Domainmodel"));
		val path = plugin.path.path

		#[
			path + "/bin",
			path + "/../org.xtext.example.domainmodel/bin"
		]
	}

}
