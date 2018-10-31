package hasun.jung.redis.repository;


import hasun.jung.redis.domain.Peer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeerRepository extends CrudRepository<Peer, String> {
}
