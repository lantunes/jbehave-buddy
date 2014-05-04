package org.bigtesting.jbehave.buddy.intellij;

import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;

public class JBehaveStoryFileEditorState implements FileEditorState {

    private final JBehaveStoryEditor editor;

    public JBehaveStoryFileEditorState(JBehaveStoryEditor editor) {
        this.editor = editor;
    }

    public JBehaveStoryEditor getEditor() {
        return editor;
    }

    public boolean canBeMergedWith(FileEditorState otherState, FileEditorStateLevel level) {
        return false;
    }
}
