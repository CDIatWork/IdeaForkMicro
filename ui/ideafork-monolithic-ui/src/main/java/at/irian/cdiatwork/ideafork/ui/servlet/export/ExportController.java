package at.irian.cdiatwork.ideafork.ui.servlet.export;

import at.irian.cdiatwork.ideafork.ui.remote.IdeaService;
import at.irian.cdiatwork.ideafork.ui.remote.dto.IdeaExport;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@WebServlet("/public/idea/export/all")
public class ExportController extends HttpServlet {
    @Inject
    private IdeaService ideaService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/text");
        response.setHeader("Content-Disposition", "attachment; filename=all_ideas.json.txt");

        ObjectMapper objectMapper = new ObjectMapper();
        String exportedString = objectMapper.writeValueAsString(
            Optional.ofNullable(ideaService.loadAll()).orElse(emptyList()).stream().map(IdeaExport::new).collect(toList()));
        response.getWriter().write(exportedString);
    }
}
