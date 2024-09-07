package snusthon.team1.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import snusthon.team1.domain.Card;
import snusthon.team1.model.mysql.User;
import snusthon.team1.repository.mysql.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import snusthon.team1.repository.neo4j.CardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CardRepository cardRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public Long getUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username))
                .getId();
    }

    // 사용자가 즐겨찾기한 카드를 검색하는 로직
    public List<String> getUserFavoriteCardContents(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userRepository.findByUsername(userDetails.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Long> favoriteCardIds = user.getCardIds();

            // Neo4j에서 favoriteCardIds에 해당하는 카드들의 content 필드만 가져옴
            return cardRepository.findCardContentsByIds(favoriteCardIds);
        }
        throw new RuntimeException("User not found or no favorite cards found.");
    }


    // 카드 즐겨찾기 추가 또는 제거 (토글 방식)
    public void toggleFavoriteCard(Long cardId, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userRepository.findByUsername(userDetails.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<Long> favoriteCardIds = user.getCardIds();

            // 즐겨찾기에 해당 카드가 이미 있는지 확인
            if (favoriteCardIds.contains(cardId)) {
                favoriteCardIds.remove(cardId); // 이미 있으면 제거
            } else {
                favoriteCardIds.add(cardId); // 없으면 추가
            }

            userRepository.save(user); // 변경된 즐겨찾기 목록 저장
        } else {
            throw new RuntimeException("User not found.");
        }
    }

}