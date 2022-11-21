package kusitms.candoit.MoramMoramServer.domain.fleaMarket.Repository;

import kusitms.candoit.MoramMoramServer.domain.fleaMarket.Entity.Fleamarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FleamarketRepository extends JpaRepository<Fleamarket, Long> {
    List<Fleamarket> findByMarketNameContaining(String m_name);
    Fleamarket findFleamarketById(Long m_id);
}
