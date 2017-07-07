package at.irian.cdiatwork.ideafork.idea.repository;

import at.irian.cdiatwork.ideafork.idea.domain.Idea;
import at.irian.cdiatwork.ideafork.idea.domain.PromotionRequest;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public abstract class PromotionRepository implements EntityRepository<PromotionRequest, String> {
    @Inject
    private EntityManager entityManager;

    public List<PromotionRequest> loadRecentIdeaPromotions(String email, String searchHint) {
        if ("*".equalsIgnoreCase(searchHint)) {
            return entityManager.createQuery(
                    "select pr from PromotionRequest pr where pr.promotedAt is null and pr.ideaForPromotion.authorEmail <> :currentEMail order by pr.createdAt desc",
                    PromotionRequest.class)
                    .setParameter("currentEMail", email)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();
        }
        return entityManager.createQuery(
                "select pr from PromotionRequest pr where pr.promotedAt is null and pr.ideaForPromotion.authorEmail <> :currentEMail and (pr.ideaForPromotion.topic like :searchText or pr.ideaForPromotion.category like :searchText) order by pr.createdAt desc",
                PromotionRequest.class)
                .setParameter("currentEMail", email)
                .setParameter("searchText", "%" + searchHint + "%")
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }

    public void promoteIdea(PromotionRequest promotionRequest) {
        PromotionRequest storedPromotionRequest = this.entityManager.merge(promotionRequest);
        storedPromotionRequest.setPromotedAt(new Date());
    }

    public List<Idea> loadRecentlyPromotedIdeas(String email) {
        return entityManager.createQuery(
                "select pr.ideaForPromotion from PromotionRequest pr where pr.promotedAt is not null and pr.ideaForPromotion.authorEmail <> :currentEMail order by pr.promotedAt desc",
                Idea.class)
                .setParameter("currentEMail", email)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
