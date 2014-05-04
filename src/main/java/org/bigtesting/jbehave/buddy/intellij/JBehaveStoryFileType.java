package org.bigtesting.jbehave.buddy.intellij;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class JBehaveStoryFileType extends LanguageFileType {

    public static final String EXTENSION = "story";

    public JBehaveStoryFileType() {
        super(new JBehaveStoryLanguage());
    }

    @NotNull
    public String getName() {
        return "JBehave Story";
    }

    @NotNull
    public String getDescription() {
        return "JBehave Story file";
    }

    @NotNull
    public String getDefaultExtension() {
        return EXTENSION;
    }

    @Nullable
    public Icon getIcon() {
        return AllIcons.FileTypes.Any_type;
    }
}
