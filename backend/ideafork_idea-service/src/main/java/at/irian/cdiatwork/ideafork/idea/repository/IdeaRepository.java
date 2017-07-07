package at.irian.cdiatwork.ideafork.idea.repository;

import at.irian.cdiatwork.ideafork.idea.domain.CategoryView;
import at.irian.cdiatwork.ideafork.idea.domain.Idea;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.MaxResults;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.enterprise.inject.Default;
import java.util.List;

@Transactional(qualifier = Default.class)
@Repository
public interface IdeaRepository extends EntityRepository<Idea, String> {
    @Query("select i from Idea i where i.authorEmail = ?1")
    List<Idea> loadAllOfAuthor(String email);

    @Query("select new at.irian.cdiatwork.ideafork.idea.domain.CategoryView(i.category, count(i.category)) from Idea i group by i.category order by count(i.category) desc")
    List<CategoryView> getHighestRatedCategories(@MaxResults int maxNumberOfHighestRatedCategories);

    @Query("select i from Idea i where i.topic like CONCAT('%', ?1, '%') or i.category like CONCAT('%', ?1, '%')")
    List<Idea> search(String searchText);
}
