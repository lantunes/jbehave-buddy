package org.bigtesting.jbehave.buddy.intellij;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.bigtesting.jbehave.buddy.core.ui.Screen;
import org.bigtesting.jbehave.buddy.core.ui.ScreenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class JBehaveStoryEditor implements FileEditor, FileEditorManagerListener {

    private Screen screen;

    public JBehaveStoryEditor(VirtualFile virtualFile) {
        this.screen = new Screen(VfsUtil.virtualToIoFile(virtualFile), new ScreenContext() {
            public void close() {}
            public void setTitle(String title) {}
            public void enableSaving(boolean enable) {}
        });
    }

    @NotNull
    public JComponent getComponent() {

        return screen.getMainPanel();
    }

    @Nullable
    public JComponent getPreferredFocusedComponent() {

        return screen.getMainPanel();
    }

    @NotNull
    public String getName() {

        return "JBehave BuDDy Story Editor";
    }

    @NotNull
    public FileEditorState getState(@NotNull FileEditorStateLevel fileEditorStateLevel) {

        return new FileEditorState()
        {
            public boolean canBeMergedWith(FileEditorState otherState,
                                           FileEditorStateLevel level)
            {
                return false;
            }
        };
    }

    public void setState(@NotNull FileEditorState fileEditorState) {

    }

    public boolean isModified() {
        return false;
    }

    public boolean isValid() {
        return true;
    }

    public void selectNotify() {

    }

    public void deselectNotify() {

    }

    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Nullable
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Nullable
    public StructureViewBuilder getStructureViewBuilder() {
        return null;
    }

    public void dispose() {

    }

    public void fileOpened(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {

    }

    public void fileClosed(@NotNull FileEditorManager fileEditorManager, @NotNull VirtualFile virtualFile) {

    }

    public void selectionChanged(@NotNull FileEditorManagerEvent fileEditorManagerEvent) {

    }

    @Nullable
    public <T> T getUserData(@NotNull Key<T> tKey) {
        return null;
    }

    public <T> void putUserData(@NotNull Key<T> tKey, @Nullable T t) {

    }
}
