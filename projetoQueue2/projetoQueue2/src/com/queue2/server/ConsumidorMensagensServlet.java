package com.queue2.server;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;


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
			// Coloco as regras de neg�cio aqui
			//
			
			Queue q = QueueFactory.getQueue("fila-mensagens");

			List<TaskHandle> tasks = q.leaseTasks(3600, TimeUnit.SECONDS, 100);
			
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