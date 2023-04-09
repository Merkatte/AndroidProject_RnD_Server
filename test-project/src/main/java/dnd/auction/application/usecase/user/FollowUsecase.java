package dnd.auction.application.usecase.user;

import dnd.auction.domain.user.entity.Follow;
import dnd.auction.domain.user.repository.FollowRepository;
import dnd.auction.domain.user.service.FollowService;
import dnd.auction.exception.CustomException;
import dnd.auction.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowUsecase {

    final private UserRepository userRepository;
    final private FollowRepository followRepository;
    final private FollowService followService;

    @Transactional
    public void execute(Follow follow){
        if (!userRepository.existsById(follow.getUserId()) || !userRepository.existsById(follow.getFollowId())){
            throw new CustomException("User Does Not Exist", HttpStatus.UNPROCESSABLE_ENTITY);
        } // exception User Not Found
        if (followRepository.existsByUserIdAndFollowId(follow.getUserId(), follow.getFollowId())){
            followService.deleteFollow(follow);
        }
        else {
            followService.createFollow(follow);
        }
    }
}
