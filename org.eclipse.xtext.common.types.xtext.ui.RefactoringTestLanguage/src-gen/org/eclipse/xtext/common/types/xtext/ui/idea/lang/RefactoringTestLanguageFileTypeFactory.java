package org.eclipse.xtext.common.types.xtext.ui.idea.lang;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class RefactoringTestLanguageFileTypeFactory extends FileTypeFactory {

	public void createFileTypes(@NotNull FileTypeConsumer consumer) {
		consumer.consume(org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageFileType.INSTANCE, org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageFileType.DEFAULT_EXTENSION);
	}

}
