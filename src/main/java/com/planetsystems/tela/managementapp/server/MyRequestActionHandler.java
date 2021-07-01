package com.planetsystems.tela.managementapp.server;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.ProcessingException;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.planetsystems.tela.dto.response.SystemErrorDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.dto.response.TokenFeedbackDTO;
import com.planetsystems.tela.managementapp.server.handlers.LoginPresenterHandler;
import com.planetsystems.tela.managementapp.server.handlers.MainPresenterHandler;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.AuthRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupSystemMenuCommand;

public class MyRequestActionHandler implements ActionHandler<MyRequestAction, MyRequestResult> {

	@Override
	public MyRequestResult execute(MyRequestAction action, ExecutionContext context) throws ActionException {
		try {
			String command = (String) action.getRequestBody().get(MyRequestAction.COMMAND);
			switch (command) {
			case AuthRequestCommand.LOGIN:
				MyRequestResult result = new MyRequestResult();
				result.setResponseText(LoginPresenterHandler.login(action));
				return result;

			case SystemUserGroupRequestCommand.LOGGEDIN_SYSTEM_USER_GROUP:
				MyRequestResult result2 = new MyRequestResult();
				result2.setSystemUserGroupResponseDTO(LoginPresenterHandler.loggedUserGroup(action));
				return result2;
				
			case AuthRequestCommand.RESET_PASSWORD:
				MyRequestResult result3 = new MyRequestResult();
				result3.setResponseText(LoginPresenterHandler.resetPassword(action));
				return result3;
				
			case AuthRequestCommand.CHANGE_PASSWORD:
				MyRequestResult result4 = new MyRequestResult();
				result4.setResponseText(MainPresenterHandler.changePassword(action));
				return result4;
				
			case SystemUserGroupSystemMenuCommand.LOGGED_USER_SYSTEM_MENU:
				MyRequestResult result5 = new MyRequestResult();
				//result4.setResponseText(MainPresenterHandler.loggedUserSystemMenus(action));
				return result5;
				

			default:
				System.out.println("UNKNOWN COMMAND");
				break;
			}
		} catch (ForbiddenException exception) {
			exception.printStackTrace();
			String message = "Session timed out. Please login in again";
			// error.setStatus("" + exception.getResponse().getStatus());
			MyRequestResult e = new MyRequestResult();
			e.setResponseInteger(createError(message, exception.getResponse().getStatusInfo().getStatusCode()));
			return e;

		} catch (ProcessingException exception) {
			exception.printStackTrace();
			String message = exception.getMessage()
					+ "\n There is a problem accessing the processing data processing engine. Please contact Systems Admin.";
			MyRequestResult e = new MyRequestResult();
			e.setResponseInteger(createError(message, 8082));
			return e;

		} catch (Exception exception) {
			exception.printStackTrace();
			String message = "System Error,  Please contact Systems Admin.";
			MyRequestResult e = new MyRequestResult();
			e.setResponseInteger(createError(message, 500));
			return e;
		}

		return null;
	}

	@Override
	public Class<MyRequestAction> getActionType() {
		// TODO Auto-generated method stub
		return MyRequestAction.class;
	}

	@Override
	public void undo(MyRequestAction action, MyRequestResult result, ExecutionContext context) throws ActionException {
		// TODO Auto-generated method stub

	}

	private SystemResponseDTO<Integer> createError(String message, int code) {
		SystemResponseDTO<Integer> systemErrorDTO = new SystemResponseDTO<Integer>();
		systemErrorDTO.setData(code);
		systemErrorDTO.setMessage(message);
		return systemErrorDTO;
	}

}
