package org.xtext.example.mydsl.idea.server

import com.intellij.compiler.server.BuildProcessParametersProvider
import com.intellij.ide.plugins.PluginManager
import com.intellij.openapi.extensions.PluginId

class MyDslBuildProcessParametersProvider extends BuildProcessParametersProvider {

	override getClassPath() {
		val plugin = PluginManager.getPlugin(PluginId.getId("org.xtext.example.mydsl.MyDsl"));
		val path = plugin.path.path

		#[
			path + "/bin",
			path + "/../org.xtext.example.mydsl/bin"
		]
	}

}
