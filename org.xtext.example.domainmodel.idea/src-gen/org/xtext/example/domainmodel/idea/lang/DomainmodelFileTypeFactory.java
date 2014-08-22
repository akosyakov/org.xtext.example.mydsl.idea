package org.xtext.example.domainmodel.idea.lang;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class DomainmodelFileTypeFactory extends FileTypeFactory {

	public void createFileTypes(@NotNull FileTypeConsumer consumer) {
		consumer.consume(org.xtext.example.domainmodel.idea.lang.DomainmodelFileType.INSTANCE, org.xtext.example.domainmodel.idea.lang.DomainmodelFileType.DEFAULT_EXTENSION);
	}

}
