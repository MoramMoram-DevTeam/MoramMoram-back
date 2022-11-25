package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository;

import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FleamarketRepository extends JpaRepository<Fleamarket, Long> {
    List<Fleamarket> findByMarketNameContaining(String m_name);
    Fleamarket findFleamarketById(Long m_id);

    @Transactional
    @Modifying
    @Query("update Fleamarket f set f.views = f.views + 1 where f.id = :id")
    int updateViewCount(Long id);

    List<Fleamarket> findTop10ByOrderByViewsDesc();
//    findTop300By OrderBy SeqDesc
}
