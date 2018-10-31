package hasun.jung.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import hasun.jung.redis.domain.Peer;
import hasun.jung.redis.repository.PeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedisTest {

	@Autowired PeerRepository peerRepository;
	@Autowired ObjectMapper objectMapper;

//	@After
//	public void testDeleteApp() throws Exception {
//		peerRepository.deleteAll();
//	}

	@Test
	public void testSave() {
		String id = "hasun2";
		Peer point = Peer.builder().id(id).peerExpireDate(60).build();
		peerRepository.save(point);

		Peer savedPoint = peerRepository.findById(id).get();
		log.debug("savedPoint : {}", savedPoint);
	}

	@Test
	public void testFind() {
		log.debug("peerRepository.count() : {}, findAll() : {}", peerRepository.count(), peerRepository.findAll());

		String id = "hasun2";
//		log.debug("peerRepository.findAll() : {}", peerRepository.findAll());
//		Peer savedPoint = peerRepository.findById(id).get();
//		log.debug("savedPoint : {}", savedPoint);

		List<Peer> students = new ArrayList<>();
		peerRepository.findAll().forEach(students::add);
		log.debug("students.size : {}", students.size());
		log.debug("students.size : {}", students.get(1));
	}
}
