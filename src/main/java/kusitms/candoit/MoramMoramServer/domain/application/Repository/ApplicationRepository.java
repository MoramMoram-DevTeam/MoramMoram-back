package kusitms.candoit.MoramMoramServer.domain.application.Repository;

import kusitms.candoit.MoramMoramServer.domain.application.Entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Application findApplicationByApplicationIdAndUserId(Long appId, Long userId);
    List<Application> findAllByUserId(Long userId);
    List<Application> findAllByMarketId(Long marketId);
    List<Application> findAllByMarketIdAndStatus(Long marketId, String status);

    Application findApplicationByApplicationId(Long applicationId);

}
