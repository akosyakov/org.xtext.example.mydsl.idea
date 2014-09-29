package org.eclipse.xtext.idea.resource

import com.google.inject.Singleton
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.psi.PsiEObject
import org.eclipse.xtext.psi.impl.BaseXtextFile
import org.eclipse.xtext.resource.DerivedStateAwareResource
import org.eclipse.xtext.resource.ISynchronizable
import org.eclipse.xtext.service.OperationCanceledError

@Singleton
class ResourceInitializationService {

	def Resource ensureFullyInitialized(Object context) {
		try {
			switch context {
				PsiEObject:
					context.resource.ensureFullyInitialized
				BaseXtextFile:
					context.resource.ensureFullyInitialized
				DerivedStateAwareResource:
					if (context instanceof ISynchronizable<?>) {
						context.execute [
							context.doEnsureFullyInitialized
						]
					} else {
						context.doEnsureFullyInitialized
					}
				default:
					null
			}
		} catch (OperationCanceledError e) {
			throw e.wrapped
		}
	}

	protected def Resource doEnsureFullyInitialized(DerivedStateAwareResource context) {
		val deliver = context.eDeliver
		try {
			context.eSetDeliver(false)
			context.installDerivedState(false)
		} finally {
			context.eSetDeliver(deliver)
		}
		context
	}

}
