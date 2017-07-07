package at.irian.cdiatwork.ideafork.ui.servlet.upload;

import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.logging.Logger;

@ApplicationScoped
public class FileUploadHandler {
    private static final Logger LOG = Logger.getLogger(FileUploadHandler.class.getName());

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Inject
    private IdeaService ideaService;

    @Inject
    private ImportSummary importSummary;

    public void storeUploadedFiles(Collection<Part> parts) {
        for (Part part : parts) {
            String fileName = getFileName(part);

            if (fileName == null) { //ignore hidden-field data
                continue;
            }

            LOG.fine("start importing " + fileName);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(part.getInputStream(), UTF8));
                String ideaToImportString = bufferedReader.readLine();

                while (ideaToImportString != null) {
                    try {
                        Idea importedIdea = importIdea(ideaToImportString);
                        importSummary.addImportedIdea(importedIdea);
                    } catch (Exception e) {
                        importSummary.addFailedImport(ideaToImportString);
                    }
                    ideaToImportString = bufferedReader.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (fileName != null) {
                    importSummary.addFailedImport("Import of '" + fileName + "' failed!");
                } else {
                    importSummary.addFailedImport("Import failed!");
                }
            }
        }
    }

    private String getFileName(Part part) {
        for (String current : part.getHeader("content-disposition").split(";")) {
            current = current.trim();
            if (current.startsWith("filename=")) {
                return current.substring(10, current.length() - 1);
            }
        }
        return null;
    }

    public Idea importIdea(String ideaString) {
        if (ideaString == null) {
            throw new IllegalArgumentException("No idea to import");
        }

        String[] ideaToImport = ideaString.split(";");

        if (ideaToImport.length >= 2) {
            Idea newIdea = new Idea(null, null, ideaToImport[0], ideaToImport[1], null);

            if (ideaToImport.length == 3) {
                newIdea.setDescription(ideaToImport[2]);
            }
            ideaService.save(newIdea);
            return newIdea;
        }
        throw new IllegalArgumentException("invalid idea to import: " + ideaString);
    }
}
