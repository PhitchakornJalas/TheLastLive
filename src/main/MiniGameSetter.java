package main;

import java.util.Random;

public class MiniGameSetter {

	GamePanel gp;
	
	//	minigame0
    public String Answer = "";
    String[] questions = new String[1];
    String[][] answers = new String[1][2];
    boolean corect = false;
    boolean goingToTarget = true;
    boolean moving = true;
    boolean waitingAtTarget = false;
    int pathIndex = 0;
    int waitCounter = 0;
    final int WAIT_DURATION = 180;
    int movementStage = 0; // เริ่มจาก 0: ไป 55
    private int[] targetPath;
    private String currentQuestion;
    private String[] options;

    boolean movementInitiated = false;

	
	public MiniGameSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void miniGame(int minigameIndex) {
		
		if (minigameIndex == 0) {
			
			switch (Answer.trim()) {
			case "เริ่ม": 
				if (gp.ui.glassNum != 49) {
					gp.ui.miniGame0Counter++;

					if (gp.ui.miniGame0Counter >= gp.ui.miniGame0Delay) {
						gp.ui.glassNum++;
						gp.ui.miniGame0Counter = 0;
					}
					
				} else if (gp.ui.glassNum == 49) {
					questions[0] = "เลือกคำถามที่ต้องการถาม";
					answers[0][0] = "มีวิญญาณในที่นี้อยู่กับเราแล้วใช่หรือไม่";
					answers[0][1] = "คุณอยู่ในแก้วแล้วใช่หรือไม่";
					
					gp.gameState = gp.QA_STATE;
					gp.ui.questionManager.startQuestions(questions, answers);
				}
				break;
				
			case "มีวิญญาณในที่นี้อยู่กับเราแล้วใช่หรือไม่": 
			case "คุณอยู่ในแก้วแล้วใช่หรือไม่":
				if (!movementInitiated) {
				    int[] path = {99};
				    String question = "เลือกคำถามที่ต้องการถาม";
				    String[] ans = {"คุณคือวิญญาณที่เสียชีวิตในที่แห่งนี้ใช่หรือไม่", "คุณเป็นผู้ชายหรือผู้หญิง"};

				    initiateSpiritMovement(path, question, ans);
				    movementInitiated = true;
				}
				break;

			case "คุณคือวิญญาณที่เสียชีวิตในที่แห่งนี้ใช่หรือไม่": 
				if (!movementInitiated) {
				    int[] path = {99};
				    String question = "เลือกคำถามที่ต้องการถาม";
				    String[] ans = {"คุณเสียชีวิตได้อย่างไร", "คุณมีอะไรให้ช่วยไหม"};

				    initiateSpiritMovement(path, question, ans);
				    movementInitiated = true;
				}
				break;

			case "คุณเป็นผู้ชายหรือผู้หญิง": 
				if (!movementInitiated) {
				    int[] path = {102};
				    String question = "เลือกคำถามที่ต้องการถาม";
				    String[] ans = {"คุณเสียชีวิตได้อย่างไร", "คุณมีอะไรให้ช่วยไหม"};

				    initiateSpiritMovement(path, question, ans);
				    movementInitiated = true;
				}
				break;
				
			case "คุณเสียชีวิตได้อย่างไร": 
				if (!movementInitiated) {
				    int[] path = {3, 60, 18, 0, 32, 32, 30}; // ฆาตกรรม
				    String question = "เลือกคำถามที่ต้องการถาม";
				    String[] ans = {"เราพอจะช่วยเหลือคุณได้อย่างไรได้บ้าง", "ฉันไม่อยากเล่นแล้ว"};

				    initiateSpiritMovement(path, question, ans);
				    movementInitiated = true;
				}
				break;
				
			case "คุณมีอะไรให้ช่วยไหม":
			case "เราพอจะช่วยเหลือคุณได้อย่างไรได้บ้าง":
				if (!movementInitiated) {
				    int[] path = {30, 60, 71, 20, 22, 0, 67}; // มาแทนฉัน

				    initiateSpiritMovement(path, null, null);
				    movementInitiated = true;
				}
				break;
				
			case "ฉันไม่อยากเล่นแล้ว":
				if (!movementInitiated) {
				    int[] path = {18, 60, 31}; // ตาย

				    initiateSpiritMovement(path, null, null);
				    movementInitiated = true;
				}
				break;
			    
			}
			
		}
		
	}
			    
	
	public void initiateSpiritMovement(int[] targets, String finalQuestion, String[] finalAnswers) {
	    this.moving = true;
	    this.waitingAtTarget = false;
	    this.waitCounter = 0;
	    this.pathIndex = 0;
	    this.movementStage = 0;
	    this.goingToTarget = true;
	    this.targetPath = targets;
	    this.currentQuestion = finalQuestion;
	    this.options = finalAnswers;
	}

	
	public void updateSpiritGlassMovement() {
	    if (!moving) return;

	    if (waitingAtTarget) {
	        waitCounter++;
	        if (waitCounter >= WAIT_DURATION) {
	            waitingAtTarget = false;
	            waitCounter = 0;

	            switch (movementStage) {
	                case 2: // รอที่ target เสร็จแล้ว ไปซ้ายสุดของแถว
	                    movementStage = 3;
	                    break;

	                case 3: // ไปซ้ายสุดเสร็จ → ไป 42
	                    movementStage = 4;
	                    break;

	                case 4: // ไป 42 เสร็จ → ไป 49
	                    movementStage = 5;
	                    break;

	                case 5: // ถึง 49 แล้ว ตรวจว่ามี target ถัดไปไหม
	                    pathIndex++;
	                    if (targetPath != null && pathIndex < targetPath.length) {
	                        movementStage = 0; // ไป 55 ใหม่
	                    } else {
	                        movementStage = 6; // เริ่มถามคำถาม
	                    }
	                    break;
	            }
	        }
	        return;
	    }

	    gp.ui.miniGame0Counter++;
	    if (gp.ui.miniGame0Counter < gp.ui.miniGame0Delay) return;
	    gp.ui.miniGame0Counter = 0;

	    int current = gp.ui.glassNum;
	    int destination = 49;

	    switch (movementStage) {
	        case 0:
	            destination = 55;
	            break;

	        case 1:
	        	if (targetPath == null || pathIndex >= targetPath.length) return;
	            int target = targetPath[pathIndex];
	            if (current / 14 != target / 14) {
	                destination = (target > 55) ? current + 14 : current - 14;
	            } else {
	                destination = target;
	            }
	            break;

	        case 2:
	            destination = current; // รอที่ target
	            break;

	        case 3:
	            destination = (current / 14) * 14; // ไปซ้ายสุดแถว
	            break;

	        case 4:
	            if (current / 14 != 42 / 14) {
	                destination = (current < 42) ? current + 14 : current - 14;
	            } else {
	                destination = 42;
	            }
	            break;

	        case 5:
	            destination = 49;
	            break;

	        case 6:
	            moving = false;
	            movementInitiated = false;

	            // ** ใส่เช็คคำถามและเริ่มถามที่นี่ **
	            if (currentQuestion == null || options == null || options.length < 2) {
	                gp.gameState = gp.LOADING_STATE;
	                gp.ui.loadingScreenState = 1;
	                gp.stage.mapId = 2;
	                break;
	            }

	            questions[0] = currentQuestion;
	            answers[0][0] = options[0];
	            answers[0][1] = options[1];
	            gp.gameState = gp.QA_STATE;
	            gp.ui.questionManager.startQuestions(questions, answers);

	            return;
	    }

	    // เคลื่อนที่ทีละช่อง
	    if (current == destination) {
	        switch (movementStage) {
	            case 0:
	                movementStage = 1;
	                break;

	            case 1:
	                movementStage = 2;
	                waitingAtTarget = true;
	                break;

	            case 3:
	                movementStage = 4;
	                break;

	            case 4:
	                movementStage = 5;
	                break;

	            case 5:
	                waitingAtTarget = true;
	                break;
	        }
	    } else {
	        if (current / 14 != destination / 14) {
	            gp.ui.glassNum += (current < destination) ? 14 : -14;
	        } else {
	            gp.ui.glassNum += (current < destination) ? 1 : -1;
	        }
	    }
	}

	
}
