package org.bigtesting.jbehave.buddy.intellij;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class JBehaveStoryEditorProvider implements ApplicationComponent, FileEditorProvider {

    private static final Logger log = Logger.getInstance("JBehaveBuDDy-FileEditorProvider");

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {

        return virtualFile.getFileType() instanceof JBehaveStoryFileType;
    }

    @NotNull
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {

        return new JBehaveStoryEditor(project, virtualFile);
    }

    public void disposeEditor(@NotNull FileEditor fileEditor) {

        fileEditor.dispose();
    }

    @NotNull
    public FileEditorState readState(@NotNull Element element, @NotNull Project project, @NotNull VirtualFile virtualFile) {

        return new FileEditorState() {
            public boolean canBeMergedWith(FileEditorState otherState, FileEditorStateLevel level) { return false; }
        };
    }

    public void writeState(@NotNull FileEditorState fileEditorState, @NotNull Project project, @NotNull Element element) {

        if (fileEditorState instanceof JBehaveStoryFileEditorState) {
            JBehaveStoryFileEditorState state = (JBehaveStoryFileEditorState)fileEditorState;
            JBehaveStoryEditor editor = state.getEditor();
            if (editor != null && !editor.isDisposed()) {
                state.getEditor().save();
            }
        }
    }

    @NotNull
    public String getEditorTypeId() {
        return getComponentName();
    }

    @NotNull
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }

    @NotNull
    public String getComponentName() {
        return "JBehave BuDDy Story Editor";
    }
}
