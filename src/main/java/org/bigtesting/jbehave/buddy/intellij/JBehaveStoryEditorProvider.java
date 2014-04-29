package org.bigtesting.jbehave.buddy.intellij;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class JBehaveStoryEditorProvider implements ApplicationComponent, FileEditorProvider {

    public void initComponent() {

    }

    public void disposeComponent() {

    }

    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {

        return virtualFile.getFileType() instanceof JBehaveStoryFileType;
    }

    @NotNull
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {

        return new JBehaveStoryEditor(virtualFile);
    }

    public void disposeEditor(@NotNull FileEditor fileEditor) {

    }

    @NotNull
    public FileEditorState readState(@NotNull Element element, @NotNull Project project, @NotNull VirtualFile virtualFile) {
        return new FileEditorState()
        {
            public boolean canBeMergedWith(FileEditorState otherState,
                                           FileEditorStateLevel level)
            {
                return false;
            }
        };
    }

    public void writeState(@NotNull FileEditorState fileEditorState, @NotNull Project project, @NotNull Element element) {

    }

    @NotNull
    public String getEditorTypeId() {
        return getComponentName();
    }

    @NotNull
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.NONE;
    }

    @NotNull
    public String getComponentName() {
        return "JBehave BuDDy Story Editor";
    }
}
