package com.queue2.server;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskHandle;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.ibm.icu.impl.Assert;



@SuppressWarnings("serial")
public class CriadorMensagensServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String mensagemResposta = "";
		resp.setContentType("text/plain");
		try {
			String mensagem = req.getParameter("msg");

			if (mensagem == null)
				throw new Exception("Mensagem vazia.");
			mensagem = mensagem.trim();
			if (mensagem.length() == 0)
				throw new Exception("Mensagem vazia.");
			
			Queue queue = QueueFactory.getQueue("fila-mensagens");
			queue.add(TaskOptions.Builder.withUrl("/consomemensagem").param(
					"msg", mensagem));
			
			mensagemResposta = "Mensagem adicionada a fila.";
			resp.getWriter().println(mensagemResposta);
		} catch (Exception ex) {
			mensagemResposta = "Falha: " + ex.getMessage();
			resp.getWriter().println(mensagemResposta);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}