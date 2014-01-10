package org.mmarini.ratio.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.mmarini.ratio.interpreter.DefsSerializer;
import org.mmarini.ratio.interpreter.Interpreter;
import org.mmarini.ratio.interpreter.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.gson.Gson;

/**
 * Servlet implementation class RatioServlet
 */
@WebServlet(name = "RatioServlet", urlPatterns = { "/ratio" })
@MultipartConfig
public class RatioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory
			.getLogger(RatioServlet.class);
	private final Gson gson;

	/**
	 * Default constructor.
	 */
	public RatioServlet() {
		gson = new Gson();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		logger.debug("Entrering RatioServlet");
		final String cmd = request.getParameter("cmd");
		if ("export".equals(cmd)) {
			final Map<String, String> map = createExpDefs(request);
			response.setContentType("text/xml");
			response.setHeader("Content-Disposition",
					"attachment; filename=ratio.xml");
			try {
				createXmlResult(response.getOutputStream(), map);
			} catch (TransformerConfigurationException
					| TransformerFactoryConfigurationError | SAXException e) {
				logger.error(e.getMessage(), e);
				throw new ServletException(e.getMessage(), e);
			}
		} else if ("import".equals(cmd)) {
			// Create path components to save the file
			try {
				logger.debug("Importing expressions ...");
				final Map<String, String> map = new DefsSerializer()
						.load(request.getPart("file").getInputStream());
				logger.debug("Processing {} expressions ...: {}", map.size(),
						map);
				final String json = createJsonResult(map);
				response.getWriter().print(json);
			} catch (SAXException | ParserConfigurationException e) {
				logger.error(e.getMessage(), e);
				throw new ServletException(e.getMessage(), e);
			}
		} else {
			// Processing request
			final Map<String, String> map = createExpDefs(request);
			logger.debug("Processing {} expressions ...: {}", map.size(), map);
			final String json = createJsonResult(map);
			response.getWriter().print(json);
		}
		logger.debug("Exiting RatioServlet");
	}

	/**
	 * @param stream
	 * @param map
	 * @throws SAXException
	 * @throws TransformerFactoryConfigurationError
	 * @throws IOException
	 * @throws TransformerConfigurationException
	 */
	private void createXmlResult(final OutputStream stream,
			final Map<String, String> map)
			throws TransformerConfigurationException, IOException,
			TransformerFactoryConfigurationError, SAXException {
		new DefsSerializer().save(stream, map);
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	private String createJsonResult(final Map<String, String> map) {
		final Interpreter i = new Interpreter(map);

		// Producing result
		final List<ExprResult> l = new ArrayList<>();
		for (final Entry<String, Value> entry : i.getValues().entrySet()) {
			final String id = entry.getKey();
			l.add(ExprResult.create(id, map.get(id), entry.getValue()));
		}
		Collections.sort(l, new Comparator<ExprResult>() {

			@Override
			public int compare(final ExprResult o1, final ExprResult o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		final String json = gson.toJson(l.toArray(new ExprResult[0]));
		return json;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> createExpDefs(final HttpServletRequest request) {
		final Map<String, String> map = new HashMap<>();
		// Getting request parameter
		final String defs = request.getParameter("defs");
		if (defs != null) {
			final ExprDef[] p = gson.fromJson(defs, ExprDef[].class);
			for (final ExprDef e : p)
				map.put(e.getId(), e.getText());
		}
		return map;
	}
}
