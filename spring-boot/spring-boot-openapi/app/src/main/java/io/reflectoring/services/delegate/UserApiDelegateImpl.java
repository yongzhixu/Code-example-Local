package io.reflectoring.services.delegate;

import com.convertlab.common.beta.http.WebClientService;
import com.convertlab.common.beta.utils.JsonUtil;
import com.convertlab.kafkapipe.service.KafkaProducer;
import io.reflectoring.entity.UserDb;
import io.reflectoring.model.User;
import io.reflectoring.services.IuserService;
import lombok.extern.slf4j.Slf4j;
import io.reflectoring.api.UserApiDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class UserApiDelegateImpl implements UserApiDelegate {

    @Autowired
    IuserService iuserService;

    @Value("${spring.kafka.template.test.topic}")
    private String messageTopic;

    @Override
    public ResponseEntity<User> getUserByName(String username) {
        User user = new User();

        user.setId(123L);
        user.setFirstName("Petros");
        user.setLastName("S");
        user.setUsername("Petros");
        user.setEmail("petors.stergioulas94@gmail.com");
        user.setPassword("secret");
        user.setPhone("+123 4567890");
        user.setUserStatus(0);

        log.info("we are the one for now ");
        log.error("we are error ");
        UserDb userdb = iuserService.getUserById(Long.valueOf("123"));
        log.error(JsonUtil.toJsonStringNonNull(userdb));
        user.setFirstName(userdb.getFirstName());
        user.setLastName(userdb.getLastName());
        KafkaProducer.send(messageTopic, user.getId().toString(), JsonUtil.toJsonStringNonNull(user));

        WebClientService webClientService = new WebClientService();
        Map<String, String> map = new HashMap<>();
//        map = webClientService.getForObject("http://localhost:8087/resource", Map.class);
//        log.error(JsonUtil.toJsonStringNonNull(map));
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<User> updateUser(String username, User body) {
        return null;
    }

    @Override
    public ResponseEntity<Void> createUser(User body) {
        User user = new User();
        user.setEmail("email");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
