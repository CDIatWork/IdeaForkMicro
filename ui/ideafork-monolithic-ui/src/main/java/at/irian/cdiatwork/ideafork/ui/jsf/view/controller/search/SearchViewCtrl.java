package at.irian.cdiatwork.ideafork.ui.jsf.view.controller.search;

import at.irian.cdiatwork.ideafork.ui.jsf.view.config.Pages;
import at.irian.cdiatwork.ideafork.ui.jsf.view.controller.ViewController;
import at.irian.cdiatwork.ideafork.ui.remote.IdeaService;
import at.irian.cdiatwork.ideafork.ui.remote.dto.Idea;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ViewController
public class SearchViewCtrl implements Serializable {

    private final String searchTextPlaceholder = "Search";

    @Inject
    private IdeaService ideaService;

    private String searchText = searchTextPlaceholder;

    private List<Idea> searchResult;

    public Class<? extends Pages.Search> search() {
        searchResult = ideaService.searchIdea(searchText);
        return Pages.Search.Fork.class;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchTextPlaceholder() {
        return searchTextPlaceholder;
    }

    public List<Idea> getSearchResult() {
        return searchResult;
    }
}
