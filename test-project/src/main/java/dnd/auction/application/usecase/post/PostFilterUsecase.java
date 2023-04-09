package dnd.auction.application.usecase.post;

import dnd.auction.domain.post.service.PostReadService;
import dnd.auction.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFilterUsecase {

    final private UserRepository userRepository;
    final private PostReadService postReadService;
//    public List<PostResponseDTO> execute(FilterKeywordDTO filterKeyword, Pageable pageable){
//
//    }
}
