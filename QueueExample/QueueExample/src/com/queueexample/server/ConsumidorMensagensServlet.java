package com.queueexample.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ConsumidorMensagensServlet extends HttpServlet {
	private static final Logger _logger = Logger
			.getLogger(ConsumidorMensagensServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String mensagemResposta = "";
		resp.setContentType("text/plain");
		try {
			String mensagem = req.getParameter("msg");
			_logger.info("Mensagem que chegou : "
					+ mensagem);
			//
			// Coloco as regras de negócio aqui
			//
			mensagemResposta = "SUCCESSO: Mensagem chegou com sucesso";
			_logger.info(mensagemResposta);
			resp.getWriter().println(mensagemResposta);
		} catch (Exception ex) {
			mensagemResposta = "Falha: " + ex.getMessage();
			_logger.info(mensagemResposta);
			resp.getWriter().println(mensagemResposta);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}