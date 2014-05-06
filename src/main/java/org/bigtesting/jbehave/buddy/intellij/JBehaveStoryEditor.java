package org.bigtesting.jbehave.buddy.intellij;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationListener;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.SettingsSavingComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentAdapter;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.fileEditor.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vcs.AbstractVcs;
import com.intellij.openapi.vcs.FilePath;
import com.intellij.openapi.vcs.ObjectsConvertor;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vfs.ReadonlyStatusHandler;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.bigtesting.jbehave.buddy.core.ui.Screen;
import org.bigtesting.jbehave.buddy.core.ui.ScreenContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.diagnostic.Logger;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class JBehaveStoryEditor implements FileEditor {

    private static final Logger log = Logger.getInstance("JBehaveBuDDy-FileEditor");

    private final VirtualFile virtualFile;
    private final Project project;
    private final Document document;

    private Screen screen;

    public JBehaveStoryEditor(Project project, final VirtualFile virtualFile) {

        this.virtualFile = virtualFile;
        this.project = project;

        this.document = FileDocumentManager.getInstance().getDocument(virtualFile);
        document.addDocumentListener(new DocumentAdapter() {
            @Override
            public void documentChanged(DocumentEvent e) {
                log.info("document changed");
                refreshScreen();
            }
        });

        this.screen = createScreen(virtualFile);
    }

    private Screen createScreen(VirtualFile virtualFile) {

        return new Screen(VfsUtil.virtualToIoFile(virtualFile), new ScreenContext() {
            public void close() {}
            public void setTitle(String title) {}
            public void enableSaving(boolean enable) {}
            public boolean isDialog() { return false; }
            public void logException(Throwable e) { log.error(e); }
        });
    }

    private void refreshScreen() {

        if (isDisposed()) return;

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            public void run() {
                FileEditorManager manager = FileEditorManager.getInstance(project);
                manager.closeFile(virtualFile);
                manager.openFile(virtualFile, true);
            }
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

    public void save() {
        if (screen.isExistingStoryChanged()) {

            if (!virtualFile.isWritable()) {
                //it may be under version control
                ReadonlyStatusHandler.ensureFilesWritable(project, virtualFile);
            }

            if (!virtualFile.isWritable()) {
                //the file may not have been checked out
                JOptionPane.showMessageDialog(screen.getMainPanel(),
                        "The story file is not writable. Any changes made will not be saved.",
                        "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            screen.saveExistingStoryFile();
        }
    }

    public boolean isDisposed() {
        return Disposer.isDisposed(this);
    }

    @NotNull
    public FileEditorState getState(@NotNull FileEditorStateLevel fileEditorStateLevel) {

        return new JBehaveStoryFileEditorState(this);
    }

    public void setState(@NotNull FileEditorState fileEditorState) {
    }

    public boolean isModified() {

        return false;
    }

    public boolean isValid() {

        return virtualFile.isValid();
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

        save();

        Disposer.dispose(this);
    }

    @Nullable
    public <T> T getUserData(@NotNull Key<T> tKey) {
        return null;
    }

    public <T> void putUserData(@NotNull Key<T> tKey, @Nullable T t) {

    }
}
