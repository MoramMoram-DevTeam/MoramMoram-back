package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository;

import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FleamarketRepository extends JpaRepository<Fleamarket, Long> {
    List<Fleamarket> findByFleaMarketNameContaining(String m_name);
    List<Fleamarket> findTop10ByOrderByViewsDesc();
//    findTop300By OrderBy SeqDesc
}
