package org.bigtesting.jbehave.buddy.intellij;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class JBehaveStoryFileType extends LanguageFileType {

    private static final Logger log = Logger.getInstance("JBehaveBuDDy-StoryFileType");

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
        return IconLoader.getIcon("/icon.png");
    }
}
