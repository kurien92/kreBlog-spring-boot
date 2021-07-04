package net.kurien.blog.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kurien.blog.module.post.entity.PostPublishStatus;
import net.kurien.blog.module.post.entity.PostViewStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postNo;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String postAuthor;
    private String postPassword;
    private String postSubject;
    private String postContent;
    private PostViewStatus postView;
    private PostPublishStatus postPublish;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postWriteTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postReservationTime;
    private String postWriteIp;

    @Builder
    public Post(Category category, String postAuthor, String postPassword, String postSubject, String postContent, PostViewStatus postView, PostPublishStatus postPublish, Date postWriteTime, Date postReservationTime, String postWriteIp) {
        this.category = category;
        this.postAuthor = postAuthor;
        this.postPassword = postPassword;
        this.postSubject = postSubject;
        this.postContent = postContent;
        this.postView = postView;
        this.postPublish = postPublish;
        this.postWriteTime = postWriteTime;
        this.postReservationTime = postReservationTime;
        this.postWriteIp = postWriteIp;
    }
}
