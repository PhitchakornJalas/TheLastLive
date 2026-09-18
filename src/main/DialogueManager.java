package main;

public class DialogueManager {

    private String[] dialogueLines;
    private int currentLineIndex;
    private boolean isActive;

    public DialogueManager() {
        this.dialogueLines = new String[0];
        this.currentLineIndex = 0;
        this.isActive = false;
    }

    public void startDialogue(String fullDialogue) {
        this.dialogueLines = fullDialogue.split("\n");
        this.currentLineIndex = 0;
        this.isActive = true;
    }

    public String getCurrentLine() {
        if (isActive && currentLineIndex < dialogueLines.length) {
            return dialogueLines[currentLineIndex];
        }
        return "";
    }

    public void nextLine() {
        if (isActive) {
            currentLineIndex++;
            if (currentLineIndex >= dialogueLines.length) {
                endDialogue();
            }
        }
    }

    public boolean isDialogueFinished() {
        return !isActive;
    }

    public void endDialogue() {
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }
}

