package com.brasee.games.chess.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.brasee.chess.Game;
import com.brasee.games.chess.web.commands.ChessCommand;
import com.brasee.games.chess.web.commands.ChessCommandFactory;

public class ChessJsonController extends AbstractController {

	public static String CHESS_GAME_SESSION_VARIABLE = "chessGame";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Game game = createOrRetrieveSessionGame(request.getSession());
		ChessCommand command = ChessCommandFactory.createCommand(request);
		JsonView jsonView = command.processCommand(request, game);
		return new ModelAndView(jsonView);
	}
	
	private Game createOrRetrieveSessionGame(HttpSession session) {
		Game game = (Game)session.getAttribute(CHESS_GAME_SESSION_VARIABLE); 
		if (game == null) {
			game = new Game();
			game.initializeBoard();
			session.setAttribute(CHESS_GAME_SESSION_VARIABLE, game);
		}
		return game;
	}

}
