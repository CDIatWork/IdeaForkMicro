package at.irian.cdiatwork.ideafork.ui.servlet.upload;

import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigDescriptor;
import org.apache.deltaspike.core.api.config.view.metadata.ViewConfigResolver;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/idea/import")
@MultipartConfig
public class IdeaImportServlet extends HttpServlet {
    @Inject
    private FileUploadHandler fileUploadHandler;

    @Inject
    private ViewConfigResolver viewConfigResolver;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fileUploadHandler.storeUploadedFiles(request.getParts());

        ViewConfigDescriptor viewConfigDescriptor = viewConfigResolver.getViewConfigDescriptor(Pages.Import.Summary.class);
        request.getRequestDispatcher(viewConfigDescriptor.getViewId() + "?dswid=" + request.getParameter("dswid")).forward(request, response);
    }
}