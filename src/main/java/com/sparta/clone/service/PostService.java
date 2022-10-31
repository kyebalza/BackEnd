package com.sparta.clone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.sparta.clone.domain.*;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.PostRequestDto;
import com.sparta.clone.dto.response.*;
import com.sparta.clone.repository.*;
import com.sparta.clone.security.user.UserDetailsImpl;
import com.sparta.clone.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;

    private final CommentRepository commentRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final PostLikesRepository postLikesRepository;

    private final LikesRepository likesRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;
    //게시글 생성
    @Transactional
    public ResponseDto<PostResponseDto> createPost(List<MultipartFile> multipartFile, PostRequestDto postRequestDto, Member member) throws IOException {
        String imgurl = null;

        Post post = Post.builder()
                .content(postRequestDto.getContent())
                .member(member)
                .build();
        postRepository.save(post);
        for (MultipartFile file : multipartFile){
            if (!multipartFile.isEmpty()) {
                String fileName = CommonUtils.buildFileName(file.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType(file.getContentType());

                byte[] bytes = IOUtils.toByteArray(file.getInputStream());
                objectMetadata.setContentLength(bytes.length);
                ByteArrayInputStream byteArrayIs = new ByteArrayInputStream(bytes);

                amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, byteArrayIs, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
                imgurl = amazonS3Client.getUrl(bucketName, fileName).toString();

                Photo photo = new Photo(imgurl, post);
//                post.addPhoto(photo);
                photoRepository.save(photo);

                //추가
            }
        }
        /*
        * 확인용
        * */
        List<Photo> imgList = photoRepository.findAllByPostId(post.getId());
        List<PhotoResponseDto> photoResponseDto = new ArrayList<>();
        for(Photo photo : imgList){
            photoResponseDto.add(
                    PhotoResponseDto.builder()
                            .postImgUrl(photo.getPostImgUrl())
                            .build()
            );
        }
        return ResponseDto.success(
                PostResponseDto.builder()
                        .content(postRequestDto.getContent())
                        .postImgUrl(photoResponseDto)
                        .build()
        );
    }
    //게시글 전체 조회
    public List<AllPostResponseDto> readAll(){

//        List<Post> postList = postRepository.findAll();

//        List<AllPostResponseDto> postResponseDtoList = postList.stream()
//                .map(post -> new AllPostResponseDto(post))
//                .collect(Collectors.toList()) ;
//        return postResponseDtoList;
        List<Post> postList = postRepository.findAll();
        List<AllPostResponseDto> allPostResponseDtoList = new ArrayList<>();
        for(Post post : postList){

            Long postId = post.getId();//게시글 번호
            Long memberId = post.getMember().getId();//맴버 번호
            //좋아요
            Long likeCnt = likesRepository.countByPostId(postId);//좋아요 수
            Optional<Likes> likes = likesRepository.findByPostIdAndMemberId(postId, memberId);//좋아요 여부
            boolean likeCheck;
            if (likes.isPresent()){
                likeCheck = true;
            }else {
                likeCheck = false;
            }
            //댓글 수
            Long CommentCnt = commentRepository.countByPostId(postId);


            ////////////////////////////////////////
            AllPostResponseDto allPostResponseDto =
                    AllPostResponseDto.builder()
                            .id(post.getId())
                            .content(post.getContent())
                            .username(post.getMember().getUsername())
                            .profileImg(post.getMember().getProfileImg())
                            .createdAt(post.getCreatedAt())
                            .modifiedAt(post.getModifiedAt())
                            .CommentCnt(CommentCnt)
                            .likeCheck(likeCheck)
                            .likeCnt(likeCnt)
                            .postImgUrl(post.getPhotos()
                                    .stream()
                                    .map(Photo::getPostImgUrl)
                                    .collect(Collectors.toList()))
                            .build();
            allPostResponseDtoList.add(allPostResponseDto);
        }
        return allPostResponseDtoList;
    }
    //상제 게시글 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> getPostOne(Long postId, Long memberId) {
        Post post = postRepository.findById(postId).orElseThrow();

        List<Comment> commentList = commentRepository.findAllById(postId);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        Long likeCnt = postLikesRepository.countByPostId(postId);
        Optional<Likes> likes = postLikesRepository.findByPostIdAndMemberId(postId, memberId);
        boolean likeCheck;
        if (likes.isPresent()) {
            likeCheck = true;
        } else {
            likeCheck = false;
        }
        for (Comment comment : commentList) {

            Long comment_id = comment.getId();//댓글 번호
            Long member_id = comment.getMember().getId();//맴버 번호
            boolean commentLikeCheck;//댓글 좋아요 여부

            Long commentLikeCnt = commentLikesRepository.countByComment_Id(comment_id);//좋아요 수
            Optional<CommentLike> commentLikes = commentLikesRepository.findByComment_IdAndMember_Id(comment_id, member_id);//좋아요 여부
            if (commentLikes.isPresent()){
                commentLikeCheck = true;
            }else {
                commentLikeCheck = false;
            }


            commentResponseDtos.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .comment(comment.getComment())
                            .profileImg(comment.getMember().getProfileImg())
                            .username(comment.getMember().getUsername())
                            .commentLikeCheck(commentLikeCheck)
                            .commentLikeCnt(commentLikeCnt)
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }
        return ResponseDto.success(
                OnePostResponseDto.builder()
                        .Id(post.getId())
                        .username(post.getMember().getUsername())
                        .profileImg(post.getMember().getProfileImg())
                        .content(post.getContent())
                        .postImgUrl(post.getPhotos().stream().map(Photo::getPostImgUrl).collect(Collectors.toList()))
                        .likeCnt(likeCnt)
                        .comments(commentResponseDtos)
                        .likeCheck(likeCheck)
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    //게시글 수정
    @Transactional
    public ResponseDto<?> updatePost(Long postId, UserDetailsImpl userDetailsImpl, PostRequestDto postRequestDto) {
        Long memberId=userDetailsImpl.getMember().getId();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디를 가진 게시글이 존재하지 않습니다.")
        );

        checkOwner(post, memberId); /***로그인한 사람의 아이디를 가져오는 역활****/

        //2.
        post.updatePost(postRequestDto.getContent());
        //3.
        postRepository.save(post);



        /////////////////////////////
        //수정된 게시글 불러오기
        Post updatePost = postRepository.findById(postId).orElseThrow();

        List<Comment> commentList = commentRepository.findAllById(postId);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        Long likeCnt = postLikesRepository.countByPostId(postId);
        Optional<Likes> likes = postLikesRepository.findByPostIdAndMemberId(postId, memberId);
        boolean likeCheck;
        if (likes.isPresent()) {
            likeCheck = true;
        } else {
            likeCheck = false;
        }
        for (Comment comment : commentList) {

            Long comment_id = comment.getId();//댓글 번호
            Long member_id = comment.getMember().getId();//맴버 번호
            boolean commentLikeCheck;//댓글 좋아요 여부

            Long commentLikeCnt = commentLikesRepository.countByComment_Id(comment_id);//좋아요 수
            Optional<CommentLike> commentLikes = commentLikesRepository.findByComment_IdAndMember_Id(comment_id, member_id);//좋아요 여부
            if (commentLikes.isPresent()){
                commentLikeCheck = true;
            }else {
                commentLikeCheck = false;
            }


            commentResponseDtos.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .comment(comment.getComment())
                            .profileImg(comment.getMember().getProfileImg())
                            .username(comment.getMember().getUsername())
                            .commentLikeCheck(commentLikeCheck)
                            .commentLikeCnt(commentLikeCnt)
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        return ResponseDto.success(OnePostResponseDto.builder()
                .Id(updatePost.getId())
                .username(updatePost.getMember().getUsername())
                .profileImg(updatePost.getMember().getProfileImg())
                .content(updatePost.getContent())
                .postImgUrl(updatePost.getPhotos().stream().map(Photo::getPostImgUrl).collect(Collectors.toList()))
                .likeCnt(likeCnt)
                .comments(commentResponseDtos)
                .likeCheck(likeCheck)
                .createdAt(updatePost.getCreatedAt())
                .modifiedAt(updatePost.getModifiedAt())
                .build());
    }


    //게시글 삭제
    @Transactional
    public ResponseDto<?> deletePost(Long postId, UserDetailsImpl userDetailsImpl){
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 아이디를 가진 게시글이 존재하지 않습니다.")
        );

        checkOwner(post, userDetailsImpl.getMember().getId());
        //댓글 삭제
        commentRepository.deleteAllByPostId(postId);
        postLikesRepository.deleteLikesByPost(post);

        photoRepository.deleteAllByPostId(postId);

        //게시글 삭제
        postRepository.deleteById(postId);//게시물을 먼저 삭제안한이유
        return ResponseDto.success("게시글이 삭제되었습니다");
    }


    private void checkOwner(Post post, Long memberId){
        if(!post.checkOwnerByMemberId(memberId)){
            throw new IllegalArgumentException("회원님이 작성한 글이 아닙니다.");
        }
    }
}
