package org.xtext.example.mydsl.idea.lang;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class MyDslFileTypeFactory extends FileTypeFactory {

	public void createFileTypes(@NotNull FileTypeConsumer consumer) {
		consumer.consume(org.xtext.example.mydsl.idea.lang.MyDslFileType.INSTANCE, org.xtext.example.mydsl.idea.lang.MyDslFileType.DEFAULT_EXTENSION);
	}

}
