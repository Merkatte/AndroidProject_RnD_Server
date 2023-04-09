package dnd.auction.application.controller;

import dnd.auction.application.usecase.post.PostFilterUsecase;
import dnd.auction.domain.post.dto.PostCategoryDTO;
import dnd.auction.domain.post.dto.PostResponseDTO;
import dnd.auction.domain.post.entity.PostCategory;
import dnd.auction.domain.post.service.PostCategoryWriteService;
import dnd.auction.domain.post.service.PostReadService;
import dnd.auction.domain.post.dto.LikesDTO;
import dnd.auction.domain.post.dto.PostDTO;
import dnd.auction.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    final private PostWriteService postWriteService;
    final private PostReadService postReadService;
    final private PostCategoryWriteService postCategoryWriteService;
    final private PostFilterUsecase postFilterUsecase;

    @PostMapping(value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDTO updatePost(@RequestPart PostDTO post, @RequestPart List<MultipartFile> images){
        return postWriteService.createPost(post, images);
    }

    @PostMapping(value = "/update/{postId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDTO updatePost(@RequestPart PostDTO post, @RequestPart MultipartFile images,
                                      @PathVariable Long postId){
        return postWriteService.updatePost(post, postId);
    }

    @GetMapping("/{postId}")
    public PostResponseDTO getPost(@PathVariable Long postId){
        return postReadService.getPost(postId);
    }

    @GetMapping("/timeline/{userId}")
    public List<PostResponseDTO> getTimeline(@PathVariable Long userId){
        return postReadService.getTimeline(userId);
    }

    @PostMapping("/likes/{postId}")
    public void postLike(@PathVariable Long postId, @RequestBody LikesDTO likesDTO){
        postWriteService.postLikes(likesDTO.userId(), postId);
    }

    @PostMapping("/create-category")
    public PostCategory createPostCategory(@RequestBody PostCategoryDTO postCategoryDTO){
        return postCategoryWriteService.createPostCategory(postCategoryDTO);
    }

    @GetMapping("/board")
    public List<PostResponseDTO> postBoard(Pageable pageable){
        return postReadService.getBoard(pageable);
    }

    @GetMapping("/category")
    public List<PostResponseDTO> getPostCategory(@RequestParam("categoryName") String categoryName, Pageable pageable){
        return postReadService.getBoardByCategory(categoryName, pageable);
    }

    @GetMapping("/user")
    public List<PostResponseDTO> getPostUserId(@RequestParam("userId") Long userId, Pageable pageable){
        return postReadService.getBoardByUserId(userId, pageable);
    }

//    @GetMapping("/filter")
//    public List<PostResponseDTO> getPostByFilter(@RequestParam FilterKeywordDTO filterKeyword, Pageable pageable){
//        return postFilterUsecase.execute(filterKeyword, pageable);
//    }


}
