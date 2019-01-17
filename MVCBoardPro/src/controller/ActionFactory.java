package controller;

import controller.action.Action;
import controller.action.BoardListAction;
import controller.action.BoardReplyAction;
import controller.action.BoardReplyFormAction;
import controller.action.BoardViewAction;
import controller.action.BoardWriteAction;
import controller.action.BoardWriteFormAction;

public class ActionFactory {
	private ActionFactory() {}
	private static ActionFactory instance=new ActionFactory();
	public static ActionFactory getInstance() {
		return instance;
	}
	
	public Action getAction(String command){
		Action action=null;
		System.out.println(command);
		if(command.equals("/boardList.bo")) {
			action=new BoardListAction();
		}else if(command.equals("/boardWriteForm.bo")) {
			action=new BoardWriteFormAction();
		}else if(command.equals("/boardWrite.bo")) {
			action=new BoardWriteAction();
		}else if(command.equals("/boardView.bo")) {
			action=new BoardViewAction();
		}else if(command.equals("/boardReplyForm.bo")) {
			action=new BoardReplyFormAction();
		}else if(command.equals("/boardReply.bo")) {
			action=new BoardReplyAction();
		}
	
		return action;
	}

}
