package com.sparta.clone.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.sparta.clone.domain.Member;
import com.sparta.clone.domain.Photo;
import com.sparta.clone.domain.Post;
import com.sparta.clone.dto.ResponseDto;
import com.sparta.clone.dto.request.PostRequestDto;
import com.sparta.clone.dto.response.PhotoResponseDto;
import com.sparta.clone.dto.response.PostResponseDto;
import com.sparta.clone.repository.PhotoRepository;
import com.sparta.clone.repository.PostRepository;
import com.sparta.clone.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PhotoRepository photoRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;

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

                Photo photo = new Photo(imgurl, post.getId());
                photoRepository.save(photo);
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
}
