package kusitms.candoit.MoramMoramServer.domain.application.Repository;

import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Application findApplicationByApplicationIdAndUserId(Long appId, Long userId);
    List<Application> findAllByUserId(Long userId);
    List<Application> findAllByMarketId(Long marketId);
}
