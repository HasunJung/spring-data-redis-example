package hasun.jung.redis.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@Builder
@RedisHash("peer")
public class Peer implements Serializable {
//	@Id
	private String id;
	@TimeToLive
	private long peerExpireDate;
}
