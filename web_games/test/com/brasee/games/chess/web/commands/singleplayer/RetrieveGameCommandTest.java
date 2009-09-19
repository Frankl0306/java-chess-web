package com.brasee.games.chess.web.commands.singleplayer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.brasee.chess.Game;
import com.brasee.games.chess.web.ChessSingleClientJsonController;
import com.brasee.games.chess.web.JsonView;
import com.brasee.games.chess.web.commands.ChessCommand;

public class RetrieveGameCommandTest {

	@Test
	public void testRetrieveGameReturnsCorrectJson() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		Game existingGame = new Game();
		session.setAttribute(ChessSingleClientJsonController.CHESS_SINGLE_CLIENT_GAME_SESSION_VARIABLE, existingGame);
		request.setSession(session);
		request.addParameter("command", "retrieve_game");

		ChessCommand command = SinglePlayerChessCommandFactory.createCommand(request);
		JsonView view = command.processCommand(request, existingGame);
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		String jsonStringResult = null;
		try {
			view.render(null, request, response);
			jsonStringResult = response.getContentAsString();
		}
		catch (Exception e) {
			fail("Should not have thrown an exception");
		}
		
		String expectedResult = "{\"players_turn\":\"white\",\"pieces\":[],\"move_notations\":[],\"captured_pieces_black\":[],\"captured_pieces_white\":[],\"move_index\":\"0\"}"; 
		assertEquals(expectedResult, jsonStringResult);
	}
}